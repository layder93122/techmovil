# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

FinTechmovil is a full-stack ERP system for mobile phone inventory and sales management. It consists of a Spring Boot 3.2.5 backend and a React 19 + Vite frontend, deployed on Railway (backend) and Vercel (frontend).

---

## Commands

All commands are run from within the relevant subdirectory (`FINTECHMOVIL/backend` or `FINTECHMOVIL/frontend`).

### Backend (Spring Boot / Maven)

```powershell
# Run in development mode (requires MySQL on localhost:3306)
./mvnw spring-boot:run

# Build JAR (skip tests for speed)
./mvnw clean package -DskipTests

# Run all tests
./mvnw test

# Run a single test class
./mvnw test -Dtest=ProductoControllerTest

# Generate JaCoCo coverage report (opens at target/site/jacoco/index.html)
./mvnw jacoco:report

# Run SonarQube analysis (requires SonarQube running on port 9000)
./mvnw sonar:sonar

# Full automated pipeline: build → test → coverage → sonar
.\EJECUTAR.ps1
```

### Frontend (React / Vite)

```powershell
# Install dependencies (first time only)
npm install

# Start dev server at http://localhost:5173
npm run dev

# Production build (output: dist/)
npm run build

# Preview production build locally
npm run preview
```

### Docker / Infrastructure

```powershell
# Start local MySQL (port 3306), SonarQube (port 9000), Jenkins (port 8081)
cd FINTECHMOVIL/backend
docker-compose up -d

# Build and run backend Docker image
docker build -t techmovil:latest .
docker run -p 8080:8080 techmovil:latest
```

---

## Architecture

### System Overview

```
React Frontend (Vercel) ──HTTP/JWT──► Spring Boot API (Railway) ──JDBC──► MySQL 8.0
                                            │
                               SonarQube / Jenkins / K6 (local Docker)
```

### Backend (`FINTECHMOVIL/backend/`)

**Package**: `com.example.techmovil`

Layered architecture:
- **`control/`** — REST controllers (AuthControlador, ProductoController, VentaController, FacturaController, UsuarioController, ReporteController, WebController)
- **`servicio/`** — Business logic services with interface + implementation pairs. A generic `CrudGenericoService` / `CrudGenericoServiceImp` handles boilerplate CRUD so specific services only override what differs.
- **`repositorio/`** — Spring Data JPA repositories
- **`modelo/`** — JPA entities: `Producto`, `Usuario`, `Venta`, `Factura`, `DetalleFactura`, `Pago`, `Caracteristica`
- **`dtos/`** — DTOs mapped via MapStruct (`mappers/GenericMapper.java`)
- **`config/`** — `SecurityConfig` (Spring Security, CSRF disabled), `JwtFilter` + `JwtService` (stateless JWT auth), `CorsConfig`, `MapperConfig`
- **`excepciones/`** — `GlobalExceptionHandler` with custom exceptions (`CarritoVacioException`, `StockInsuficienteException`)

**Key config** (`src/main/resources/application.properties`):
- Database: `jdbc:mysql://localhost:3306/techmovil_db`, `ddl-auto=update`
- Default admin: `app.admin.username=admin` / `app.admin.password=admin123`
- JWT secret: `app.jwt.signing` property (overridden by `JWT_SECRET` env var in production)
- CORS origins: localhost:5173 (dev) + `https://fintechmovil.vercel.app` (prod)

**API base**: `http://localhost:8080/api`  
**Swagger UI**: `http://localhost:8080/swagger-ui/index.html`

**Testing**: 31 test files, 196 tests, 85.4% coverage. JaCoCo excludes entities, DTOs, mappers, and repositories from coverage measurement. Quality gate requires ≥80% instruction coverage.

### Frontend (`FINTECHMOVIL/frontend/`)

The entire application lives in two files: `src/main.jsx` (entry point) and `src/App.jsx` (~1400 lines, the entire app).

`App.jsx` is structured into inline sections:
1. **Embedded CSS** (~4900 lines, supports light/dark themes via CSS variables)
2. **Mock data** (`PRODUCTOS_MOCK`, `CLIENTES_MOCK`, `VENTAS_MOCK`, etc.) — the app works fully without a backend
3. **Shared UI components**: `ToastProvider`, `StockBadge`, `EstadoBadge`, `SearchInput`, SVG chart components (`BarChart`, `LineChart`, `DonutChart`)
4. **Module components**: `Dashboard`, `Productos`, `Inventario`, `Ventas` (POS), `Reportes`, `Clientes`, `Auth`
5. **Shell layout**: sidebar + topbar with React state-based routing (no router library)

**Backend connection**: Set `VITE_API_URL` in `.env` (see `.env.example`). If the env var is absent or calls fail, mock data is used as fallback. All API calls use `Authorization: Bearer <token>` header.

**Frontend login credentials** (mock mode): `admin` / `1234`

### Deployment

| Service | Platform | Config file |
|---------|----------|-------------|
| Backend | Railway.app | `railway.json`, `nixpacks.toml`, `Procfile` |
| Frontend | Vercel.app | `vercel.json` (SPA rewrites → `/index.html`) |
| Database | Railway MySQL | Via `MYSQLURL`, `MYSQLUSER`, `MYSQLPASSWORD` env vars |

Production build command: `./mvnw clean package -DskipTests` → runs JAR from `target/techmovil-0.0.1-SNAPSHOT.jar`.

### Load Testing

K6 scripts in `FINTECHMOVIL/backend/K6/`: `smoke-test.js`, `load-test.js`, `stress-test.js`. Run with `k6 run <script>` against the local or remote API.
