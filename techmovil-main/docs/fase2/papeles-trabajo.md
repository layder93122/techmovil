# Papeles de Trabajo de Auditoría

!!! info "Ficha del documento"
    **Código:** PT-SDLC-ASTRALOG-001
    **Versión:** 1.0
    **Estado:** Aprobado
    **Fecha:** Junio 2026

    [:material-file-pdf-box: Descargar documento original (PDF)](../assets/entregables/fase2/papeles-de-trabajo-auditoria.pdf)

## 1. Objetivo

Los Papeles de Trabajo constituyen el registro técnico de las actividades realizadas durante la Auditoría del Ciclo de Vida del Desarrollo de Software (SDLC) del sistema AstraLog. Documentan los procedimientos aplicados, las evidencias revisadas, los resultados obtenidos y las conclusiones emitidas por el equipo auditor.

## 2. Metodología

Cada papel de trabajo documenta un procedimiento específico de auditoría e incluye: objetivo de la revisión, criterio de auditoría, procedimiento aplicado, evidencias revisadas (referenciadas al [Registro de Evidencias](registro-evidencias.md), REV-SDLC-ASTRALOG-001), resultado obtenido, observaciones y conclusión.

### PT-001 — Gestión del Proyecto

- **Objetivo:** verificar que AstraLog fue planificado y gestionado siguiendo prácticas de gestión de proyectos de software.
- **Criterio:** existencia de planificación, cronograma, backlog y seguimiento del proyecto.
- **Procedimiento:** revisión del Project Charter, Product Backlog, Sprint Backlog, cronograma; entrevista al Líder del Proyecto.
- **Evidencias:** EV-001 a EV-005.
- **Resultado:** Cumple.
- **Observaciones:** se verificó la utilización de Scrum como metodología de desarrollo y Jira como herramienta de planificación y seguimiento.
- **Conclusión:** existe evidencia suficiente de una adecuada gestión del proyecto.

### PT-002 — Gestión de Requisitos

- **Objetivo:** verificar la correcta identificación y documentación de los requisitos funcionales y no funcionales.
- **Criterio:** existencia de documentación de requisitos y trazabilidad.
- **Procedimiento:** revisión del documento de requisitos, historias de usuario; entrevista al analista.
- **Evidencias:** EV-006 a EV-009.
- **Resultado:** Cumple.
- **Observaciones:** los requisitos fueron documentados y gestionados mediante historias de usuario en Jira.
- **Conclusión:** los requisitos presentan un nivel adecuado de documentación y organización.

### PT-003 — Arquitectura del Sistema

- **Objetivo:** evaluar la arquitectura implementada.
- **Procedimiento:** revisión de diagramas C4, revisión UML, comparación con el código fuente.
- **Evidencias:** EV-010 a EV-013.
- **Resultado:** Cumple.
- **Observaciones:** existe correspondencia entre la arquitectura documentada y la implementación.
- **Conclusión:** la arquitectura presenta coherencia y modularidad.

### PT-004 — Desarrollo del Software

- **Objetivo:** verificar las buenas prácticas de construcción del software.
- **Procedimiento:** revisión de GitHub, commits, Pull Requests, backend, frontend y Flutter.
- **Evidencias:** EV-014 a EV-019.
- **Resultado:** Cumple.
- **Observaciones:** el código se encuentra organizado por módulos y bajo control de versiones.
- **Conclusión:** el proceso de desarrollo evidencia buenas prácticas de Ingeniería de Software.

### PT-005 — Calidad y Pruebas

- **Objetivo:** verificar la existencia de procedimientos de aseguramiento de calidad.
- **Procedimiento:** revisión de casos de prueba, SonarCloud, incidencias.
- **Evidencias:** EV-020 a EV-023.
- **Resultado:** **Cumple Parcialmente.**
- **Observaciones:** existen evidencias de pruebas funcionales y de integración. Se recomienda **fortalecer la documentación de pruebas unitarias automatizadas.**
- **Conclusión:** el proceso de pruebas es adecuado, aunque presenta oportunidades de mejora.

!!! danger "Este es el hallazgo central de todo el sitio"
    Es el único PT con resultado "Cumple Parcialmente", y se confirma de forma independiente en [Evidencia de Desarrollo → Pruebas unitarias](evidencia-desarrollo.md#pruebas-unitarias-hallazgo): el documento "Pruebas Unitarias" entregado por el equipo de desarrollo no contiene ni una sola captura de evidencia (solo la carátula). Coincide con la no conformidad menor registrada en el [Informe Final](../fase3/informe-final.md).

### PT-006 — Seguridad

- **Objetivo:** verificar la implementación de controles de seguridad.
- **Procedimiento:** revisión de Spring Security, JWT, roles, configuración HTTPS.
- **Evidencias:** EV-024 a EV-027.
- **Resultado:** Cumple.
- **Observaciones:** los mecanismos de autenticación y autorización fueron correctamente implementados.
- **Conclusión:** el sistema presenta controles adecuados para proteger el acceso a la información.

### PT-007 — Base de Datos

- **Objetivo:** evaluar la gestión de la base de datos.
- **Procedimiento:** revisión del modelo MySQL, scripts SQL, respaldos.
- **Evidencias:** EV-028 a EV-030.
- **Resultado:** Cumple.
- **Observaciones:** la estructura de la base de datos es consistente con los requerimientos funcionales.
- **Conclusión:** la gestión de la base de datos cumple con los criterios establecidos.

### PT-008 — Documentación

- **Objetivo:** evaluar la documentación técnica y funcional.
- **Procedimiento:** revisión del informe final, manual técnico, manual de usuario, diagramas.
- **Evidencias:** EV-031 a EV-034.
- **Resultado:** Cumple.
- **Observaciones:** la documentación disponible facilita el mantenimiento y comprensión del sistema.
- **Conclusión:** la documentación presenta un nivel adecuado de completitud y organización.

## 3. Resumen de resultados

| Papel de Trabajo | Área | Resultado |
|---|---|---|
| PT-001 | Gestión del Proyecto | Cumple |
| PT-002 | Requisitos | Cumple |
| PT-003 | Diseño | Cumple |
| PT-004 | Desarrollo | Cumple |
| PT-005 | Calidad y Pruebas | **Cumple Parcialmente** |
| PT-006 | Seguridad | Cumple |
| PT-007 | Base de Datos | Cumple |
| PT-008 | Documentación | Cumple |

## 4. Conclusiones generales

La revisión efectuada evidencia que el proyecto AstraLog fue desarrollado siguiendo prácticas de Ingeniería de Software acordes con la metodología Scrum y apoyado por herramientas de gestión, control de versiones y aseguramiento de la calidad. Se verificó la existencia de documentación técnica, arquitectura, gestión de requisitos, control de versiones, mecanismos de autenticación y registros de pruebas que respaldan el proceso de desarrollo.

Como oportunidad de mejora, se recomienda **fortalecer la documentación de las pruebas unitarias automatizadas** y ampliar el registro de métricas de calidad para futuras versiones del sistema.

## 5. Aprobación

| Cargo | Nombre |
|---|---|
| Auditor Líder | MAMANI VARGAS, Anthony Kelman |
| Auditor Técnico | RAMOS COAQUIRA, Jeimy Paul |
| Auditor Documental | QUISPE QUISPE, Yunior Benito |

Código del Documento: PT-SDLC-ASTRALOG-001 · Versión: 1.0 · Estado: Aprobado
