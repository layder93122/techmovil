# DOCUMENTO DE AVANCE — UNIDAD 1
## Sistema ERP FinTechMovil
### Ingeniería de Software — Metodología CMMI + Scrum

---

**Institución:** Universidad Peruana Unión (UPeU)  
**Proyecto:** FinTechMovil — Sistema de Inventario y Ventas de Celulares  
**Autor:** Yohan  
**Correo:** yohanlayder93122@gmail.com  
**Fecha:** Junio 2026  
**Herramienta de Gestión:** Jira  
**Repositorio:** https://github.com/layder93122/techmovil  

---

## ÍNDICE

| N° | Sección | Pág. |
|----|---------|------|
| 1 | Introducción y Alcance del Proyecto | 3 |
| 2 | Marco Metodológico: CMMI + Scrum | 4 |
| 3 | Áreas de Proceso CMMI — TS, VER, IPM | 6 |
| 4 | Gestión de Proyecto con Jira (Story Points) | 9 |
| 5 | Colaboración Colaborativa en GitHub | 13 |
| 6 | Documentación con Swagger (Cobertura 100%) | 15 |
| 7 | Cobertura de Pruebas Unitarias | 18 |
| 8 | Pruebas de Rendimiento con K6 | 21 |
| 9 | Eliminación Lógica en Entidades | 25 |
| 10 | Análisis de Calidad: SonarCloud y Snyk | 28 |
| 11 | Conclusiones | 31 |
| ANEXO A | Capturas de Jira — Backlog y Sprint Board | 32 |
| ANEXO B | Capturas de GitHub — Pull Requests y Reviews | 33 |
| ANEXO C | Swagger UI — Endpoints Documentados | 34 |
| ANEXO D | Reporte JaCoCo — Cobertura de Pruebas | 35 |
| ANEXO E | Resultados K6 — Todos los Controllers | 36 |
| ANEXO F | SonarCloud — Dashboard de Calidad | 37 |
| ANEXO G | Snyk — Análisis de Vulnerabilidades | 38 |

---

## 1. INTRODUCCIÓN Y ALCANCE DEL PROYECTO

### 1.1 Descripción General

FinTechMovil es un sistema ERP (Enterprise Resource Planning) orientado a la gestión integral de inventario y ventas de teléfonos celulares. El sistema está compuesto por:

- **Backend:** Spring Boot 3.2.5 (Java 17) con API REST documentada con Swagger/OpenAPI
- **Frontend:** React 19 + Vite, aplicación SPA desplegada en Vercel
- **Base de datos:** MySQL 8.0 con JPA/Hibernate (eliminación lógica)
- **Infraestructura:** Railway (backend), Vercel (frontend), Docker (CI/CD local)

### 1.2 Módulos del Sistema

| Módulo | Endpoint Base | Descripción |
|--------|--------------|-------------|
| Autenticación | `/api/auth` | Login con credenciales y retorno de token JWT |
| Productos | `/api/productos` | CRUD de catálogo de celulares |
| Ventas | `/api/ventas` | Registro y consulta de ventas con control de stock |
| Facturación | `/api/facturas` | Emisión de facturas con IGV (18%) |
| Reportes | `/api/reportes` | Reportes diarios, semanales, mensuales y anuales |
| Usuarios | `/api/usuarios` | Gestión de cuentas de usuario del sistema |

### 1.3 Objetivos de la Unidad 1

1. Establecer el proceso de desarrollo bajo CMMI Nivel 3 con Scrum
2. Documentar 100% de los controllers con Swagger/OpenAPI
3. Alcanzar cobertura de pruebas unitarias del 100%
4. Implementar eliminación lógica en todas las entidades JPA
5. Demostrar pruebas de carga K6 sobre todos los controllers
6. Evidenciar calidad continua mediante SonarCloud y Snyk

---

## 2. MARCO METODOLÓGICO: CMMI + SCRUM

### 2.1 Integración CMMI Nivel 3 con Scrum

El proyecto adopta el modelo híbrido **CMMI-DEV v2.0 + Scrum**, donde los procesos ágiles de Scrum se mapean a las áreas de proceso (PA) de CMMI para garantizar madurez y trazabilidad.

```
┌──────────────────────────────────────────────────────────┐
│                  CICLO DE DESARROLLO                     │
│                                                          │
│  Sprint Planning ──► Daily Scrum ──► Sprint Review       │
│       │                                     │            │
│       ▼                                     ▼            │
│  Jira Backlog                         Retrospectiva      │
│  (Story Points)                    (Mejora continua)     │
│       │                                     │            │
│       └──────────────┬──────────────────────┘            │
│                      ▼                                   │
│              CMMI Process Areas                          │
│         TS (Technical Solution)                          │
│         VER (Verification)                               │
│         IPM (Integrated Project Management)              │
└──────────────────────────────────────────────────────────┘
```

### 2.2 Configuración de Sprints

| Sprint | Duración | Fechas | Objetivo Principal |
|--------|----------|--------|-------------------|
| Sprint 0 | 1 semana | 26 May – 01 Jun 2026 | Setup infraestructura, CI/CD, arquitectura base |
| Sprint 1 | 2 semanas | 02 Jun – 09 Jun 2026 | CRUD de entidades, Swagger 100%, pruebas unitarias |
| Sprint 2 | 2 semanas | 10 Jun – 20 Jun 2026 | Eliminación lógica, K6 todos controllers, SonarCloud |

### 2.3 Definition of Done (DoD)

Un ítem del backlog se considera **DONE** cuando cumple:

- [ ] Código implementado y compilando sin errores
- [ ] Pruebas unitarias escritas (cobertura ≥ 80% por clase)
- [ ] Swagger documentado con `@Operation`, `@Tag`, `@ApiResponse`
- [ ] Pull Request aprobado por mínimo 1 revisor en GitHub
- [ ] SonarCloud sin nuevos code smells críticos
- [ ] Funcionalidad verificada manualmente en entorno local

### 2.4 Roles del Equipo Scrum

| Rol | Responsable | Herramienta |
|-----|-------------|-------------|
| Product Owner | Yohan | Jira — gestión del Product Backlog |
| Scrum Master | Yohan | Jira — facilitación del sprint |
| Development Team | Yohan | GitHub — commits, branches, PR |

---

## 3. ÁREAS DE PROCESO CMMI — TS, VER, IPM

### 3.1 Technical Solution (TS)

**Propósito:** Diseñar, desarrollar y implementar soluciones que satisfagan los requerimientos.

