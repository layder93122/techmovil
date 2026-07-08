# Matriz de Hallazgos de Auditoría SDLC

!!! info "Ficha del documento"
    **Código:** MH-SDLC-TECHMOVIL-001
    **Versión:** 1.0
    **Estado:** Aprobado

    [:material-file-pdf-box: Descargar documento original (PDF)](../assets/entregables/fase3/matriz-de-hallazgos.pdf)

Auditoría del Ciclo de Vida del Desarrollo de Software (SDLC)
Proyecto: **Techmovil** – Solución Logística Centralizada

## Control del documento

| Campo | Información |
|---|---|
| Código | MH-SDLC-TECHMOVIL-001 |
| Documento | Matriz de Hallazgos de Auditoría |
| Proyecto | Techmovil |
| Organización Auditada | Techmovil |
| Auditoría | Auditoría SDLC |
| Versión | 1.0 |
| Fecha | Junio 2026 |
| Elaborado por | Equipo Auditor |
| Estado | Aprobado |

### Historial de versiones

| Versión | Fecha | Descripción | Responsable |
|---|---|---|---|
| 1.0 | Junio 2026 | Elaboración inicial de la Matriz de Hallazgos | Equipo Auditor |

## 1. Objetivo

La presente Matriz de Hallazgos tiene como objetivo documentar los resultados obtenidos durante la Auditoría del Ciclo de Vida del Desarrollo de Software (SDLC) del sistema Techmovil, identificando el grado de cumplimiento de los criterios de auditoría establecidos, las evidencias revisadas, los impactos asociados y las recomendaciones propuestas para fortalecer la calidad del proyecto.

## 2. Criterios de clasificación

Los hallazgos identificados durante la auditoría se clasifican de la siguiente manera:

| Clasificación | Descripción |
|---|---|
| Conformidad | El proceso o actividad cumple con los criterios establecidos y existe evidencia suficiente que lo respalda. |
| Oportunidad de Mejora | El proceso cumple con los criterios; sin embargo, se identifican acciones que podrían incrementar la eficiencia, mantenibilidad o calidad. |
| No Conformidad Menor | Se detecta un incumplimiento parcial que no compromete significativamente la operación del sistema, pero requiere acciones correctivas. |
| No Conformidad Mayor | Incumplimiento significativo que afecta el cumplimiento de los objetivos del proyecto o representa un riesgo importante. |

## 3. Matriz de hallazgos

