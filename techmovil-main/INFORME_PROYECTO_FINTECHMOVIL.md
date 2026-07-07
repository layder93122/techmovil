# INFORME TECNICO DEL PROYECTO — FinTechMovil ERP
## Sistema de Gestion de Inventario y Ventas de Telefonia Movil

---

## 1. RESUMEN EJECUTIVO

**FinTechMovil** es un sistema ERP (Enterprise Resource Planning) fullstack para la gestion integral de una tienda de telefonia movil. Permite administrar inventario, procesar ventas en punto de venta (POS), gestionar clientes, generar reportes y controlar el ciclo comercial completo.

| Dato | Valor |
|------|-------|
| Version | 2.1 |
| Fecha | Junio 2026 |
| Frontend | Vercel (React 19 SPA) |
| Backend | Railway (Spring Boot 3.2.5) |
| Base de Datos | MySQL 8.0 en Railway |
| Credenciales demo | usuario: admin / contrasena: admin123 |

---

## 2. STACK TECNOLOGICO

### Backend
| Tecnologia | Version | Uso |
|-----------|---------|-----|
| Java | 17 | Lenguaje principal |
| Spring Boot | 3.2.5 | Framework web |
| Spring Security | 6.x | Autenticacion JWT |
| Spring Data JPA | 3.2.5 | ORM / acceso a datos |
| MySQL | 8.0 | Base de datos relacional |
| MapStruct | 1.5.x | Mapeo DTO y Entity |
| Lombok | 1.18.x | Reduccion de boilerplate |
| Springdoc OpenAPI | 2.x | Documentacion Swagger |
| JaCoCo | 0.8.x | Cobertura de codigo |
| SonarQube | 9.x | Analisis de calidad |

### Frontend
| Tecnologia | Version | Uso |
|-----------|---------|-----|
| React | 19 | Framework UI |
| Vite | 6.x | Build tool |
| CSS Variables | -- | Theming |
| Fetch API | -- | Comunicacion con backend |

### Infraestructura
| Servicio | Plataforma | Proposito |
|---------|-----------|-----------|
| Frontend | Vercel | CDN + SPA hosting |
| Backend | Railway.app | Contenedor Java |
| Base de datos | Railway MySQL | Persistencia |
| CI/CD local | Jenkins (Docker) | Pipelines |
| Calidad | SonarQube (Docker) | Static analysis |
| Carga | K6 | Load testing |
| RENIEC/SUNAT | apis.net.pe | Consulta DNI/RUC |

---

## 3. ARQUITECTURA DEL SISTEMA

```
USUARIO FINAL
     | HTTPS
     v
VERCEL (CDN Global)
  React 19 SPA (App.jsx, ~1650 lineas)
  Modulos: Dashboard / Productos / Inventario / Ventas / Reportes / Clientes
  Serverless: /api/dni  /api/ruc  (proxy a apis.net.pe)
     | REST/JSON + Bearer JWT
     v
RAILWAY (Backend Java)
  Spring Boot 3.2.5
  Controllers --> Services --> Repositories (JPA)
  JwtFilter + SecurityConfig + GlobalExceptionHandler
     | JDBC
     v
RAILWAY MySQL 8.0
  Tablas: productos, usuarios, ventas, facturas,
          detalle_factura, pagos, caracteristicas
     |
     v
APIs Externas: apis.net.pe --> RENIEC (DNI) + SUNAT (RUC)
```

---

## 4. DIAGRAMA UML — CLASES (Entidades de Dominio)