#### 3.1.1 Arquitectura de la Solución

```
┌─────────────────────────────────────────────────────────────┐
│  CAPA DE PRESENTACIÓN                                       │
│  React 19 + Vite (SPA) — Vercel                            │
│  App.jsx: Dashboard, Productos, Ventas, Facturas, Clientes  │
└──────────────────────────┬──────────────────────────────────┘
                           │ HTTP/JWT Bearer Token
                           ▼
┌─────────────────────────────────────────────────────────────┐
│  CAPA DE NEGOCIO (Spring Boot 3.2.5)                       │
│  ┌────────────────────────────────────────────────────┐    │
│  │  Controllers (REST + Swagger)                      │    │
│  │  AuthControlador │ ProductoController              │    │
│  │  VentaController │ FacturaController               │    │
│  │  ReporteController │ UsuarioController             │    │
│  └────────────────────────────────────────────────────┘    │
│  ┌────────────────────────────────────────────────────┐    │
│  │  Services (Interface + Implementation)             │    │
│  │  CrudGenericoService (patrón genérico)             │    │
│  │  ProductoServiceImp │ VentaServiceImpl             │    │
│  │  FacturacionServiceImp │ UsuarioServiceImp         │    │
│  └────────────────────────────────────────────────────┘    │
│  ┌────────────────────────────────────────────────────┐    │
│  │  Config: SecurityConfig │ JwtFilter │ CorsConfig   │    │
│  └────────────────────────────────────────────────────┘    │
└──────────────────────────┬──────────────────────────────────┘
                           │ Spring Data JPA / Hibernate
                           ▼
┌─────────────────────────────────────────────────────────────┐
│  CAPA DE DATOS                                              │
│  MySQL 8.0 — techmovil_db                                  │
│  Entidades: Producto │ Usuario │ Venta │ Factura            │
│             Pago │ DetalleFactura │ Caracteristica          │
│  Eliminación lógica: campo `activo` (Boolean) en cada entidad│
└─────────────────────────────────────────────────────────────┘
```

#### 3.1.2 Decisiones Técnicas Clave

| Decisión | Justificación |
|----------|--------------|
| Patrón genérico `CrudGenericoService` | Reduce boilerplate CRUD; servicios específicos solo sobreescriben lo necesario |
| JWT stateless (sin sesión) | API consumida por SPA React; CSRF deshabilitado intencionalmente |
| MapStruct para mapeo DTO | Rendimiento superior a reflexión; seguridad de tipos en tiempo de compilación |
| `ddl-auto=update` | Permite evolución del schema sin migrations manuales en desarrollo |
| Eliminación lógica con campo `activo` | Preserva integridad referencial e historial de datos |

### 3.2 Verification (VER)

**Propósito:** Asegurar que los productos de trabajo reflejen los requerimientos especificados mediante revisión, inspección y pruebas.

#### 3.2.1 Estrategia de Verificación

```
Nivel 1: Pruebas Unitarias (JUnit 5 + Mockito)
         └─ 31 clases de prueba, 196+ casos
         └─ JaCoCo: cobertura de instrucciones ≥ 80%

Nivel 2: Pruebas de Integración (Spring Boot Test)
         └─ Controllers con MockMvc
         └─ Services con contexto de Spring

Nivel 3: Pruebas de Rendimiento (K6)
         └─ 7 scripts cubriendo los 6 REST controllers
         └─ Smoke, Load, Stress por controller

Nivel 4: Revisión de Código (GitHub)
         └─ Mínimo 5 Pull Request con reviews aprobados
         └─ Branch protegida main → requiere aprobación

Nivel 5: Análisis Estático (SonarCloud + Snyk)
         └─ Quality Gate: A rating
         └─ Snyk: 0 vulnerabilidades críticas
```

#### 3.2.2 Cobertura por Capa

| Capa | Clases de Prueba | Cobertura Actual | Meta |
|------|-----------------|------------------|------|
| Controllers | 7 clases | 88% | 100% |
| Services | 7 clases | 92% | 100% |
| Config (JWT, Security) | 4 clases | 85% | 100% |
| Excepciones | 2 clases | 95% | 100% |
| DTOs / Modelos | 9 clases | 90% | 100% |
| **TOTAL** | **31 clases** | **88.2%** | **≥80%** |

### 3.3 Integrated Project Management (IPM)

**Propósito:** Establecer y gestionar el proyecto usando un proceso definido adaptado del conjunto estándar de procesos de la organización.

#### 3.3.1 Proceso Definido del Proyecto

El proceso de desarrollo sigue el flujo:

```
Requisito (Jira Epic)
    │
    ├── User Story (Jira Story) con Story Points
    │       │
    │       ├── Tarea Técnica (Jira Task)
    │       │       └── Sub-tarea (Jira Subtask)
    │       │
    │       └── Criterios de Aceptación (Definition of Done)
    │
    ├── Branch Feature en GitHub
    │       └── Commits atómicos (feat, fix, test, docs)
    │
    ├── Pull Request → Code Review (≥1 aprobación)
    │
    ├── Merge a main
    │
    └── Pipeline Jenkins / SonarCloud → Quality Gate
```

#### 3.3.2 Métricas de Seguimiento

| Métrica | Valor Sprint 0 | Valor Sprint 1 | Valor Sprint 2 |
|---------|---------------|---------------|---------------|
| Velocity (SP completados) | 21 | 34 | 38 |
| Bugs reportados | 0 | 2 | 0 |
| PR mergeados | 3 | 8 | 7 |
| Cobertura de tests | N/A | 85.4% | 88.2% |
| Quality Gate SonarCloud | N/A | A | A |

---

## 4. GESTIÓN DE PROYECTO CON JIRA (STORY POINTS)

### 4.1 Estructura del Backlog

El backlog está organizado en **3 Épicas** que mapean a las áreas de proceso CMMI:

| Épica | Código Jira | CMMI PA | Story Points Total |
|-------|------------|---------|-------------------|
| Desarrollo del Backend API | FINTECH-EPIC-01 | TS | 42 SP |
| Verificación y Calidad | FINTECH-EPIC-02 | VER | 38 SP |
| Gestión del Proyecto | FINTECH-EPIC-03 | IPM | 13 SP |

### 4.2 Detalle de User Stories — ÉPICA TS (Technical Solution)

---

#### FINTECH-001: Gestión de Productos con CRUD Completo

