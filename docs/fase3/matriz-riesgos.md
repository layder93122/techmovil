# Matriz de Riesgos de Auditoría SDLC

!!! info "Ficha del documento"
    **Código:** MR-SDLC-TECHMOVIL-001
    **Versión:** 1.0
    **Estado:** Aprobado

    [:material-file-pdf-box: Descargar documento original (PDF)](../assets/entregables/fase3/matriz-de-riesgos.pdf)

Auditoría del Ciclo de Vida del Desarrollo de Software (SDLC)
Proyecto: **Techmovil** – Solución Logística Centralizada

## Control del documento

| Campo | Información |
|---|---|
| Código | MR-SDLC-TECHMOVIL-001 |
| Documento | Matriz de Riesgos |
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
| 1.0 | Junio 2026 | Elaboración inicial de la Matriz de Riesgos | Equipo Auditor |

## 1. Objetivo

La presente Matriz de Riesgos tiene como finalidad identificar y evaluar los riesgos derivados de los hallazgos obtenidos durante la Auditoría del Ciclo de Vida del Desarrollo de Software (SDLC) del proyecto Techmovil, determinando su probabilidad, impacto y nivel de riesgo, así como las acciones de tratamiento recomendadas para minimizar sus efectos sobre la calidad y continuidad del sistema.

## 2. Metodología de evaluación

Para la valoración de los riesgos se utilizaron los siguientes criterios:

### Probabilidad

| Valor | Descripción |
|---|---|
| Baja | Poco probable que ocurra. |
| Media | Puede ocurrir en determinadas circunstancias. |
| Alta | Alta posibilidad de ocurrencia. |

### Impacto

| Valor | Descripción |
|---|---|
| Bajo | Consecuencias menores sobre el proyecto. |
| Medio | Afecta parcialmente la operación o calidad del sistema. |
| Alto | Compromete significativamente el funcionamiento o mantenimiento del sistema. |

### Nivel de riesgo

| Nivel | Interpretación |
|---|---|
| Bajo | Riesgo aceptable; requiere seguimiento. |
| Medio | Requiere acciones de mitigación. |
| Alto | Requiere atención prioritaria e inmediata. |

## 3. Matriz de riesgos

| Código | Riesgo Identificado | Hallazgo Relacionado | Probabilidad | Impacto | Nivel de Riesgo | Acción de Mitigación | Responsable |
|---|---|---|---|---|---|---|---|
| R-001 | Dificultad para detectar errores en futuras modificaciones debido a la limitada documentación de pruebas unitarias automatizadas. | H-006 | Media | Alto | Alto | Implementar pruebas unitarias automatizadas y documentar su ejecución para los módulos críticos. | Equipo de Desarrollo / QA |
| R-002 | Incremento del tiempo requerido para actividades de mantenimiento por documentación técnica insuficientemente detallada. | H-007 | Media | Medio | Medio | Actualizar y ampliar la documentación técnica y operativa del sistema. | Equipo Técnico |
| R-003 | Retrasos y mayor probabilidad de errores durante la implementación por ausencia de un proceso de integración y despliegue continuo (CI/CD). | H-009 | Media | Medio | Medio | Incorporar herramientas de integración y despliegue continuo que automaticen el proceso de liberación. | Equipo DevOps / Desarrollo |
| R-004 | Dificultad para gestionar incidencias posteriores al despliegue debido a la inexistencia de un procedimiento formal de mantenimiento. | H-010 | Media | Medio | Medio | Definir e implementar un procedimiento formal para la gestión de incidencias, cambios y mantenimiento correctivo. | Líder del Proyecto |
| R-005 | Desactualización de la arquitectura y documentación técnica ante futuras modificaciones del sistema. | H-003 / H-007 | Baja | Medio | Bajo | Establecer revisiones periódicas de la documentación arquitectónica y técnica. | Arquitecto del Software |
| R-006 | Configuración incorrecta de roles y permisos en futuras versiones del sistema. | H-005 | Baja | Alto | Medio | Revisar periódicamente la configuración de seguridad y realizar auditorías de permisos antes de cada liberación. | Administrador del Sistema |
| R-007 | Pérdida de información por fallas en la ejecución o seguimiento de los procedimientos de respaldo de la base de datos MySQL. | H-008 | Baja | Alto | Medio | Programar respaldos automáticos y realizar pruebas periódicas de restauración de la base de datos. | Administrador de Base de Datos |
| R-008 | Desviaciones en la planificación de futuras versiones del sistema si no se mantiene la disciplina en la gestión ágil del proyecto. | H-001 | Baja | Medio | Bajo | Continuar utilizando Scrum, Jira y reuniones periódicas de seguimiento para el control del proyecto. | Scrum Master |

## 4. Resumen de riesgos

| Nivel de Riesgo | Cantidad |
|---|---|
| Alto | 1 |
| Medio | 5 |
| Bajo | 2 |
| **Total** | **8** |

## 5. Priorización de riesgos

| Prioridad | Código | Riesgo |
|---|---|---|
| 1 | R-001 | Insuficiente documentación de pruebas unitarias automatizadas. |
| 2 | R-003 | Ausencia de un proceso de integración y despliegue continuo (CI/CD). |
| 3 | R-004 | Falta de un procedimiento formal para la gestión de incidencias y mantenimiento. |
| 4 | R-002 | Documentación técnica con oportunidades de mejora. |
| 5 | R-006 | Riesgo asociado a la configuración de roles y permisos. |
| 6 | R-007 | Riesgo relacionado con respaldos y recuperación de la base de datos. |
| 7 | R-005 | Desactualización de la documentación arquitectónica. |
| 8 | R-008 | Riesgo de desviaciones en la planificación de futuras versiones. |

## 6. Conclusiones

La evaluación de riesgos realizada evidencia que el proyecto Techmovil presenta un nivel adecuado de control sobre la mayoría de sus procesos de desarrollo. Los riesgos identificados se concentran principalmente en aspectos de mejora continua relacionados con la automatización de pruebas, la documentación técnica, la integración continua y la formalización del proceso de mantenimiento.

No se identificaron riesgos críticos que comprometan la viabilidad del sistema; sin embargo, la implementación de las acciones de mitigación propuestas permitirá fortalecer la calidad, confiabilidad y mantenibilidad del software en futuras versiones.

## 7. Aprobación

| Cargo | Nombre |
|---|---|
| Auditor Líder | Idonis Mijael Paye Trujillo |
| Auditor Documental | Kevin Edwin Sucapuca Calcinay |

**Código del Documento:** MR-SDLC-TECHMOVIL-001
**Versión:** 1.0
**Estado:** Aprobado