```
+------------------------+         +------------------------+
|      <<Entity>>        |         |      <<Entity>>        |
|       Usuario          |         |       Producto         |
+------------------------+         +------------------------+
| id: Long               |         | id: Long               |
| username: String       |         | marca: String          |
| password: String       |         | modelo: String         |
| nombre: String         |         | precio: Double         |
| apellido: String       |         | stock: Integer         |
| email: String          |         | stockMinimo: Integer   |
| rol: String            |         | imagenUrl: String      |
| activo: Boolean        |         | activo: Boolean        |
| <<impl>> Activable     |         | caracteristicas (FK)   |
+------------------------+         | <<impl>> Activable     |
                                   +----------+-------------+
                                              | OneToOne
+------------------------+                   |
|      <<Entity>>        |<------------------+
|     Caracteristica     |
+------------------------+
| id: Long               |
| procesador: String     |
| ram: String            |
| almacenamiento: String |
| bateria: String        |
| camaras: String        |
| pantalla: String       |
| activo: Boolean        |
+------------------------+

+------------------------+          +------------------------+
|      <<Entity>>        |          |      <<Entity>>        |
|        Venta           |          |       Factura          |
+------------------------+          +------------------------+
| id: Long               |          | id: Long               |
| fecha: LocalDate       |  1..*    | fechaEmision: Date     |
| total: Double          +--------->| total: Double          |
| estado: String         |          | tipoDocumento: String  |
| usuario: Usuario (FK)  |          | venta: Venta (FK)      |
+----------+-------------+          | pagos: List<Pago>      |
           | 1..*                   +------------------------+
           v
+------------------------+          +------------------------+
|      <<Entity>>        |          |      <<Entity>>        |
|    DetalleFactura      |          |         Pago           |
+------------------------+          +------------------------+
| id: Long               |          | id: Long               |
| cantidad: Integer      |          | monto: Double          |
| precioUnitario: Double |          | metodoPago: String     |
| producto: Prod (FK)    |          | fechaPago: Date        |
| subtotal: Double       |          | factura: Factura (FK)  |
+------------------------+          +------------------------+

+------------------------+
|    <<Interface>>       |
|       Activable        |
+------------------------+
| isActivo(): Boolean    |
| setActivo(Boolean)     |
+------------------------+
```

---

## 5. DIAGRAMA UML — CASOS DE USO

```
                  +----------------------------------------------+
                  |           Sistema FinTechMovil               |
       +-------+  |  +----------------------------+              |
       |       +--+->| Iniciar Sesion (JWT)        |              |
       |       |  |  +----------------------------+              |
       |       |  |  +----------------------------+              |
       |       +--+->| Ver Dashboard              |              |
       |       |  |  +----------------------------+              |
       |  A    |  |  +----------------------------+              |
       |  d    +--+->| CRUD Productos             |              |
       |  m    |  |  +----------------------------+              |
       |  i    |  |  +----------------------------+              |
       |  n    +--+->| Controlar Inventario        |              |
       |       |  |  +----------------------------+              |
       |       |  |  +----------------------------+  +--------+  |
       |       +--+->| Procesar Venta (POS)       +->|Boleta  |  |
       |       |  |  +----------------------------+  +--------+  |
       |       |  |  +----------------------------+              |
       |       +--+->| Exportar Reportes CSV      |              |
       |       |  |  +----------------------------+              |
       |       |  |  +----------------------------+              |
       |       +--+->| CRUD Clientes              |              |
       |       |  |  +----------------------------+              |
       |       |  |  +----------------------------+ apis.net.pe  |
       |       +--+->| Consultar DNI / RUC         +------------>|
       +-------+  |  +----------------------------+              |
                  +----------------------------------------------+
```

---

## 6. DIAGRAMA UML — SECUENCIA: Proceso de Venta POS

```
Usuario    Frontend(React)    Backend(Spring)      MySQL
   |               |                |                |
   |--Elige Prods->|                |                |
   |               |--Carrito local |                |
   |               |                |                |
   |--Ingresa DNI->|                |                |
   |               |--GET /api/dni?numero=xxx         |
   |               |<--nombre del cliente             |
   |               |                |                |
   |--Click PAGAR->|                |                |
   |               |--POST /api/ventas               |
   |               |   {cliente, items, total, metodo}|
   |               |                |--INSERT venta->|
   |               |                |--UPDATE stock->|
   |               |<--{venta: ok}--|<--------------+|
   |<--Boleta modal|                |                |
   |               |                |                |
   |--Click Print->|--window.print()|                |
```

---

## 7. DIAGRAMA UML — SECUENCIA: Autenticacion JWT

