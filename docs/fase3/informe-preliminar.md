# Informe Preliminar de Auditoría SDLC

!!! info "Ficha del documento"
    **Código:** IPA-SDLC-ASTRALOG-001
    **Versión:** 1.0
    **Estado:** Borrador para revisión

    [:material-file-pdf-box: Descargar documento original (PDF)](../assets/entregables/fase3/informe-preliminar-auditoria-sdlc.pdf)

Auditoría del Ciclo de Vida del Desarrollo de Software (SDLC)
Proyecto: **AstraLog** – Solución Logística Centralizada

## Control del documento

| Campo | Información |
|---|---|
| Código | IPA-SDLC-ASTRALOG-001 |
| Documento | Informe Preliminar de Auditoría |
| Proyecto | AstraLog |
| Organización Auditada | ASTRAMACO III |
| Auditoría | Auditoría SDLC |
| Versión | 1.0 |
| Fecha | Junio 2026 |
| Elaborado por | Equipo Auditor |
| Estado | Borrador para revisión |

### Historial de versiones

| Versión | Fecha | Descripción | Responsable |
|---|---|---|---|
| 1.0 | Junio 2026 | Emisión del Informe Preliminar | Equipo Auditor |

## 1. Introducción

En cumplimiento del Plan de Auditoría SDLC aprobado para el proyecto AstraLog, el equipo auditor llevó a cabo la evaluación de los procesos aplicados durante el Ciclo de Vida del Desarrollo de Software (SDLC).

La auditoría tuvo como propósito verificar el grado de cumplimiento de los criterios definidos en el Project Charter, considerando como referencia las buenas prácticas de ISO/IEC 12207, ISO/IEC 25010, CMMI-DEV y el Checklist SDLC, mediante la revisión de documentación, entrevistas, evidencias técnicas y papeles de trabajo.

El presente informe consolida los resultados obtenidos hasta esta etapa y será presentado al equipo auditado para su revisión y comentarios antes de la emisión del Informe Final de Auditoría.

## 2. Objetivo

Presentar de manera preliminar los resultados de la Auditoría del Ciclo de Vida del Desarrollo de Software (SDLC) del proyecto AstraLog, identificando las conformidades, oportunidades de mejora, no conformidades y riesgos detectados, con el fin de que el equipo auditado pueda revisar la información, formular observaciones y proporcionar aclaraciones antes de la emisión del informe definitivo.

## 3. Alcance

La auditoría comprendió la revisión de los siguientes procesos del proyecto AstraLog:

- Gestión del proyecto.
- Gestión de requisitos.
- Diseño y arquitectura del sistema.
- Desarrollo del software.
- Gestión de configuración y control de versiones.
- Calidad y pruebas.
- Seguridad.
- Implementación.
- Mantenimiento.
- Documentación técnica y funcional.

## 4. Metodología aplicada

Durante la auditoría se ejecutaron las siguientes actividades:

- Revisión del Project Charter.
- Entrevistas al equipo de desarrollo.
- Revisión de la documentación técnica y funcional.
- Evaluación del repositorio GitHub.
- Revisión del tablero Jira.
- Revisión de los diagramas arquitectónicos.
- Revisión de SonarCloud.
- Revisión de la evidencia de pruebas.
- Elaboración de Papeles de Trabajo.
- Evaluación de cumplimiento frente a los criterios de auditoría.

## 5. Documentación revisada

Como parte del proceso de auditoría fueron revisados, entre otros, los siguientes documentos y artefactos:

- Project Charter de Auditoría SDLC.
- Registro de Entrevistas.
- Registro de Evidencias.
- Papeles de Trabajo de Auditoría.
- Documentación de requisitos.
- Diagramas C4 y UML.
- Manual técnico.
- Manual de usuario.
- Repositorio GitHub.
- Tablero Jira.
- Reportes SonarCloud.
- Modelo de base de datos MySQL.

## 6. Resumen de hallazgos

