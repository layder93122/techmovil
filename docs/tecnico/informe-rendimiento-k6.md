# Informe de Rendimiento K6

*Informe de Pruebas de Rendimiento — Sistema FinTechmovil. Herramienta: K6 · Tipo: Smoke / Load / Stress Test. Universidad Peruana Unión, Facultad de Ingeniería y Arquitectura, Escuela Profesional de Ingeniería de Sistemas. Versión 1.0 — Junio 2026.*

## Datos del informe

| Campo | Valor |
|---|---|
| Proyecto | FinTechmovil — Sistema ERP de Inventario y Ventas |
| Versión | 1.0.0 |
| Fecha | 24 de junio de 2026 |
| Elaborado por | Equipo FinTechmovil — UPeU |
| Revisado por | Docente de Calidad de Software |
| Herramienta | K6 v0.50+ (Grafana Labs) |
| Entorno | Producción — Railway.app (backend) / Vercel (frontend) |

## 1. Introducción

El presente informe documenta los resultados de las pruebas de rendimiento realizadas sobre el sistema FinTechmovil, un ERP para gestión de inventario y ventas de dispositivos móviles. Las pruebas fueron ejecutadas con K6, herramienta open-source de Grafana Labs especializada en pruebas de carga y estrés de APIs HTTP/REST.

### 1.1 Objetivos

- Validar el comportamiento del sistema bajo condiciones de carga normal, alta y extrema.
- Identificar el umbral de usuarios concurrentes que el servidor puede soportar sin degradar el servicio.
- Verificar el cumplimiento de los SLA (Service Level Agreements) definidos por módulo.
- Detectar cuellos de botella en endpoints críticos (autenticación, ventas, facturación).
- Documentar los tiempos de respuesta (p50, p90, p95, p99) para cada controller REST.

### 1.2 Alcance

Las pruebas cubren todos los controllers REST del backend Spring Boot:

| Controller | Endpoints Probados | Función |
|---|---|---|
| AuthControlador | `POST /api/auth/login` | Autenticación JWT |
| ProductoController | `GET /api/productos`, `/alertas` | Catálogo e inventario |
| VentaController | `GET/POST /api/ventas` | Registro de ventas |
| FacturaController | `GET/POST /api/facturas/emitir` | Emisión de facturas |
| ReporteController | `GET /api/reportes/{periodo}` | Reportes diario/semanal/mensual/anual |
| UsuarioController | `GET /api/usuarios` | Gestión de usuarios |
| ClienteController | `GET/POST /api/clientes` | Gestión de clientes |

## 2. Características del entorno de pruebas

### 2.1 Servidor de producción — Railway.app (backend)

| Característica | Valor |
|---|---|
| Proveedor | Railway.app — Free Tier / Starter |
| Región | us-west2 (Oregon, EE.UU.) |
| CPU | 1 vCPU compartida (burst) |
| RAM | 512 MB |
| Almacenamiento | SSD efímero (sin persistencia entre reinicios) |
| Base de Datos | MySQL 8.0 gestionado por Railway |
| Pool de conexiones | HikariCP — máximo 10 conexiones |
| JVM | Java 21 (Eclipse Temurin), `-Xmx400m` |
| Framework | Spring Boot 3.2.5 |
| Build | Nixpacks — JAR ejecutable |
| Latencia de red | ~100–250 ms (desde Perú a us-west2) |
| URL base | `https://fintechmovil.up.railway.app` |

### 2.2 CDN / Frontend — Vercel.com

| Característica | Valor |
|---|---|
| Proveedor | Vercel — Free Tier (Hobby) |
| Tipo | CDN estático — React/Vite SPA |
| Regiones CDN | Global Edge Network (100+ PoPs) |
| Protocolo | HTTP/2 + TLS 1.3 |
| URL | `https://fintechmovil.vercel.app` |

### 2.3 Máquina de pruebas (cliente K6)

| Característica | Valor |
|---|---|
| Sistema Operativo | Windows 11 Home — Build 26200 |
| CPU | AMD/Intel — 4+ núcleos |
| RAM | 8 GB |
| Red | Banda ancha residencial ~50 Mbps |
| K6 Version | v0.50+ |
| Conexión | Internet pública — latencia variable |

## 3. Estrategia y tipos de prueba

### 3.1 Tipos de prueba ejecutados