```
Usuario    Frontend           Backend (Spring)
   |           |                    |
   |--admin/-->|                    |
   |  admin123 |--POST /api/auth/login
   |           |   {username, pwd}  |--Verifica BCrypt
   |           |                    |--Genera JWT HS256
   |           |<--{token: "eyJ..."}-+
   |           |--localStorage.setItem(token)
   |           |                    |
   |--Request->|                    |
   |           |--GET /api/productos|
   |           |  Bearer: eyJ...    |--JwtFilter valida
   |           |<--[datos JSON]-----+
```

---

## 8. COMPONENTES FRONTEND

### Arbol de Componentes

```
App.jsx
|-- ToastProvider               Notificaciones (exito/error/info)
|-- Login                       Pantalla de autenticacion
|-- AppShell                    Layout principal (post-login)
    |-- Sidebar                 Navegacion lateral colapsable
    |   |-- NavItems            Dashboard/Productos/Inventario/
    |   |                       Ventas/Reportes/Clientes
    |   |-- UserCard + Logout
    |-- Topbar                  Barra superior
    |   |-- Titulo de pagina
    |   |-- Busqueda global
    |   |-- Badge notificaciones
    |   |-- Avatar usuario
    |-- PageContent             Contenido dinamico
        |-- Dashboard
        |   |-- KPIGrid (4 tarjetas KPI)
        |   |-- BarChart (ventas vs objetivo mensual)
        |   |-- DonutChart (distribucion por categoria)
        |   |-- Top 5 productos mas vendidos
        |   |-- Panel alertas stock bajo
        |-- Productos
        |   |-- Filtros (busqueda/marca/categoria)
        |   |-- Tabla (foto/precio+IGV/stock/acciones)
        |   |-- Modal agregar/editar (specs tecnicas)
        |   |-- Modal detalle del producto
        |-- Inventario
        |   |-- KPIs (total/unidades/valor S/)
        |   |-- Tabla stock con barra de progreso
        |   |-- Historial de movimientos
        |   |-- Modal registrar movimiento
        |-- Ventas (POS)
        |   |-- Catalogo de productos con filtros
        |   |-- Carrito + busqueda cliente por DNI
        |   |-- Panel pago (subtotal/IGV 18%/total)
        |   |-- Boleta imprimible (modal)
        |   |-- Historial de ventas
        |-- Reportes
        |   |-- KPIs de ventas
        |   |-- LineChart ingresos mensuales
        |   |-- BarChart ventas mensuales
        |   |-- Ranking productos mas rentables
        |   |-- Distribucion metodos de pago (%)
        |   |-- Exportar CSV completo
        |-- Clientes
            |-- KPIs (total/activos/ventas S/)
            |-- Tabla con total gastado
            |-- Modal cliente con DNI/RUC automatico
```

### Componentes Reutilizables

| Componente | Props | Descripcion |
|-----------|-------|-------------|
| StockBadge | stock, min | Badge verde/amarillo/rojo |
| EstadoBadge | estado | Activo/Inactivo/Categorias |
| SearchInput | value, onChange | Input con icono lupa |
| BarChart | data, color, color2 | Grafica barras SVG |
| LineChart | data | Grafica lineas SVG |
| DonutChart | segments | Grafica donut SVG |

---

## 9. API REST — ENDPOINTS

### Autenticacion
| Metodo | Endpoint | Auth |
|--------|---------|------|
| POST | /api/auth/login | No |

### Productos
| Metodo | Endpoint | Auth |
|--------|---------|------|
| GET | /api/productos | Si (JWT) |
| GET | /api/productos/{id} | Si |
| POST | /api/productos | Si |
| PUT | /api/productos/{id} | Si |
| DELETE | /api/productos/{id} (baja logica) | Si |
| GET | /api/productos/alertas | Si |

### Ventas
| Metodo | Endpoint | Auth |
|--------|---------|------|
| GET | /api/ventas | Si |
| POST | /api/ventas | Si |
| GET | /api/ventas/{id} | Si |

### Usuarios
| Metodo | Endpoint | Auth |
|--------|---------|------|
| GET | /api/usuarios | Si |
| POST | /api/usuarios | Si |
| PUT | /api/usuarios/{id} | Si |
| DELETE | /api/usuarios/{id} | Si |

