# Casos de Prueba IEEE 829

*Techmovil ERP — Gestión de Inventario y Ventas de Celulares · v1.0.0 · Estándar IEEE 829 / ISO/IEC 29119*

## 1. Plan de pruebas

| Campo | Detalle |
|---|---|
| Proyecto / Sistema | Techmovil ERP — Gestión de Inventario y Ventas de Celulares |
| Versión / Release | v1.0.0 — Build Junio 2026 |
| Módulos bajo prueba | Login · Dashboard · Productos · Inventario · Ventas POS · Reportes · Clientes |
| Responsable QA | Equipo QA Techmovil |
| Fecha inicio / fin planificado | 02/06/2026 |
| Entorno de prueba | `http://localhost:5173` (React 19) · `http://localhost:8080/api` (Spring Boot) |
| Herramientas | Postman v11 · k6 0.50 · Jira Software · SonarQube Community |

### Resumen de resultados

| Métrica | Total | Ejecutados | Pasados | Fallidos | Bloqueados | Omitidos |
|---|---|---|---|---|---|---|
| Techmovil ERP | 17 | 16 | 13 | 1 | 0 | 2 |

### Leyenda de estados

| Estado | Significado |
|---|---|
| PASS | El caso de prueba fue ejecutado y el resultado es el esperado. |
| FAIL | El caso fue ejecutado y el resultado NO coincide con lo esperado. Se genera defecto. |
| BLOQUEADO | No puede ejecutarse porque otro defecto o dependencia lo impide. |
| PENDIENTE | Aún no ha sido ejecutado en este ciclo de pruebas. |
| OMITIDO | Se decidió no ejecutar en este ciclo (descartado o diferido). |

## 2. Casos de prueba

| ID | Módulo | Nombre del Caso | Tipo | Prioridad | Resultado | Defecto ID |
|---|---|---|---|---|---|---|
| TC-TM-001 | Login | Login exitoso con credenciales válidas | Funcional | Alta | PASS | — |
| TC-TM-002 | Login | Login con credenciales incorrectas | Funcional | Alta | PASS | — |
| TC-TM-003 | Dashboard | Dashboard muestra 4 KPIs y gráficos | Funcional | Alta | PASS | — |
| TC-TM-004 | Productos | Catálogo muestra todos los productos | Funcional | Alta | PASS | — |
| TC-TM-005 | Productos | Registrar nuevo producto | Funcional | Alta | PASS | — |
| TC-TM-006 | Productos | Búsqueda en tiempo real | Funcional | Media | PASS | — |
| TC-TM-007 | Inventario | Registrar entrada de stock | Funcional | Alta | PASS | — |
| TC-TM-008 | Inventario | Impide salida con stock=0 | Funcional | Alta | PASS | — |
| TC-TM-009 | Ventas | Agregar productos al carrito POS | Funcional | Alta | PASS | — |
| TC-TM-010 | Ventas | Flujo completo de venta — Happy Path | Sistema | Alta | PASS | — |
| TC-TM-011 | Ventas | Impide venta con carrito vacío | Funcional | Media | PASS | — |
| TC-TM-012 | Reportes | Ver reportes estadísticos | Funcional | Media | PASS | — |
| TC-TM-013 | Clientes | Registrar nuevo cliente | Funcional | Media | PASS | — |
| TC-TM-014 | Clientes | Búsqueda de clientes | Funcional | Baja | PASS | — |
| TC-TM-015 | Sesión | Cerrar sesión del sistema | Funcional | Alta | PASS | — |
| TC-TM-F | Reportes | Exportar reportes a Excel | Funcional | Alta | **FAIL** | BUG-TM-001 |
| TC-TM-S01 | Sistema | Alertas stock bajo en Dashboard | Sistema | Alta | PENDIENTE | — |

### Detalle de casos seleccionados

**TC-TM-003 — Dashboard muestra 4 KPIs y gráficos**
Resultado obtenido: 23 ventas, S/155K, 152 unidades, 3 alertas — 4 KPIs + gráfico de barras + alertas, todo correcto.

**TC-TM-009 — Agregar productos al carrito POS**
iPhone 15 Pro + Samsung S24 → 2 ítems, total **S/10,499.64** con IGV 18% calculado correctamente.

**TC-TM-F — Exportar reportes a Excel (FAIL)**
Al hacer clic en "📥 Exportar Excel" el sistema solo muestra un toast informativo ("Exportando reporte a Excel...") pero **no genera ningún archivo `.xlsx` descargable**. Genera el defecto BUG-TM-001.

**TC-TM-S01 — Alertas de stock bajo (PENDIENTE)**
Diferido a Sprint 5. Productos identificados con stock bajo: Xiaomi 14 Pro (2 unidades), Realme C65 (0 unidades).

## 3. Reporte de defectos

| ID Defecto | TC Relacionado | Módulo | Título | Severidad | Prioridad | Estado | Asignado a |
|---|---|---|---|---|---|---|---|
| BUG-TM-001 | TC-TM-F | Reportes | Exportación a Excel no genera archivo descargable | Media | Alta | Nuevo | Dev-Team |

**Descripción:** al hacer clic en "📥 Exportar Excel" del módulo Reportes, el sistema solo muestra un toast informativo pero no genera ningún archivo `.xlsx` descargable.

**Resultado esperado:** debe generarse y descargarse un archivo `.xlsx` con ventas e ingresos del período.

**Observaciones / recomendación:** implementar SheetJS en el frontend para la generación de Excel en el cliente.

## 4. Dashboard de métricas por módulo

| Módulo | Total CP | PASS | FAIL | BLOQUEADO | PENDIENTE |
|---|---|---|---|---|---|
| Login | 2 | 2 | 0 | 0 | 0 |
| Dashboard | 2 | 2 | 0 | 0 | 0 |
| Productos | 3 | 3 | 0 | 0 | 0 |
| Inventario | 2 | 2 | 0 | 0 | 0 |
| Ventas POS | 3 | 3 | 0 | 0 | 0 |
| Reportes | 2 | 1 | 1 | 0 | 0 |
| Clientes | 2 | 2 | 0 | 0 | 0 |
| Sesión/Sistema | 1 | 1 | 0 | 0 | 0 |

## 5. Guía de uso de la plantilla

Esta planilla sigue el estándar **IEEE 829** e **ISO/IEC 29119**, y es compatible con Jira, TestRail, Zephyr y Azure DevOps — pensada para que el equipo QA registre casos, defectos y métricas de forma trazable entre sprints.

[:material-file-excel-box: Descargar planilla completa (XLSX)](../assets/entregables/tecnico/casos-prueba-ieee829.xlsx)