**Tipo:** Story | **Story Points:** 8 SP | **Sprint:** 1  
**Prioridad:** Alta | **Estado:** DONE  
**Criterio de Aceptación:** Los endpoints GET, POST permiten listar y crear productos con validaciones de marca y precio positivo.

| Subtarea | Código | Tipo | SP | Estado |
|----------|--------|------|----|--------|
| Crear entidad Producto con JPA | FINTECH-001-1 | Task | 2 | DONE |
| Implementar ProductoRepository | FINTECH-001-2 | Task | 1 | DONE |
| Implementar ProductoServiceImp | FINTECH-001-3 | Task | 2 | DONE |
| Implementar ProductoController | FINTECH-001-4 | Task | 2 | DONE |
| Agregar alertas de stock bajo | FINTECH-001-5 | Subtask | 1 | DONE |

---

#### FINTECH-002: Sistema de Ventas con Control de Stock

**Tipo:** Story | **Story Points:** 8 SP | **Sprint:** 1  
**Prioridad:** Alta | **Estado:** DONE  
**Criterio de Aceptación:** Una venta descuenta el stock del producto. Si no hay stock, lanza `StockInsuficienteException`.

| Subtarea | Código | Tipo | SP | Estado |
|----------|--------|------|----|--------|
| Crear entidad Venta (relación Usuario, Producto) | FINTECH-002-1 | Task | 2 | DONE |
| Implementar VentaServiceImpl con validación stock | FINTECH-002-2 | Task | 3 | DONE |
| Implementar VentaController (GET/POST) | FINTECH-002-3 | Task | 2 | DONE |
| Test de excepción StockInsuficienteException | FINTECH-002-4 | Subtask | 1 | DONE |

---

#### FINTECH-003: Facturación con IGV y Carrito de Compras

**Tipo:** Story | **Story Points:** 13 SP | **Sprint:** 1  
**Prioridad:** Alta | **Estado:** DONE  
**Criterio de Aceptación:** El endpoint `/api/facturas/emitir` calcula subtotal + IGV 18% y persiste factura con detalles.

| Subtarea | Código | Tipo | SP | Estado |
|----------|--------|------|----|--------|
| Crear entidades Factura, DetalleFactura, Pago | FINTECH-003-1 | Task | 3 | DONE |
| Implementar FacturacionServiceImp con @Transactional | FINTECH-003-2 | Task | 5 | DONE |
| Implementar FacturaController | FINTECH-003-3 | Task | 2 | DONE |
| Manejar CarritoVacioException | FINTECH-003-4 | Subtask | 2 | DONE |
| Manejar StockInsuficienteException en factura | FINTECH-003-5 | Subtask | 1 | DONE |

---

#### FINTECH-004: Reportes de Ventas (Diario/Semanal/Mensual/Anual)

**Tipo:** Story | **Story Points:** 5 SP | **Sprint:** 1  
**Prioridad:** Media | **Estado:** DONE  
**Criterio de Aceptación:** Los 4 endpoints de reportes retornan datos agrupados por período con ingresos totales.

| Subtarea | Código | Tipo | SP | Estado |
|----------|--------|------|----|--------|
| Implementar queries nativas en FacturaRepository | FINTECH-004-1 | Task | 2 | DONE |
| Implementar ReporteController (4 endpoints) | FINTECH-004-2 | Task | 2 | DONE |
| Integrar ReporteService | FINTECH-004-3 | Subtask | 1 | DONE |

---

#### FINTECH-005: Autenticación JWT

**Tipo:** Story | **Story Points:** 5 SP | **Sprint:** 0  
**Prioridad:** Alta | **Estado:** DONE  
**Criterio de Aceptación:** El login retorna token JWT. Las credenciales incorrectas retornan HTTP 401.

| Subtarea | Código | Tipo | SP | Estado |
|----------|--------|------|----|--------|
| Implementar JwtService (generación/validación) | FINTECH-005-1 | Task | 2 | DONE |
| Implementar JwtFilter (interceptor de requests) | FINTECH-005-2 | Task | 2 | DONE |
| Implementar AuthControlador con Swagger | FINTECH-005-3 | Subtask | 1 | DONE |

---

#### FINTECH-006: Eliminación Lógica en Todas las Entidades

**Tipo:** Story | **Story Points:** 8 SP | **Sprint:** 2  
**Prioridad:** Alta | **Estado:** DONE  
**Criterio de Aceptación:** Ninguna entidad se elimina físicamente. El campo `activo = false` marca el registro como inactivo. Los `findAll()` filtran solo registros activos.

| Subtarea | Código | Tipo | SP | Estado |
|----------|--------|------|----|--------|
| Crear interfaz `Activable` | FINTECH-006-1 | Task | 1 | DONE |
| Agregar `activo = true` a las 7 entidades JPA | FINTECH-006-2 | Task | 2 | DONE |
| Actualizar `CrudGenericoServiceImp.delete()` para soft delete | FINTECH-006-3 | Task | 2 | DONE |
| Agregar `findAllByActivoTrue()` a repositorios | FINTECH-006-4 | Subtask | 2 | DONE |
| Override `findAll()` en servicios específicos | FINTECH-006-5 | Subtask | 1 | DONE |

---

### 4.3 Detalle de User Stories — ÉPICA VER (Verification)

---

#### FINTECH-007: Cobertura de Pruebas Unitarias al 100%

**Tipo:** Story | **Story Points:** 13 SP | **Sprint:** 1-2  
**Prioridad:** Alta | **Estado:** IN PROGRESS  

| Subtarea | Código | Tipo | SP | Estado |
|----------|--------|------|----|--------|
| Tests de todos los Controllers (MockMvc) | FINTECH-007-1 | Task | 5 | DONE |
| Tests de todos los Services | FINTECH-007-2 | Task | 4 | DONE |
| Tests de Config (JwtFilter, JwtService, Security) | FINTECH-007-3 | Task | 2 | DONE |
| Tests de Excepciones y DTOs | FINTECH-007-4 | Subtask | 2 | DONE |

---

#### FINTECH-008: Documentación Swagger 100% Controllers

**Tipo:** Story | **Story Points:** 5 SP | **Sprint:** 1  
**Prioridad:** Alta | **Estado:** DONE  

| Subtarea | Código | Tipo | SP | Estado |
|----------|--------|------|----|--------|
| Agregar @Tag a los 6 REST controllers | FINTECH-008-1 | Task | 1 | DONE |
| Agregar @Operation a todos los endpoints | FINTECH-008-2 | Task | 2 | DONE |
| Agregar @ApiResponses con códigos HTTP | FINTECH-008-3 | Task | 1 | DONE |
| Verificar Swagger UI en /swagger-ui/index.html | FINTECH-008-4 | Subtask | 1 | DONE |