Como resultado de la evaluación se identificaron los siguientes hallazgos:

| Clasificación | Cantidad |
|---|---|
| Conformidades | 6 |
| Oportunidades de Mejora | 3 |
| No Conformidades Menores | 1 |
| No Conformidades Mayores | 0 |
| **Total** | **10** |

Los principales resultados obtenidos fueron:

- Se verificó una adecuada gestión del proyecto mediante la metodología Scrum y el uso de Jira.
- Los requisitos del sistema fueron documentados y gestionados de forma organizada.
- La arquitectura monolítica modular implementada mantiene coherencia con la documentación técnica.
- Se comprobó el uso de GitHub para el control de versiones y la gestión colaborativa del código.
- Los mecanismos de autenticación mediante JWT y Spring Security fueron implementados correctamente.
- Se identificó como oportunidad de mejora la ampliación de la documentación técnica y la automatización del proceso de despliegue.
- Se detectó una no conformidad menor relacionada con la documentación de pruebas unitarias automatizadas.

## 7. Resumen de riesgos

La evaluación de riesgos permitió identificar los siguientes niveles:

| Nivel | Cantidad |
|---|---|
| Alto | 1 |
| Medio | 5 |
| Bajo | 2 |
| **Total** | **8** |

El riesgo con mayor prioridad corresponde a la limitada documentación de pruebas unitarias automatizadas, ya que podría dificultar la detección temprana de defectos en futuras modificaciones del sistema.

## 8. Observaciones preliminares

El equipo auditor considera que el proyecto AstraLog presenta un nivel satisfactorio de cumplimiento de los criterios establecidos para el desarrollo de software.

No obstante, se recomienda fortalecer los siguientes aspectos antes del cierre de la auditoría:

- Documentar de forma más detallada las pruebas unitarias automatizadas.
- Formalizar un procedimiento para la gestión de incidencias y mantenimiento.
- Evaluar la incorporación de un proceso de integración y despliegue continuo (CI/CD).
- Mantener actualizada la documentación técnica del sistema.

## 9. Recomendaciones preliminares

Con base en los resultados obtenidos, se proponen las siguientes acciones:

- Implementar un plan de documentación de pruebas unitarias para los módulos críticos.
- Definir un procedimiento formal para la gestión de mantenimiento e incidencias.
- Incorporar herramientas de integración continua y despliegue continuo que optimicen la liberación de nuevas versiones.
- Actualizar periódicamente los diagramas de arquitectura y la documentación técnica.
- Mantener revisiones periódicas de seguridad y calidad del código mediante SonarCloud y revisiones de código.

## 10. Comentarios del equipo auditado

El presente informe será remitido al equipo del proyecto AstraLog para que, dentro del plazo establecido por el Plan de Auditoría, pueda presentar observaciones, aclaraciones o evidencias adicionales respecto a los resultados obtenidos.

Las observaciones recibidas serán evaluadas por el equipo auditor y, de ser pertinentes, serán incorporadas en el Informe Final de Auditoría.

## 11. Conclusión preliminar

De manera preliminar, el equipo auditor concluye que el proyecto AstraLog evidencia un adecuado cumplimiento de las prácticas de Ingeniería de Software evaluadas durante la auditoría del SDLC.

Las conformidades identificadas superan ampliamente las oportunidades de mejora y la única no conformidad detectada corresponde a un aspecto documental que no compromete la operación ni la funcionalidad del sistema. En consecuencia, el proyecto presenta condiciones favorables para continuar con su evolución, siempre que se implementen las acciones de mejora recomendadas.

## 12. Aprobación

| Cargo | Nombre |
|---|---|
| Auditor Líder | MAMANI VARGAS, Anthony Kelman |
| Auditor Técnico | RAMOS COAQUIRA, Jeimy Paul |
| Auditor Documental | QUISPE QUISPE, Yunior Benito |

**Código del Documento:** IPA-SDLC-ASTRALOG-001
**Versión:** 1.0
**Estado:** Borrador para revisión
