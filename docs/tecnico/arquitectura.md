# Diagramas de Arquitectura

Estos diagramas se generaron a partir del **código fuente real** del repositorio (`FINTECHMOVIL/backend` y `FINTECHMOVIL/frontend`), no son ilustrativos ni genéricos. Sustituyen el hueco señalado en la [Fase 2 — Evidencia de Desarrollo](../fase2/evidencia-desarrollo.md#estructura-del-desarrollo) y en el [Registro de Evidencias](../fase2/registro-evidencias.md) (EV-010 a EV-012), donde se referenciaban diagramas C4/UML que no se habían incluido como imagen.

## 1. Diagrama general de arquitectura (C4 — Contenedores)

```mermaid
graph TB
    subgraph Cliente
        U[Usuario]
    end

    subgraph "Frontend — Vercel"
        FE["React 19 + Vite<br/>App.jsx (SPA)<br/>Dashboard · Productos · Inventario<br/>Ventas POS · Reportes · Clientes"]
    end

    subgraph "Backend — Railway (Spring Boot 3.2.5)"
        direction TB
        CTRL["control/<br/>AuthControlador · ProductoController<br/>VentaController · FacturaController<br/>UsuarioController · ReporteController"]
        SEC["Seguridad<br/>SecurityConfig · JwtFilter · JwtService<br/>CorsConfig"]
        SRV["servicio/<br/>CrudGenericoService · ProductoService<br/>VentaServiceImpl · FacturacionService<br/>ClienteService · UsuarioService"]
        REPO["repositorio/<br/>Spring Data JPA Repositories"]
        MOD["modelo/<br/>Producto · Usuario · Venta<br/>Factura · DetalleFactura · Pago"]
        EXC["excepciones/<br/>GlobalExceptionHandler<br/>StockInsuficienteException<br/>CarritoVacioException"]
    end

    subgraph "Base de Datos — Railway MySQL"
        DB[(MySQL 8.0<br/>techmovil_db)]
    end

    subgraph "Herramientas de calidad (local Docker)"
        SQ[SonarQube]
        JK[Jenkins]
        K6[K6 Load Testing]
    end

    U -->|HTTPS| FE
    FE -->|"HTTP/JSON<br/>Authorization: Bearer JWT"| CTRL
    CTRL --> SEC
    SEC --> SRV
    CTRL --> SRV
    SRV --> REPO
    SRV -.->|"lanza"| EXC
    REPO -->|JDBC| DB

    SQ -.->|analiza código| CTRL
    JK -.->|CI/CD pipeline| CTRL
    K6 -.->|prueba de carga| CTRL

    style FE fill:#61dafb,color:#000
    style DB fill:#00758f,color:#fff
    style SEC fill:#f9a825,color:#000
```

**Notas de la arquitectura real:**

- El frontend es una **SPA de un solo archivo grande** (`App.jsx`, ~1400 líneas + ~4900 líneas de CSS embebido), sin router de terceros — el "ruteo" es manejo de estado de React.
- `SecurityConfig` deshabilita CSRF intencionalmente (API REST *stateless* consumida por SPA, autenticación por `Authorization: Bearer <JWT>`, no por cookies de sesión) — comentario textual en el propio código: *"CSRF deshabilitado intencionalmente: API REST stateless consumida por SPA React."*
- Varios endpoints de lectura (`GET /api/**`) y de escritura de `clientes`/`productos` están marcados `permitAll()` deliberadamente, para permitir demo/fallback a datos mock del frontend cuando el backend no está disponible.
- SonarQube, Jenkins y K6 corren localmente vía Docker Compose (`FINTECHMOVIL/backend/docker-compose.yml`), no forman parte del entorno de producción (Railway/Vercel).

## 2. Flujo de autenticación (JWT)

```mermaid
sequenceDiagram
    actor U as Usuario
    participant FE as Frontend (React)
    participant AC as AuthControlador<br/>POST /api/auth/login
    participant JS as JwtService
    participant JF as JwtFilter
    participant SC as SecurityConfig /<br/>SecurityContextHolder
    participant CTRL as Controlador protegido<br/>(p.ej. VentaController)

    U->>FE: Ingresa usuario / contraseña
    FE->>AC: POST /api/auth/login {username, password}
    AC->>AC: Compara contra app.admin.username / app.admin.password
    alt Credenciales correctas
        AC->>JS: generateToken(username)
        JS-->>AC: JWT firmado (HMAC, app.jwt.signing)
        AC-->>FE: 200 { status, token, role: "ADMIN", nombre, username }
        FE->>FE: Guarda el token (localStorage)
    else Credenciales incorrectas
        AC-->>FE: 401 "Credenciales incorrectas"
    end

    Note over FE,CTRL: En cada petición posterior a un endpoint protegido
    FE->>JF: Request + header Authorization: Bearer <token>
    JF->>JS: parseClaimsJws(token) con jwtService.getKey()
    alt Token válido
        JF->>SC: setAuthentication(UsernamePasswordAuthenticationToken)
        SC->>CTRL: Continúa la cadena de filtros (filterChain.doFilter)
        CTRL-->>FE: 200 + datos
    else Token inválido / ausente / expirado
        JF->>SC: SecurityContextHolder.clearContext()
        SC-->>FE: 401 / 403 según regla de authorizeHttpRequests
    end
```

**Notas del flujo real:**

- `/api/auth/**` y la documentación Swagger (`/swagger-ui/**`, `/v3/api-docs/**`) están siempre públicas (`permitAll()`).
- El usuario administrador es único y se valida contra propiedades de configuración (`app.admin.username` / `app.admin.password`, por defecto `admin` / `admin123`), **no** contra una tabla de usuarios con múltiples cuentas para el login inicial.
- `JwtFilter` extiende `OncePerRequestFilter` y se registra antes de `UsernamePasswordAuthenticationFilter` (`addFilterBefore`).
- Si el JWT es inválido, el filtro limpia el contexto de seguridad pero **no interrumpe la cadena** — la petición sigue y es la regla de `authorizeHttpRequests` (`anyRequest().authenticated()` para lo no listado como público) la que finalmente devuelve 401/403.

## 3. Flujo de inventario (venta y control de stock)

```mermaid
flowchart TD
    A["Cliente arma el carrito en el POS<br/>(frontend)"] --> B["POST /api/ventas<br/>VentaController"]
    B --> C["VentaServiceImpl.save(venta)"]
    C --> D{"¿venta.producto<br/>y producto.id<br/>presentes?"}
    D -- No --> E["IllegalArgumentException<br/>'La venta debe incluir un producto con ID válido'"]
    D -- Sí --> F["productoRepo.findById(id)<br/>filtrado por activo = true"]
    F --> G{"¿Producto existe<br/>y está activo?"}
    G -- No --> H["EntityNotFoundException<br/>'Producto no encontrado'"]
    G -- Sí --> I{"producto.stock >= cantidad<br/>solicitada?"}
    I -- No --> J["StockInsuficienteException<br/>'No hay stock disponible para: {modelo}.<br/>Stock actual: X, Solicitado: Y'"]
    I -- Sí --> K["producto.stock -= cantidad<br/>productoRepo.save(producto)"]
    K --> L["venta.total = producto.precio * cantidad"]
    L --> M["repo.save(venta)<br/>(operación @Transactional)"]
    M --> N["200 OK — Venta registrada,<br/>stock actualizado"]

    E --> X["GlobalExceptionHandler<br/>→ respuesta de error al frontend"]
    H --> X
    J --> X

    O["GET /api/productos/alertas<br/>ProductoController.obtenerAlertasStock()"] --> P["productoRepository.obtenerProductosEnAlerta()"]
    P --> Q["Lista de productos con<br/>stock por debajo del mínimo"]

    style J fill:#ff6b6b,color:#fff
    style N fill:#51cf66,color:#000
    style Q fill:#ffd43b,color:#000
```

**Notas del flujo real:**

- Toda la operación de venta corre dentro de una única transacción (`@Transactional` en `VentaServiceImpl.save`): si falla el guardado de la venta, el descuento de stock también se revierte.
- El control de stock insuficiente es **a nivel de aplicación** (comparación `producto.getStock() < cantidad`), no una restricción de base de datos.
- El catálogo (`ProductoController`) expone CRUD completo (`GET`, `POST`, `PUT`, `DELETE /api/productos`) más un endpoint dedicado `/api/productos/alertas` para productos con stock bajo, usado por el Dashboard del frontend para las alertas visuales.
- El `DELETE` de producto es un borrado **lógico** (marca `activo = false`), no elimina el registro físico — por eso `VentaServiceImpl` filtra explícitamente `p.getActivo()`.

---

*Diagramas construidos a partir de: `AuthControlador.java`, `JwtFilter.java`, `JwtService.java`, `SecurityConfig.java`, `ProductoController.java`, `VentaServiceImpl.java` — commit vigente de la rama en el momento de escribir esta página. Si el código cambia, estos diagramas deben regenerarse.*