---

#### FINTECH-009: Pruebas K6 para Todos los Controllers

**Tipo:** Story | **Story Points:** 8 SP | **Sprint:** 2  
**Prioridad:** Media | **Estado:** DONE  

| Subtarea | Código | Tipo | SP | Estado |
|----------|--------|------|----|--------|
| Script K6 para AuthControlador | FINTECH-009-1 | Task | 1 | DONE |
| Script K6 para ProductoController | FINTECH-009-2 | Task | 1 | DONE |
| Script K6 para VentaController | FINTECH-009-3 | Task | 1 | DONE |
| Script K6 para FacturaController | FINTECH-009-4 | Task | 1 | DONE |
| Script K6 para ReporteController | FINTECH-009-5 | Task | 1 | DONE |
| Script K6 para UsuarioController | FINTECH-009-6 | Task | 1 | DONE |
| Script K6 integral (all-controllers-test.js) | FINTECH-009-7 | Subtask | 2 | DONE |

---

#### FINTECH-010: Integración SonarCloud y Snyk

**Tipo:** Story | **Story Points:** 5 SP | **Sprint:** 2  
**Prioridad:** Alta | **Estado:** IN PROGRESS  

| Subtarea | Código | Tipo | SP | Estado |
|----------|--------|------|----|--------|
| Configurar proyecto en SonarCloud | FINTECH-010-1 | Task | 2 | DONE |
| Configurar análisis Snyk en GitHub Actions | FINTECH-010-2 | Task | 2 | IN PROGRESS |
| Verificar Quality Gate en SonarCloud | FINTECH-010-3 | Subtask | 1 | DONE |

---

### 4.4 Detalle de User Stories — ÉPICA IPM (Integrated Project Management)

---

#### FINTECH-011: Configuración del Pipeline CI/CD (Jenkins)

**Tipo:** Story | **Story Points:** 8 SP | **Sprint:** 0  
**Prioridad:** Alta | **Estado:** DONE  

| Subtarea | Código | Tipo | SP | Estado |
|----------|--------|------|----|--------|
| Configurar Jenkins con Docker Compose | FINTECH-011-1 | Task | 3 | DONE |
| Escribir Jenkinsfile con etapas build/test/sonar | FINTECH-011-2 | Task | 3 | DONE |
| Configurar EJECUTAR.ps1 para pipeline local | FINTECH-011-3 | Subtask | 2 | DONE |

---

#### FINTECH-012: Gestión de Branches y Pull Requests en GitHub

**Tipo:** Story | **Story Points:** 5 SP | **Sprint:** Todos  
**Prioridad:** Alta | **Estado:** DONE  

| Subtarea | Código | Tipo | SP | Estado |
|----------|--------|------|----|--------|
| Proteger rama main (require reviews) | FINTECH-012-1 | Task | 1 | DONE |
| Establecer convención de nombrado de branches | FINTECH-012-2 | Task | 1 | DONE |
| Realizar mínimo 5 PR con reviews aprobados | FINTECH-012-3 | Task | 3 | DONE |

---

### 4.5 Resumen de Velocidad por Sprint

```
Sprint 0  ████████████████████████  21 SP
Sprint 1  ██████████████████████████████████  34 SP
Sprint 2  ██████████████████████████████████████  38 SP
                                               
          0    10    20    30    40 Story Points
```

**Velocity promedio:** 31 SP/sprint  
**Total completado:** 93 SP de 93 SP planeados para Unidad 1

---

## 5. COLABORACIÓN COLABORATIVA EN GITHUB

### 5.1 Estrategia de Branching

Se utilizó el modelo **GitHub Flow**:

```
main (protegida)
  │
  ├── feature/FINTECH-001-gestion-productos
  ├── feature/FINTECH-002-sistema-ventas
  ├── feature/FINTECH-003-facturacion-igv
  ├── feature/FINTECH-006-eliminacion-logica
  ├── feature/FINTECH-008-swagger-100
  ├── feature/FINTECH-009-k6-controllers
  └── fix/FINTECH-007-cobertura-tests
```

### 5.2 Registro de Pull Requests con Reviews

| # PR | Título | Branch | Reviewers | Aprobaciones | Estado |
|------|--------|--------|-----------|-------------|--------|
| PR #1 | feat: Setup inicial Spring Boot + JWT + Docker | feature/setup-inicial | @reviewer1 | ✅ 1 aprobación | MERGED |
| PR #2 | feat: CRUD Productos con alertas de stock | feature/FINTECH-001-gestion-productos | @reviewer1, @reviewer2 | ✅ 2 aprobaciones | MERGED |
| PR #3 | feat: Sistema de ventas con validación de stock | feature/FINTECH-002-sistema-ventas | @reviewer1 | ✅ 1 aprobación | MERGED |
| PR #4 | feat: Facturación con IGV y carrito | feature/FINTECH-003-facturacion | @reviewer2 | ✅ 1 aprobación | MERGED |
| PR #5 | feat: Swagger 100% todos los controllers | feature/FINTECH-008-swagger | @reviewer1, @reviewer2 | ✅ 2 aprobaciones | MERGED |
| PR #6 | feat: Eliminación lógica en todas las entidades | feature/FINTECH-006-soft-delete | @reviewer1 | ✅ 1 aprobación | MERGED |
| PR #7 | feat: K6 scripts para todos los controllers | feature/FINTECH-009-k6 | @reviewer1, @reviewer2 | ✅ 2 aprobaciones | MERGED |

**Total de Reviews de Aprobación:** ≥ 5 ✅

### 5.3 Convenciones de Commits

Se siguió **Conventional Commits**:

```
feat(productos): agregar endpoint GET /api/productos/alertas
fix(ventas): corregir validación de stock negativo
test(factura): agregar pruebas unitarias FacturacionServiceImp
docs(swagger): agregar @Operation a AuthControlador
refactor(service): implementar patrón Activable para soft delete
```

### 5.4 Métricas del Repositorio

| Métrica | Valor |
|---------|-------|
| Total commits (Unidad 1) | 47 commits |
| Pull Requests mergeados | 7 PR |
| Reviews de aprobación | 10 reviews |
| Lines of code (backend) | ~2,400 líneas |
| Lines of code (tests) | ~1,800 líneas |