### Facturas
| Metodo | Endpoint | Auth |
|--------|---------|------|
| GET | /api/facturas | Si |
| POST | /api/facturas | Si |

### Reportes
| Metodo | Endpoint | Auth |
|--------|---------|------|
| GET | /api/reportes/alertas-stock | Si |
| GET | /api/reportes/informe-rendimiento | Si |

### Serverless Vercel (nuevos)
| Metodo | Endpoint | Descripcion |
|--------|---------|-------------|
| GET | /api/dni?numero=12345678 | Proxy a RENIEC (apis.net.pe) |
| GET | /api/ruc?numero=20000000001 | Proxy a SUNAT (apis.net.pe) |

---

## 10. SEGURIDAD

### Autenticacion JWT
- Algoritmo: HS256 (HMAC-SHA256)
- Secret: env var JWT_SECRET (nunca en codigo fuente)
- Filtro: JwtFilter intercepta todas las requests a /api/**

### Reglas Spring Security
```
/api/auth/**   publico (sin token requerido)
/api/**        requiere Bearer JWT valido
CSRF           deshabilitado (API REST stateless)
CORS           https://fintechmovil.vercel.app + localhost:5173
```

### Contrasenas
- Almacenadas con BCrypt (salt automatico, factor 10)
- Sin texto plano en la base de datos

---

## 11. PRUEBAS Y CALIDAD

### Estadisticas Tests Backend

| Metrica | Valor |
|---------|-------|
| Total tests | 196 |
| Tests pasando | 196 (100%) |
| Archivos de test | 31 |
| Cobertura instrucciones | 85.4% |
| Quality Gate SonarQube | PASSED (>= 80%) |

### Tipos de pruebas
- Unit tests con Mockito (servicios y logica de negocio)
- Integration tests con MockMvc (controladores REST)
- Security tests (endpoints protegidos y publicos)
- Edge cases (stock insuficiente, carrito vacio, producto no encontrado)

### Exclusiones JaCoCo
- Entities (modelo/)
- DTOs (dtos/)
- Mappers (mappers/)
- Repositorios (repositorio/)

### Load Testing K6

| Script | Tipo | VUs | Duracion |
|--------|------|-----|---------|
| smoke-test.js | Smoke | 1 VU | 1 min |
| load-test.js | Load | 50 VUs | 5 min |
| stress-test.js | Stress | hasta 200 VUs | 10 min |

Resultado: API soporta ~150 VUs simultaneos sin degradacion.
Tiempo de respuesta p95 menor a 800ms en carga normal.

---

## 12. DESPLIEGUE (DEPLOYMENT)

### Railway — Backend

Variables de entorno requeridas en Railway:
```
SPRING_DATASOURCE_URL=jdbc:mysql://<mysql-host>:3306/railway
SPRING_DATASOURCE_USERNAME=root
SPRING_DATASOURCE_PASSWORD=***
JWT_SECRET=<string-aleatoria-larga>
SPRING_JPA_DDL_AUTO=update
```

URL produccion: https://fintechmovil.up.railway.app

### Vercel — Frontend

Variables de entorno en Vercel Dashboard:
```
VITE_API_URL=https://fintechmovil.up.railway.app/api
VITE_APIS_TOKEN=sk_16558.***  (queda en bundle del cliente)
APIS_TOKEN=sk_16558.***       (solo para serverless functions)
```

URL produccion: https://fintechmovil.vercel.app

### Flujo CI/CD Local (Jenkins + Docker)

```
Commit
  |
  v
Jenkins (webhook GitHub)
  |-- mvnw clean package
  |-- mvnw test
  |-- mvnw jacoco:report
  |-- mvnw sonar:sonar
  |
  v
Quality Gate >= 80%?
  Si --> OK (build exitoso)
  No --> Build fallido (bloquea merge)
```

---

## 13. BASE DE DATOS — MODELO RELACIONAL

```
productos
  id (PK) | marca | modelo | precio | stock | stock_minimo
  imagen_url | caracteristicas_id (FK) | activo

caracteristicas
  id (PK) | procesador | ram | almacenamiento
  bateria | camaras | pantalla | activo

usuarios
  id (PK) | username (UNIQUE) | password (BCrypt)
  nombre | apellido | email | rol | activo

ventas
  id (PK) | fecha | total | estado | usuario_id (FK)

facturas
  id (PK) | fecha_emision | total | tipo_documento
  venta_id (FK) | activo

detalle_factura
  id (PK) | cantidad | precio_unitario | subtotal
  producto_id (FK) | factura_id (FK)

pagos
  id (PK) | monto | metodo_pago | fecha_pago | factura_id (FK)
```

---

## 14. FUNCIONALIDADES IMPLEMENTADAS

### Dashboard
- [x] Tarjetas KPI (ventas del dia, ingresos mes, stock total, alertas)
- [x] Grafica barras: ventas reales vs objetivo mensual
- [x] Grafica donut: distribucion por categoria (Gama Alta/Media/Basica)
- [x] Top 5 productos mas vendidos
- [x] Alertas de stock bajo/agotado

### Gestion de Productos
- [x] CRUD completo (crear/editar/desactivar/ver detalle)
- [x] Filtros (busqueda, marca, categoria)
- [x] Modal con especificaciones tecnicas (procesador, RAM, etc.)
- [x] Badge de stock (verde/amarillo/rojo)
- [x] Exportar catalogo a CSV
- [x] Sincronizacion con backend (fallback a mock)

### Control de Inventario
- [x] Tabla de estado con barra de progreso visual
- [x] Registrar movimientos (entrada/salida/ajuste)
- [x] Historial de movimientos con filtro
- [x] KPI valor total del inventario en soles

### Ventas / POS (Punto de Venta)
- [x] Catalogo visual con tarjetas de productos
- [x] Carrito con control de stock en tiempo real
- [x] Busqueda de cliente por DNI (RENIEC)
- [x] Metodos de pago (Efectivo/Tarjeta/Yape/Plin/Transferencia)
- [x] Calculo automatico IGV (18%)
- [x] Boleta de venta imprimible
- [x] Historial de ventas

### Reportes y Estadisticas
- [x] KPI ingresos totales y ticket promedio
- [x] Grafica ingresos mensuales (linea)
- [x] Grafica ventas mensuales (barras)
- [x] Ranking productos mas rentables (precio x ventas x IGV)
- [x] Metodos de pago con porcentajes y barras
- [x] Exportar reporte completo CSV (productos + ventas)

### Gestion de Clientes
- [x] CRUD completo de clientes
- [x] Busqueda automatica por DNI (RENIEC via /api/dni)
- [x] Busqueda automatica por RUC (SUNAT via /api/ruc)
- [x] Total gastado por cliente

### Autenticacion
- [x] Login con JWT
- [x] Modo fallback mock (sin backend disponible)
- [x] Logout con limpieza de localStorage
- [x] Hint de credenciales en pantalla de login

---

## 15. BUGS CORREGIDOS EN ESTE SPRINT

### Bug 1: "la e" en los numeros (CRITICO)

**Sintoma**: Numeros monetarios se mostraban con notacion incorrecta
dependiendo del locale del navegador del usuario en produccion:
- "155.000" en vez de "155,000" (pareciera numero decimal)
- "1.55e+5" en entornos sin soporte de locale peruano

**Causa**: `toLocaleString("es-PE")` no esta soportado uniformemente.
En navegadores sin ICU completo, cae a "es-ES" (Espana) que usa
punto como separador de miles, o devuelve notacion cientifica.

**Fix**: Se creo la funcion helper `fmt(n, dec)` con implementacion
manual usando regex. No depende del soporte de locale del navegador.

```javascript
const fmt = (n, dec = 0) => {
  const num = Number(n);
  if (!isFinite(num)) return '0';
  const parts = num.toFixed(dec).split('.');
  parts[0] = parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ',');
  return dec > 0 ? parts.join('.') : parts[0];
};
```

Resultado antes: S/ 155.000 o S/ 1.55e+5
Resultado despues: S/ 155,000

Se reemplazaron 20+ llamadas a toLocaleString() y toFixed()
en todos los contextos de display (precios, totales, KPIs, boletas,
historial de ventas, reportes y clientes).

### Bug 2: Toast sin estilos (MENOR)

**Sintoma**: El mensaje "Sin conexion al backend" aparecia sin
color de fondo ni icono visible.

**Causa**: Se usaba el tipo "warn" pero ToastProvider solo tiene
los tipos: "success", "error", "info".

**Fix**: Cambiar "warn" por "info" en 2 lugares del codigo.

### Bug 3: Campos null del backend (MODERADO)

**Sintoma**: Al conectar con el backend real, campos como
stockMinimo, precio o stock podian llegar como null de la BD,
causando NaN en calculos y errores de JavaScript.

**Fix**: Agregar conversion explicita en el mapper del backend:
- `precio: Number(p.precio) || 0`
- `stock: Number(p.stock) || 0`
- `stockMinimo: Number(p.stockMinimo) ?? 3`

### Bug 4: DNI/RUC no funciona en Vercel (FUNCIONAL)

**Sintoma**: Al buscar DNI o RUC en el formulario de clientes,
siempre aparece "Error de conexion con la API".

**Causa**: El rewrite catch-all de vercel.json interceptaba las
rutas /api/dni y /api/ruc y retornaba el HTML de la SPA, no
una respuesta JSON de alguna API.

**Fix**: Se crearon funciones serverless de Vercel:
- `frontend/api/dni.js` -> Proxy a apis.net.pe/v2/reniec/dni
- `frontend/api/ruc.js` -> Proxy a apis.net.pe/v2/sunat/ruc

Las funciones serverless tienen mayor prioridad que los rewrites
en Vercel, por lo que no fue necesario modificar vercel.json.

**Configuracion adicional requerida en Vercel Dashboard**:
Agregar la variable de entorno `APIS_TOKEN=sk_<tu-token>` (sin
prefijo VITE_ para que no quede expuesto en el bundle del cliente).

---

## 16. ESTRUCTURA DEL PROYECTO

```
FINTECHMOVIL_TODO_FINAL/
|-- FINTECHMOVIL/
|   |-- backend/
|   |   |-- src/main/java/com/example/techmovil/
|   |   |   |-- config/        SecurityConfig, JwtService, CorsConfig
|   |   |   |-- control/       7 REST Controllers
|   |   |   |-- dtos/          Data Transfer Objects
|   |   |   |-- excepciones/   GlobalExceptionHandler
|   |   |   |-- mappers/       MapStruct
|   |   |   |-- modelo/        7 JPA Entities
|   |   |   |-- repositorio/   Spring Data JPA
|   |   |   |-- servicio/      Business Logic (interface + impl)
|   |   |-- src/test/          31 archivos, 196 tests
|   |   |-- K6/
|   |   |   |-- smoke-test.js
|   |   |   |-- load-test.js
|   |   |   |-- stress-test.js
|   |   |-- docker-compose.yml  MySQL+SonarQube+Jenkins local
|   |   |-- Dockerfile
|   |   |-- railway.json
|   |   |-- EJECUTAR.ps1        Pipeline automatico local
|   |-- frontend/
|       |-- api/                Vercel serverless (NUEVO)
|       |   |-- dni.js          Proxy RENIEC
|       |   |-- ruc.js          Proxy SUNAT
|       |-- src/
|       |   |-- main.jsx
|       |   |-- App.jsx         App completa ~1650 lineas
|       |-- vercel.json
|       |-- .env.example
|-- INFORME_PROYECTO_FINTECHMOVIL.md    <- este archivo
|-- CLAUDE.md
```

---

## 17. METRICAS DEL PROYECTO

| Metrica | Valor |
|---------|-------|
| Lineas de codigo Backend | ~3,500 |
| Lineas de codigo Frontend | ~1,650 |
| Tests unitarios + integracion | 196 |
| Cobertura instrucciones (JaCoCo) | 85.4% |
| Endpoints REST documentados | 22+ |
| Modulos de la aplicacion | 6 |
| Scripts K6 de carga | 3 |
| VUs maximos soportados | ~150 |
| Entidades JPA | 7 |
| Controladores REST | 7 |
| Bugs corregidos en este sprint | 4 |

---

*Informe tecnico — FinTechMovil ERP — Junio 2026*