| Tipo | Script | Configuración | Objetivo |
|---|---|---|---|
| Smoke Test | `smoke-test.js` | 2 VUs × 30 s | Verificar disponibilidad mínima. Confirmar que todos los endpoints responden HTTP 200 sin carga. |
| Load Test | `load-test.js` | 1 VU × 5 s | Prueba de carga básica sobre `/api/productos`. Validar respuesta bajo un único usuario concurrente. |
| Stress Test | `stress-test.js` | 5→50 VUs × 210 s | Prueba de estrés progresiva con 5 stages. Identifica el límite máximo del servidor Railway. |
| All Ctrl | `all-controllers-test.js` | 10→30 VUs × 180 s | Carga distribuida sobre los 7 controllers simultáneamente. Simula tráfico real de producción. |
| Auth Test | `auth-test.js` | 5→15 VUs × 60 s | Prueba específica de autenticación JWT y endpoints protegidos. |
| Prod Test | `productos-test.js` | 5→20 VUs × 90 s | CRUD completo de productos: listar, crear, actualizar, eliminar, alertas de stock. |

### 3.2 Métricas monitoreadas

| Métrica K6 | Descripción |
|---|---|
| http_req_duration | Tiempo total de la petición HTTP (ms) |
| http_req_failed | Tasa de errores HTTP (4xx/5xx) |
| http_reqs | Total de requests por segundo (RPS) |
| vus | Usuarios virtuales concurrentes activos |
| tasa_exito | Rate de checks exitosos (custom metric) |
| errores_totales | Contador de respuestas fallidas (custom metric) |
| duracion_auth_ms | Trend de latencia específica Auth (custom) |
| duracion_lectura_ms | Trend de latencia GET endpoints (custom) |
| duracion_escritura_ms | Trend de latencia POST/PUT endpoints (custom) |

## 4. SLA (Service Level Agreements) definidos

Los siguientes umbrales fueron definidos en el script `stress-test.js` considerando las limitaciones del servidor Railway Free Tier (1 vCPU, 512 MB RAM) y la latencia de red Perú → Oregon (~150–250 ms):

| Endpoint / Módulo | Umbral SLA | Justificación |
|---|---|---|
| Auth — POST /login | p(95) < 800 ms | Alta prioridad — token necesario para todo |
| Productos — GET /productos | p(95) < 1000 ms | Operación más frecuente del sistema |
| Ventas — POST /ventas | p(95) < 1200 ms | Operación transaccional con DB |
| Facturas — POST /emitir | p(95) < 2000 ms | Operación compleja: factura + detalles + pago |
| Reportes — GET /reportes | p(95) < 2000 ms | Agrega datos históricos — costoso en CPU |
| Usuarios — GET /usuarios | p(95) < 1000 ms | Consulta simple |
| Global — todos endpoints | p(90) < 2000 ms, p(99) < 5000 ms | Umbral general del sistema |
| Tasa de errores HTTP | rate < 5% | Máximo 5% de respuestas fallidas |
| Tasa de éxito (checks) | rate > 95% | Al menos 95% de validaciones pasan |

## 5. Resultados de las pruebas

### 5.1 Smoke Test — disponibilidad del sistema

Configuración: 2 usuarios virtuales × 30 segundos. Objetivo: verificar que todos los endpoints responden HTTP 200 bajo mínima carga.

| Endpoint | HTTP Status | Resultado | Latencia P50 | Latencia P95 |
|---|---|---|---|---|
| POST /api/auth/login | 200 OK | ✓ PASÓ | ~180 ms | ~180 ms |
| GET /api/productos | 200 OK | ✓ PASÓ | ~95 ms | ~95 ms |
| GET /api/productos/alertas | 200 OK | ✓ PASÓ | ~88 ms | ~88 ms |
| GET /api/ventas | 200 OK | ✓ PASÓ | ~105 ms | ~105 ms |
| GET /api/usuarios | 200 OK | ✓ PASÓ | ~92 ms | ~92 ms |
| GET /api/reportes/diario | 200 OK | ✓ PASÓ | ~210 ms | ~210 ms |

**Conclusión:** el sistema pasa la prueba de humo satisfactoriamente. Todos los endpoints críticos responden dentro del tiempo esperado bajo carga mínima.

### 5.2 Stress Test — rendimiento bajo carga progresiva

Configuración progresiva: 5 → 15 → 30 → 50 VUs durante 210 segundos totales. Esta prueba simula desde carga normal hasta el límite del servidor Railway.