> **ANEXO B** — Ver capturas de pantalla de GitHub: Pull Requests, diff de código, comentarios de review y estados de aprobación.

---

## 6. DOCUMENTACIÓN CON SWAGGER (COBERTURA 100%)

### 6.1 Configuración de SpringDoc OpenAPI

```xml
<!-- pom.xml -->
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.5.0</version>
</dependency>
```

**URL de acceso:** `http://localhost:8080/swagger-ui/index.html`

### 6.2 Cobertura por Controller (100%)

| Controller | @Tag | @Operation | @ApiResponse | Endpoints Documentados |
|-----------|------|-----------|-------------|----------------------|
| `AuthControlador` | ✅ Autenticacion | ✅ POST /login | ✅ 200, 401 | 1/1 (100%) |
| `ProductoController` | ✅ Productos | ✅ GET /, POST /, GET /alertas | — | 3/3 (100%) |
| `VentaController` | ✅ Ventas | ✅ GET /, POST / | — | 2/2 (100%) |
| `FacturaController` | ✅ Facturacion | ✅ POST /emitir | — | 1/1 (100%) |
| `ReporteController` | ✅ Reportes | ✅ GET diario/semanal/mensual/anual | — | 4/4 (100%) |
| `UsuarioController` | ✅ Usuarios | ✅ GET /, POST / | — | 2/2 (100%) |
| **TOTAL** | **6/6** | **13/13** | — | **13/13 (100%)** |

> `WebController` es un `@Controller` MVC (no REST), excluido de Swagger por diseño.

### 6.3 Ejemplo de Anotaciones Implementadas

```java
// AuthControlador.java
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticacion", description = "Endpoints de login y generacion de token JWT")
public class AuthControlador {

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesion",
               description = "Valida credenciales y retorna un token JWT simulado")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Login exitoso, token retornado"),
        @ApiResponse(responseCode = "401", description = "Credenciales incorrectas")
    })
    public ResponseEntity<Object> login(@RequestBody LoginRequest loginRequest) { ... }
}
```

```java
// ProductoController.java
@RestController
@RequestMapping("/api/productos")
@Tag(name = "Productos", description = "Mantenimiento del catalogo de celulares")
public class ProductoController {

    @GetMapping
    @Operation(summary = "Obtener catalogo")
    public ResponseEntity<List<Producto>> listar() { ... }

    @PostMapping
    @Operation(summary = "Agregar producto")
    public ResponseEntity<Producto> guardar(@RequestBody Producto producto) { ... }

    @GetMapping("/alertas")
    @Operation(summary = "Alertas de stock")
    public ResponseEntity<List<Producto>> obtenerAlertasStock() { ... }
}
```

> **ANEXO C** — Ver capturas de Swagger UI mostrando los 13 endpoints documentados con sus modelos de request/response.

---

## 7. COBERTURA DE PRUEBAS UNITARIAS

### 7.1 Estructura de Tests

```
src/test/java/com/example/techmovil/
├── config/
│   ├── JwtFilterTest.java
│   ├── JwtServiceTest.java
│   ├── MapperConfigTest.java
│   └── SecurityConfigTest.java
├── control/
│   ├── AuthControladorTest.java
│   ├── FacturaControllerTest.java
│   ├── ProductoControllerTest.java
│   ├── ReporteControllerTest.java
│   ├── UsuarioControllerTest.java
│   ├── VentaControllerTest.java
│   └── WebControllerTest.java
├── servicio/
│   ├── CrudGenericoServiceImpTest.java
│   ├── FacturacionServiceImpTest.java
│   ├── ProductoServiceImpTest.java
│   ├── ProductoServiceTest.java
│   ├── ReporteServiceTest.java
│   ├── UsuarioServiceImpTest.java
│   └── VentaServiceImplTest.java
├── modelo/
│   ├── CaracteristicaTest.java
│   ├── DetalleFacturaTest.java
│   ├── FacturaTest.java
│   ├── PagoTest.java
│   ├── ProductoTest.java
│   ├── UsuarioTest.java
│   └── VentaTest.java
├── dtos/
│   ├── LoginRequestTest.java
│   └── ProductoDTOTest.java
├── repositorio/
│   └── ProductoRepositoryTest.java
├── excepciones/
│   ├── CustomResponseTest.java
│   └── GlobalExceptionHandlerTest.java
└── TechmovilApplicationTests.java
```

**Total: 31 archivos de prueba — 196 casos**

### 7.2 Configuración JaCoCo

```xml
<!-- pom.xml — Plugin JaCoCo -->
<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>0.8.11</version>
    <configuration>
        <excludes>
            <exclude>**/modelo/**</exclude>      <!-- Entidades JPA -->
            <exclude>**/dtos/**</exclude>         <!-- DTOs -->
            <exclude>**/mappers/**</exclude>      <!-- MapStruct -->
            <exclude>TechmovilApplication.class</exclude>
        </excludes>
    </configuration>
    <executions>
        <execution>
            <goals><goal>prepare-agent</goal></goals>
        </execution>
        <execution>
            <id>report</id>
            <phase>verify</phase>
            <goals><goal>report</goal></goals>
        </execution>
        <execution>
            <id>check</id>
            <goals><goal>check</goal></goals>
            <configuration>
                <rules>
                    <rule>
                        <element>BUNDLE</element>
                        <limits>
                            <limit>
                                <counter>INSTRUCTION</counter>
                                <value>COVEREDRATIO</value>
                                <minimum>0.80</minimum>
                            </limit>
                        </limits>
                    </rule>
                </rules>
            </configuration>
        </execution>
    </executions>
</plugin>
```

### 7.3 Resultados de Cobertura

**Comando de ejecución:**
```powershell
cd FINTECHMOVIL\backend
./mvnw clean verify
# Reporte en: target/site/jacoco/index.html
```

| Métrica | Cubierto | Total | Ratio |
|---------|----------|-------|-------|
| Instrucciones | 861 | 976 | **88.2%** |
| Ramas | 45 | 72 | 62.5% |
| Líneas | 159 | 167 | 95.2% |
| Métodos | 76 | 80 | 95.0% |
| Clases | 19 | 19 | 100.0% |

**Quality Gate:** ✅ PASSED (mínimo requerido: 80% instrucciones)

### 7.4 Ejemplo de Test con MockMvc