| Código | Área Auditada | Hallazgo | Criterio de Auditoría | Evidencia | Clasificación | Impacto | Recomendación |
|---|---|---|---|---|---|---|---|
| H-001 | Gestión del Proyecto | Se verificó la planificación y seguimiento del proyecto mediante Scrum y Jira. | ISO/IEC 12207 – Gestión del Proyecto | EV-001 al EV-005 / PT-001 | Conformidad | Favorece el control y seguimiento del proyecto. | Mantener las prácticas de gestión implementadas. |
| H-002 | Gestión de Requisitos | Los requisitos funcionales y no funcionales fueron documentados y gestionados mediante historias de usuario. | ISO/IEC 12207 – Ingeniería de Requisitos | EV-006 al EV-009 / PT-002 | Conformidad | Reduce el riesgo de desviaciones durante el desarrollo. | Mantener la trazabilidad entre requisitos y funcionalidades. |
| H-003 | Arquitectura del Sistema | La arquitectura monolítica modular documentada presenta coherencia con la implementación desarrollada. | Modelo C4 / Buenas prácticas de diseño | EV-010 al EV-013 / PT-003 | Conformidad | Facilita el mantenimiento y evolución del sistema. | Actualizar la documentación cuando existan cambios arquitectónicos. |
| H-004 | Desarrollo del Software | Se evidenció el uso de GitHub, control de versiones y revisiones mediante Pull Requests. | CMMI-DEV – Gestión de Configuración | EV-014 al EV-019 / PT-004 | Conformidad | Incrementa la calidad del código y el trabajo colaborativo. | Continuar aplicando revisiones de código en futuras versiones. |
| H-005 | Seguridad | Se comprobó la implementación de autenticación mediante JWT, Spring Security y control de acceso basado en roles. | ISO/IEC 25010 – Seguridad | EV-024 al EV-027 / PT-006 | Conformidad | Protege el acceso a la información del sistema. | Revisar periódicamente las políticas de seguridad y permisos. |
| H-006 | Calidad y Pruebas | Se realizaron pruebas funcionales y de integración; sin embargo, la evidencia de pruebas unitarias automatizadas es limitada. | ISO/IEC 12207 – Pruebas de Software | EV-020 al EV-023 / PT-005 | No Conformidad Menor | Puede dificultar la detección temprana de errores en futuras modificaciones. | Implementar y documentar pruebas unitarias automatizadas para los módulos críticos. |
| H-007 | Documentación | El proyecto dispone de documentación técnica y manuales; no obstante, algunos procedimientos pueden detallarse con mayor profundidad. | Estándares de Documentación | EV-031 al EV-034 / PT-008 | Oportunidad de Mejora | Facilita el mantenimiento y la transferencia del conocimiento. | Complementar la documentación técnica con procedimientos de mantenimiento y despliegue. |
| H-008 | Base de Datos | La estructura de la base de datos MySQL es consistente con los requisitos funcionales del sistema. | Buenas prácticas de diseño de bases de datos | EV-028 al EV-030 / PT-007 | Conformidad | Favorece la integridad y disponibilidad de la información. | Mantener el control de versiones de scripts SQL y respaldos periódicos. |
| H-009 | Implementación | El sistema dispone de procedimientos de instalación; sin embargo, no se evidencia un proceso automatizado de integración y despliegue continuo (CI/CD). | Buenas prácticas DevOps | EV-017, EV-018, EV-019 | Oportunidad de Mejora | La implementación depende de procesos manuales, aumentando el tiempo de despliegue. | Evaluar la incorporación de herramientas de integración y despliegue continuo. |
| H-010 | Mantenimiento | Se identificó el control de versiones del software, aunque no existe un procedimiento formal para la gestión de incidencias posteriores al despliegue. | ISO/IEC 12207 – Mantenimiento | EV-014, EV-023 | Oportunidad de Mejora | Puede dificultar el seguimiento y resolución de incidentes en producción. | Definir un procedimiento formal para la gestión de incidencias y solicitudes de mantenimiento. |

## 4. Resumen de hallazgos

| Clasificación | Cantidad |
|---|---|
| Conformidades | 6 |
| Oportunidades de Mejora | 3 |
| No Conformidades Menores | 1 |
| No Conformidades Mayores | 0 |
| **Total de Hallazgos** | **10** |

## 5. Conclusiones generales

Como resultado de la evaluación realizada, se concluye que el proyecto Techmovil presenta un adecuado nivel de cumplimiento de los criterios establecidos para la Auditoría del Ciclo de Vida del Desarrollo de Software (SDLC).

Las evidencias revisadas demuestran la aplicación de buenas prácticas en la gestión del proyecto, el desarrollo del software, la arquitectura del sistema, la seguridad, la gestión de requisitos y el control de versiones.

No obstante, se identificaron oportunidades de mejora relacionadas con la documentación técnica, la automatización del proceso de despliegue y la formalización del proceso de mantenimiento. Asimismo, se detectó una no conformidad menor asociada a la limitada documentación de pruebas unitarias automatizadas, aspecto que se recomienda fortalecer en futuras versiones del sistema.

## 6. Aprobación

| Cargo | Nombre |
|---|---|
| Auditor Líder | Idonis Mijael Paye Trujillo |
| Auditor Técnico |  |
| Auditor Documental | Kevin Edwin Sucapuca Calcinay |

**Código del Documento:** MH-SDLC-TECHMOVIL-001
**Versión:** 1.0
**Estado:** Aprobado