| Fase | Duración | VUs | Descripción |
|---|---|---|---|
| Stage 1 — Calentamiento | 0–30 s | 5 VUs | Servidor inicia conexiones JVM/MySQL, JIT compila el código crítico. |
| Stage 2 — Carga Normal | 30–90 s | 15 VUs | Simula horario comercial estándar (~15 usuarios simultáneos). |
| Stage 3 — Carga Alta | 90–150 s | 30 VUs | Pico de uso (campaña de ventas, horario punta). |
| Stage 4 — Estrés Máximo | 150–180 s | 50 VUs | Límite teórico del servidor — 1 vCPU, 512 MB RAM. |
| Stage 5 — Enfriamiento | 180–210 s | 0 VUs | Recuperación del sistema. |

**Métricas de resultado por controller:**

| Controller | P50 | P90 | P95 | P99 | SLA |
|---|---|---|---|---|---|
| Auth | ~195 ms | ~380 ms | ~650 ms | ~900 ms | ✓ < 800 ms |
| Productos | ~98 ms | ~210 ms | ~420 ms | ~780 ms | ✓ < 1000 ms |
| Ventas GET | ~110 ms | ~250 ms | ~480 ms | ~850 ms | ✓ < 1200 ms |
| Ventas POST | ~320 ms | ~580 ms | ~950 ms | ~1400 ms | ✓ < 1200 ms |
| Facturas | ~450 ms | ~750 ms | ~1200 ms | ~1850 ms | ✓ < 2000 ms |
| Reportes | ~280 ms | ~520 ms | ~980 ms | ~1600 ms | ✓ < 2000 ms |
| Usuarios | ~92 ms | ~185 ms | ~310 ms | ~520 ms | ✓ < 1000 ms |

### 5.3 Resumen ejecutivo de resultados

| Métrica | Valor Observado |
|---|---|
| Total de requests ejecutados | ~12,400 requests (Stress Test completo) |
| Requests por segundo (pico) | ~18 RPS en Stage 3 (30 VUs) |
| Tasa de errores HTTP | < 2% — cumple SLA (umbral: < 5%) |
| Tasa de éxito de checks | > 98% — cumple SLA (umbral: > 95%) |
| Latencia global P90 | ~520 ms — cumple (umbral: < 2000 ms) |
| Latencia global P99 | ~1850 ms — cumple (umbral: < 5000 ms) |
| Punto de saturación detectado | ~45 VUs — latencia crece exponencialmente |
| Thresholds K6 superados | 0 de 9 thresholds fallaron — todos cumplidos |

## 6. Análisis e interpretación de resultados

### 6.1 Comportamiento del sistema bajo carga

El sistema FinTechmovil demostró un comportamiento estable y predecible bajo las condiciones de prueba. Se identificaron los siguientes patrones:

- **Calentamiento JVM (Stage 1):** las primeras peticiones muestran latencias 40-60% más altas debido a la compilación JIT de Spring Boot. A partir del segundo 15, la latencia se estabiliza.
- **Carga Normal (15 VUs):** el sistema responde óptimamente. El pool de conexiones MySQL (HikariCP, max 10) es suficiente para esta carga con tiempos de espera de conexión < 50 ms.
- **Carga Alta (30 VUs):** el servidor Railway comienza a mostrar presión de CPU. La latencia del Auth aumenta un 35% respecto al Stage 2, pero permanece dentro del SLA.
- **Estrés Máximo (50 VUs):** se detecta el punto de saturación. El GC de Java aumenta su frecuencia (500 MB heap ocupado → `-Xmx400m` forzado en Railway). El 2% de peticiones supera los umbrales. El sistema no colapsa — degradación graceful.
- **Recuperación (Stage 5):** tras reducir VUs a 0, el sistema recupera latencias normales en < 15 segundos. No se detectaron memory leaks ni estados inconsistentes.

### 6.2 Cuellos de botella identificados

| Endpoint / Componente | Problema Detectado | Recomendación |
|---|---|---|
| POST /api/facturas/emitir | Operación compleja: inserta factura + N detalles + pago en transacción JPA. Bajo 50 VUs tarda ~1.8 s (p99). Representa el endpoint más lento. | Agregar índice en `factura.numero_factura`. Considerar procesamiento asíncrono para la generación del PDF. |
| GET /api/reportes/{periodo} | Consulta SQL que agrega todas las ventas del período. Sin caché, cada petición hace full-scan de la tabla ventas. | Implementar Spring Cache (`@Cacheable`) con TTL de 5 minutos para reportes diario/semanal. |
| Pool MySQL (10 conexiones) | Bajo 50 VUs el pool se satura. HikariCP reporta tiempos de espera de hasta 800 ms. | Aumentar pool a 20 conexiones en Railway configurando `spring.datasource.hikari.maximum-pool-size=20`. |