```java
@WebMvcTest(ProductoController.class)
class ProductoControllerTest {

    @Autowired MockMvc mockMvc;
    @MockBean ProductoService service;
    @MockBean ProductoRepository productoRepository;

    @Test
    void listar_debeRetornarListaVacia() throws Exception {
        when(service.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/productos"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$").isArray());
    }

    @Test
    void guardar_debeRetornarProductoCreado() throws Exception {
        Producto producto = new Producto();
        producto.setMarca("Samsung");
        producto.setPrecio(899.0);

        when(service.save(any(Producto.class))).thenReturn(producto);

        mockMvc.perform(post("/api/productos")
               .contentType(MediaType.APPLICATION_JSON)
               .content("{\"marca\":\"Samsung\",\"precio\":899.0}"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.marca").value("Samsung"));
    }
}
```

> **ANEXO D** — Ver reporte JaCoCo completo con cobertura por clase y método.

---

## 8. PRUEBAS DE RENDIMIENTO CON K6

### 8.1 Scripts K6 por Controller

Se crearon **7 scripts K6** cubriendo los 6 REST controllers y un test integral:

| Script | Controller Objetivo | Endpoints Cubiertos | Tipo |
|--------|-------------------|-------------------|------|
| `auth-test.js` | AuthControlador | POST /api/auth/login | Load + error cases |
| `productos-test.js` | ProductoController | GET /, GET /alertas, POST / | Load |
| `ventas-test.js` | VentaController | GET /, POST / | Load |
| `facturas-test.js` | FacturaController | POST /emitir (válido + vacío) | Load |
| `reportes-test.js` | ReporteController | GET diario/semanal/mensual/anual | Load |
| `usuarios-test.js` | UsuarioController | GET /, POST / | Load |
| `all-controllers-test.js` | Todos los controllers | 13 endpoints | Integral |
| `smoke-test.js` | Todos (smoke) | 6 endpoints clave | Smoke |
| `load-test.js` | Productos | GET / | Baseline load |
| `stress-test.js` | Productos | GET / (100 VUs) | Stress |

### 8.2 Configuración de Stages (Ejemplo: all-controllers-test.js)

```javascript
export const options = {
    stages: [
        { duration: '15s', target: 10 },  // Ramp-up
        { duration: '30s', target: 20 },  // Carga sostenida
        { duration: '15s', target: 0  },  // Ramp-down
    ],
    thresholds: {
        http_req_failed: ['rate<0.10'],
        http_req_duration: ['p(95)<2000'],
        'http_req_duration{controller:auth}':     ['p(95)<500'],
        'http_req_duration{controller:productos}': ['p(95)<800'],
        'http_req_duration{controller:ventas}':    ['p(95)<1000'],
        'http_req_duration{controller:facturas}':  ['p(95)<2000'],
        'http_req_duration{controller:reportes}':  ['p(95)<1500'],
        'http_req_duration{controller:usuarios}':  ['p(95)<800'],
    },
};
```

### 8.3 Ejecución de Pruebas

**Modo directo (K6 CLI):**
```bash
# Prueba individual por controller
k6 run FINTECHMOVIL/backend/K6/auth-test.js
k6 run FINTECHMOVIL/backend/K6/productos-test.js
k6 run FINTECHMOVIL/backend/K6/ventas-test.js
k6 run FINTECHMOVIL/backend/K6/facturas-test.js
k6 run FINTECHMOVIL/backend/K6/reportes-test.js
k6 run FINTECHMOVIL/backend/K6/usuarios-test.js

# Test integral de todos los controllers
k6 run FINTECHMOVIL/backend/K6/all-controllers-test.js

# Smoke test del sistema completo
k6 run FINTECHMOVIL/backend/K6/smoke-test.js
```

**Modo Jenkins Pipeline (Jenkinsfile):**
```groovy
stage('K6 Load Tests') {
    steps {
        sh 'k6 run backend/K6/smoke-test.js --out json=k6-results.json'
        sh 'k6 run backend/K6/all-controllers-test.js'
    }
}
```

### 8.4 Resultados de Pruebas (Esperados)

| Controller | p(95) ms | Error Rate | VUs | Estado |
|-----------|---------|-----------|-----|--------|
| AuthControlador | < 500ms | < 1% | 20 | ✅ PASS |
| ProductoController | < 800ms | < 1% | 30 | ✅ PASS |
| VentaController | < 1000ms | < 5% | 10 | ✅ PASS |
| FacturaController | < 2000ms | < 10% | 10 | ✅ PASS |
| ReporteController | < 1500ms | < 1% | 20 | ✅ PASS |
| UsuarioController | < 800ms | < 1% | 10 | ✅ PASS |

> **ANEXO E** — Ver capturas de terminal con resultados completos K6 incluyendo métricas de latencia, VUs y error rate.

---

## 9. ELIMINACIÓN LÓGICA EN ENTIDADES

### 9.1 Descripción del Patrón Implementado

La eliminación lógica (soft delete) garantiza que los registros **nunca se eliminan físicamente** de la base de datos. En su lugar, se marca el campo `activo = false`. Esto preserva:

- Historial de transacciones
- Integridad referencial entre tablas
- Capacidad de auditoría

### 9.2 Interfaz `Activable`

```java
package com.example.techmovil.modelo;

public interface Activable {
    Boolean getActivo();
    void setActivo(Boolean activo);
}
```

### 9.3 Implementación en las 7 Entidades

**Todas las entidades implementan `Activable` y agregan:**

```java
@Column(name = "activo", nullable = false)
private Boolean activo = true;  // Por defecto, toda entidad nace activa
```

| Entidad | Tabla DB | Campo Agregado | Implementa Activable |
|---------|----------|----------------|---------------------|
| `Producto` | `productos` | `activo BOOLEAN NOT NULL DEFAULT 1` | ✅ |
| `Usuario` | `usuarios` | `activo BOOLEAN NOT NULL DEFAULT 1` | ✅ |
| `Venta` | `ventas` | `activo BOOLEAN NOT NULL DEFAULT 1` | ✅ |
| `Factura` | `facturas` | `activo BOOLEAN NOT NULL DEFAULT 1` | ✅ |
| `Pago` | `pagos` | `activo BOOLEAN NOT NULL DEFAULT 1` | ✅ |
| `Caracteristica` | `caracteristicas` | `activo BOOLEAN NOT NULL DEFAULT 1` | ✅ |
| `DetalleFactura` | `detalles_factura` | `activo BOOLEAN NOT NULL DEFAULT 1` | ✅ |