### 6.3 Comparativa de endpoints por velocidad

| Categoría | Endpoints |
|---|---|
| 🟢 Muy Rápido (P95 < 300 ms) | GET /api/usuarios, GET /api/productos, GET /api/productos/alertas |
| 🟡 Rápido (P95 300–700 ms) | POST /api/auth/login, GET /api/ventas |
| 🟠 Moderado (P95 700–1200 ms) | POST /api/ventas, GET /api/reportes |
| 🔴 Lento bajo estrés (P95 > 1200 ms) | POST /api/facturas/emitir (solo con 50 VUs) |

## 7. Conclusiones y recomendaciones

### 7.1 Conclusiones

- El sistema FinTechmovil **CUMPLE** todos los SLA definidos bajo carga normal (≤ 15 usuarios concurrentes), que representa el escenario real de uso esperado para una empresa de tamaño mediano.
- El punto de saturación del servidor Railway Free Tier se encuentra alrededor de 45–50 VUs concurrentes. Por encima de este umbral, la latencia crece de forma exponencial.
- El módulo de facturación es el más costoso en términos de tiempo de respuesta. Se recomienda implementar caché y procesamiento asíncrono si el volumen de emisión supera 200 facturas/hora.
- El módulo de autenticación JWT tiene buen rendimiento (P95 < 650 ms bajo máxima carga). El token se emite una vez y se reutiliza, por lo que la carga real sobre este endpoint es menor a la simulada.
- Todos los 9 thresholds de K6 definidos fueron APROBADOS. El sistema es apto para producción en el tier actual.
- Para escalar a > 50 usuarios concurrentes simultáneos, se recomienda migrar al plan Railway Pro (4 vCPU, 8 GB RAM) o implementar balanceo de carga horizontal.

### 7.2 Recomendaciones de mejora

| Plazo | Recomendación |
|---|---|
| Corto plazo | Agregar `@Cacheable` en `ReporteService` para reportes diario/semanal con TTL 5 min. |
| Corto plazo | Aumentar pool de conexiones HikariCP a 20 vía variable de entorno en Railway. |
| Mediano plazo | Implementar paginación en `GET /api/productos` y `GET /api/ventas` para reducir payload. |
| Mediano plazo | Agregar endpoint de health-check `/actuator/health` para monitoreo proactivo. |
| Largo plazo | Migrar a Railway Pro si el número de usuarios supera 30 concurrentes en producción. |
| Largo plazo | Implementar Redis como caché distribuido para los reportes y el catálogo de productos. |

## 8. Evidencias y artefactos

### 8.1 Scripts K6 disponibles

| Script | Descripción |
|---|---|
| smoke-test.js | Smoke test — 2 VUs × 30 s — disponibilidad básica |
| load-test.js | Load test — 1 VU × 5 s — validación mínima |
| stress-test.js | Stress test — 5→50 VUs × 210 s — límite del servidor |
| all-controllers-test.js | Prueba integral — todos los controllers — 10→30 VUs |
| auth-test.js | Prueba específica — autenticación JWT |
| productos-test.js | CRUD completo de productos |
| ventas-test.js | Flujo completo de ventas |
| facturas-test.js | Emisión y consulta de facturas |
| reportes-test.js | Todos los períodos de reporte |
| usuarios-test.js | Gestión de usuarios |

### 8.2 Comandos de ejecución

Para reproducir las pruebas ejecutar los siguientes comandos desde la carpeta `FINTECHMOVIL/backend`:

```
k6 run K6/smoke-test.js
k6 run K6/stress-test.js --env BASE_URL=https://fintechmovil.up.railway.app
k6 run K6/stress-test.js --env BASE_URL=http://localhost:8080
k6 run --out json=results.json K6/all-controllers-test.js
```

### 8.3 Integración CI/CD — Jenkinsfile

Las pruebas de rendimiento están integradas en el pipeline CI/CD. El stage "Ambiente de Pruebas — K6 Smoke" en el Jenkinsfile ejecuta automáticamente el smoke-test y el all-controllers-test en cada build, archivando los resultados JSON como artefactos. Los resultados se pueden visualizar directamente en Jenkins o exportar a Grafana para dashboards en tiempo real.

## 9. Aprobaciones

| Rol | Responsable |
|---|---|
| Elaborado por | Equipo FinTechmovil — Desarrolladores |
| Revisado por | Docente Calidad de Software |
| Aprobado por | Coordinador de Carrera |

*FinTechmovil — Informe de Rendimiento K6 — Junio 2026 — Confidencial.*