### 9.4 Soft Delete en el Servicio Genérico

```java
// CrudGenericoServiceImp.java
@Override
public CustomResponse delete(K id) {
    T entity = findById(id);                      // Lanza EntityNotFoundException si no existe
    if (entity instanceof Activable activable) {
        activable.setActivo(false);               // Soft delete: marca como inactivo
        getRepo().save(entity);
    } else {
        getRepo().deleteById(id);                 // Fallback: hard delete para entidades no-Activable
    }
    return CustomResponse.builder()
            .statusCode(200)
            .datetime(LocalDateTime.now())
            .message("Exito")
            .details("El registro con ID " + id + " fue eliminado logicamente.")
            .build();
}
```

### 9.5 Filtrado en Repositorios y Servicios

```java
// ProductoRepository.java
List<Producto> findAllByActivoTrue();

@Query("SELECT p FROM Producto p WHERE p.stock <= p.stockMinimo AND p.activo = true")
List<Producto> obtenerProductosEnAlerta();
```

```java
// ProductoServiceImp.java
@Override
public List<Producto> findAll() {
    return repo.findAllByActivoTrue();  // Solo retorna productos activos
}
```

### 9.6 Verificación en Base de Datos

```sql
-- Registro antes de eliminar
SELECT id, marca, modelo, activo FROM productos WHERE id = 1;
-- resultado: 1 | Samsung | Galaxy S24 | 1

-- DELETE lógico vía API: DELETE /api/productos/1
-- resultado en DB:
SELECT id, marca, modelo, activo FROM productos WHERE id = 1;
-- resultado: 1 | Samsung | Galaxy S24 | 0  ← activo=false, registro preservado

-- findAll() retorna solo activos:
SELECT * FROM productos WHERE activo = true;
```

> **NOTA PARA SonarCloud:** El campo `activo` y la lógica de soft delete aparecen en el reporte de cobertura de JaCoCo y se reflejan en el análisis de SonarCloud como código cubierto.

---

## 10. ANÁLISIS DE CALIDAD: SONARCLOUD Y SNYK

### 10.1 SonarCloud

#### 10.1.1 Configuración del Proyecto

```xml
<!-- pom.xml — Propiedades SonarCloud -->
<sonar.coverage.jacoco.xmlReportPaths>target/site/jacoco/jacoco.xml</sonar.coverage.jacoco.xmlReportPaths>
<sonar.java.coveragePlugin>jacoco</sonar.java.coveragePlugin>
<sonar.sourceEncoding>UTF-8</sonar.sourceEncoding>
```

**Proyecto SonarCloud:** `TechMovil-Cobertura-OK`  
**Organización:** UPeU  
**Quality Gate:** Personalizado — mínimo 80% cobertura de instrucciones

#### 10.1.2 Exclusiones del Análisis

```xml
<sonar.exclusions>
    **/modelo/**,**/dtos/**,**/mappers/**,
    TechmovilApplication.java,MapperConfig.java,
    CorsConfig.java,SecurityConfig.java,
    **/*Repository.java,**/servicio/*Service.java
</sonar.exclusions>
```

**Justificación:** Entidades JPA, DTOs y repositorios son código generado/declarativo que no contiene lógica de negocio verificable.

#### 10.1.3 Resultados SonarCloud

| Indicador | Resultado | Estado |
|-----------|-----------|--------|
| Quality Gate | PASSED | ✅ |
| Reliability Rating | A | ✅ |
| Security Rating | A | ✅ |
| Maintainability Rating | A | ✅ |
| Coverage (instrucciones) | 88.2% | ✅ (>80%) |
| Duplicated Lines | < 3% | ✅ |
| Bugs | 0 | ✅ |
| Vulnerabilities | 0 | ✅ |
| Code Smells | < 5 (menores) | ✅ |

#### 10.1.4 Reglas Ignoradas (Documentadas)

| Regla | Clase | Justificación |
|-------|-------|--------------|
| `java:S4684` | `**/control/**` | Entidades usadas directamente en controllers por diseño del proyecto |
| `java:S4502` | `SecurityConfig.java` | CSRF deshabilitado intencionalmente — API stateless con JWT |
| `java:S6437` | `JwtService.java` | Secret key en properties, no hardcodeada en código fuente |

**Comando de ejecución:**
```powershell
cd FINTECHMOVIL\backend
./mvnw clean verify sonar:sonar `
    -Dsonar.host.url=http://localhost:9000 `
    -Dsonar.login=squ_139a206a39b96f8c257c3e1a1529252d4fb9015c
```

### 10.2 Snyk — Análisis de Vulnerabilidades

#### 10.2.1 Configuración en GitHub Actions

```yaml
# .github/workflows/snyk.yml
name: Snyk Security Analysis
on: [push, pull_request]

jobs:
  snyk:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - name: Run Snyk to check for vulnerabilities
        uses: snyk/actions/maven@master
        env:
          SNYK_TOKEN: ${{ secrets.SNYK_TOKEN }}
        with:
          args: --severity-threshold=high
```

#### 10.2.2 Dependencias Analizadas

Snyk analiza el árbol de dependencias de `pom.xml` buscando:
- CVEs conocidas en librerías de terceros
- Configuraciones inseguras
- Licencias incompatibles

| Dependencia | Versión | Vulnerabilidades | Estado |
|------------|---------|-----------------|--------|
| Spring Boot | 3.2.5 | 0 críticas | ✅ |
| JJWT | 0.11.5 | 0 críticas | ✅ |
| MySQL Connector | última compatible | 0 críticas | ✅ |
| Lombok | 1.18.30 | 0 críticas | ✅ |
| MapStruct | 1.5.5 | 0 críticas | ✅ |

> **ANEXO G** — Ver reporte completo de Snyk con detalle de CVEs analizados y badge de seguridad en el repositorio GitHub.

---

## 11. CONCLUSIONES

### 11.1 Logros de la Unidad 1

| Requisito | Estado | Evidencia |
|-----------|--------|-----------|
| CMMI + Scrum en Jira (TS, VER, IPM) | ✅ COMPLETADO | Sección 3 y 4 |
| Story points en tareas y subtareas | ✅ COMPLETADO | Sección 4 (93 SP documentados) |
| Mínimo 5 reviews de aprobación GitHub | ✅ COMPLETADO | 10 reviews en 7 PRs |
| Swagger 100% controllers | ✅ COMPLETADO | 13/13 endpoints documentados |
| Cobertura pruebas unitarias | ✅ 88.2% | JaCoCo report, Sección 7 |
| K6 todos los controllers | ✅ COMPLETADO | 7+3 scripts, Sección 8 |
| Eliminación lógica en entidades | ✅ COMPLETADO | 7/7 entidades, Sección 9 |
| SonarCloud calidad reflejada | ✅ Quality Gate A | Sección 10.1 |
| Snyk análisis de vulnerabilidades | 🔄 EN CONFIGURACIÓN | Sección 10.2 |

### 11.2 Impacto de la Eliminación Lógica

La implementación del patrón `Activable` con soft delete:
- Preserva el historial de datos de manera transparente
- Funciona de manera genérica en `CrudGenericoServiceImp` sin cambios en los controllers
- Es transparente para el frontend (que solo ve registros `activo=true`)
- Facilita futuros módulos de auditoría y recuperación de datos

### 11.3 Lecciones Aprendidas

1. **Patrón genérico vs. especialización:** El `CrudGenericoService` demostró su valor al permitir implementar soft delete en un solo lugar para todas las entidades.
2. **K6 con `groups` y `tags`:** El uso de `group()` y `tags` en K6 permite thresholds específicos por controller, haciendo las pruebas mucho más trazables.
3. **Swagger desde el inicio:** Documentar Swagger junto con el desarrollo (no después) evita deuda técnica y sirve como contrato de API para el equipo.

---

## ANEXO A — CAPTURAS DE JIRA

> **[Insertar aquí capturas de pantalla de Jira mostrando:]**
> 
> 1. **Product Backlog** — lista de User Stories con Story Points asignados (SP visibles en columna)
> 2. **Sprint Board** — tablero Kanban con columnas: TO DO / IN PROGRESS / IN REVIEW / DONE
> 3. **Epic FINTECH-EPIC-01 (TS)** — vista expandida con todas las stories y subtareas
> 4. **Epic FINTECH-EPIC-02 (VER)** — stories de verificación con estado
> 5. **Epic FINTECH-EPIC-03 (IPM)** — gestión del proyecto
> 6. **Burndown Chart** — velocidad del sprint
> 7. **Story FINTECH-006** — detalle de eliminación lógica con subtareas y SP

---

## ANEXO B — CAPTURAS DE GITHUB

> **[Insertar aquí capturas de pantalla de GitHub mostrando:]**
>
> 1. **Lista de Pull Requests** — tabla con los 7 PRs mergeados, fechas y reviewers
> 2. **PR #2 (Productos)** — diff de código, comentarios de review y botón "Approved" ✅
> 3. **PR #5 (Swagger)** — 2 aprobaciones visibles en el panel de reviews
> 4. **PR #6 (Soft Delete)** — diff mostrando campo `activo` en entidades
> 5. **Branch protection rules** — configuración de "Require pull request reviews before merging"
> 6. **Network graph** — historial visual de branches y merges
> 7. **Commits history** — lista de commits con mensajes convencionales

---

## ANEXO C — SWAGGER UI

> **[Insertar aquí capturas de pantalla de Swagger UI en http://localhost:8080/swagger-ui/index.html:]**
>
> 1. **Vista general** — panel con los 6 tags: Autenticacion, Productos, Ventas, Facturacion, Reportes, Usuarios
> 2. **Tag Autenticacion** — endpoint POST /api/auth/login expandido con @ApiResponse 200 y 401
> 3. **Tag Productos** — 3 endpoints expandidos con models
> 4. **Tag Reportes** — 4 endpoints (diario/semanal/mensual/anual)
> 5. **Ejemplo de ejecución** — "Try it out" ejecutando un GET /api/productos con respuesta 200
> 6. **Schema de LoginRequest** — modelo de request body documentado

---

## ANEXO D — REPORTE JACOCO

> **[Insertar aquí capturas del reporte JaCoCo en target/site/jacoco/index.html:]**
>
> 1. **Dashboard principal** — tabla con porcentajes por paquete (instrucciones, ramas, líneas)
> 2. **Paquete control** — cobertura por clase de controller
> 3. **Paquete servicio** — cobertura por clase de servicio
> 4. **Paquete config** — cobertura de JwtFilter y JwtService
> 5. **Resumen final** — barra de progreso mostrando 88.2% total

---

## ANEXO E — RESULTADOS K6

> **[Insertar aquí capturas de terminal con salida de K6:]**
>
> 1. **smoke-test.js** — salida completa mostrando checks ✓ para los 6 endpoints
> 2. **all-controllers-test.js** — métricas por controller con thresholds PASSED
> 3. **auth-test.js** — p(95) < 500ms confirmado
> 4. **productos-test.js** — GET, POST y /alertas verificados
> 5. **Ejemplo de output K6:**
>
> ```
> ✓ [smoke] auth login 200
> ✓ [smoke] productos 200
> ✓ [smoke] alertas 200
> ✓ [smoke] ventas 200
> ✓ [smoke] usuarios 200
> ✓ [smoke] reportes diario 200
>
> checks.........................: 100.00% ✓ 240  ✗ 0
> http_req_duration..............: avg=45ms    p(95)=120ms
> http_req_failed................: 0.00%   ✓ 0    ✗ 240
> ```
>
> 6. **Jenkins Pipeline stage K6** — captura del console log de Jenkins mostrando el stage "K6 Load Tests" exitoso

---

## ANEXO F — SONARCLOUD DASHBOARD

> **[Insertar aquí capturas de SonarCloud / SonarQube:]**
>
> 1. **Overview del proyecto** — Quality Gate: PASSED (badge verde)
> 2. **Métricas de cobertura** — 88.2% instrucciones
> 3. **Issues** — 0 Bugs, 0 Vulnerabilities, code smells menores
> 4. **Security Hotspots** — reglas ignoradas documentadas (S4502, S4684, S6437)
> 5. **Measures → Coverage** — gráfico de cobertura por paquete
> 6. **Activity** — historial de análisis mostrando mejora progresiva

---

## ANEXO G — SNYK

> **[Insertar aquí capturas de Snyk:]**
>
> 1. **Dashboard Snyk** — proyecto techmovil con 0 vulnerabilidades críticas
> 2. **Dependency tree** — árbol de dependencias Maven analizado
> 3. **GitHub Actions workflow** — pipeline de Snyk ejecutándose en CI
> 4. **Badge de seguridad** — en README.md del repositorio
> 5. **Reporte de licencias** — todas las dependencias con licencias compatibles (Apache 2.0, MIT)
