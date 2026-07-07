# Checklist SDLC Adaptado

!!! info "Ficha del documento"
    **Código:** No especificado en el documento fuente (entregable "Checklist SDLC Adaptado a AstraLog")
    **Versión:** 1.0
    **Estado:** Emitido

    [:material-file-pdf-box: Descargar documento original (PDF)](../assets/entregables/fase1/checklist-sdlc-astralog.pdf)

## Lista de Verificación SDLC (SDLC Audit Checklist)

La siguiente lista de verificación se basa en el *System Development Life Cycle Audit Checklist* estándar. Constituye el instrumento operativo de recolección de evidencia para cada objetivo de auditoría.

Columnas: **Código** · **Pregunta** (objetivo / ítem de verificación) · **Cumple** (Sí/No) · **Observaciones**.

### METODOLOGÍA SDLC

| Código | Pregunta | Cumple | Observaciones |
|---|---|---|---|
| CL-01-01 | ¿Se han determinado el alcance de las responsabilidades de la dirección, auditoría interna, usuarios, QA y procesamiento de datos durante el diseño, desarrollo y mantenimiento del sistema? | Sí | — |
| CL-01-02 | ¿Los workpapers del SDLC evidencian que se obtuvieron los niveles de autorización apropiados para cada fase? | Sí | El jefe de la empresa y los docentes al ver le viabilidad |
| CL-01-03 | ¿Existe una metodología formal de desarrollo implantada y soportada por herramientas CASE? | Sí | SCRUM como base para reestructurar el proyecto en cada sprint |
| CL-01-04 | ¿El proyecto tiene un plan formal de proyecto documentado? | Sí | — |
| CL-01-05 | ¿El proyecto tiene definición de alcance y marco formal? | Sí | Se encuentra en los requerimientos |
| CL-01-06 | ¿Están especificados los entregables principales, plazos y roles/responsabilidades? | Sí | — |
| CL-01-07 | ¿Se realiza análisis de riesgos del proyecto? | Sí | Se encuentra en las pruebas unitarias y de integración |
| CL-01-08 | ¿Se están siguiendo los procedimientos definidos para el área de desarrollo? | Sí | Se encargó al desarrollador fechas estrictas para realizar |

### ANÁLISIS DE NECESIDADES

| Código | Pregunta | Cumple | Observaciones |
|---|---|---|---|
| CL-02-01 | ¿Existen procedimientos formales para realizar el análisis de necesidades? | Sí | Encuestas en Google Cuestionarios |
| CL-02-02 | ¿El análisis de necesidades de un proyecto reciente cumple con los estándares establecidos? | No | Cumplen parcialmente |
| CL-02-03 | ¿Existe un mecanismo para registrar necesidades de desarrollo con descripción, riesgos y análisis coste/beneficio? | Sí | Sí, debido a los requisitos de mi empresa |
| CL-02-04 | ¿Qué tipo de especificaciones de requisitos se están empleando? | Sí | Requerimientos funcionales |
| CL-02-05 | ¿Dónde se almacenan los requisitos? ¿Se usa una herramienta estándar? | Sí | Jira |
| CL-02-06 | ¿Existe un proceso de revisión de requisitos? | Sí | La empresa y el ingeniero corrigiendo funcionalidades repetidas |
| CL-02-07 | ¿Se gestiona la trazabilidad de requisitos? | No | Parcialmente, con el manual de usuario |

### DISEÑO Y DESARROLLO

| Código | Pregunta | Cumple | Observaciones |
|---|---|---|---|
| CL-03-01 | ¿Se han revisado las especificaciones de diseño y hay evidencia escrita de aprobación? | Sí | Se demostró antes a ingenieros de la arquitectura de la base de datos |
| CL-03-02 | ¿Las especificaciones de diseño cumplen con los estándares? | Sí | UML |
| CL-03-03 | ¿Se incorporan pista de auditoría y controles programados en las especificaciones de diseño? | Sí | Sí, con los usuarios ADMIN, USER, TRANSPORTISTA |
| CL-03-04 | ¿Los documentos fuente para entrada de datos están diseñados para facilitar la captura precisa? | Sí | Sí, informa respecto en el manual de uso y del usuario |
| CL-03-05 | ¿Los programas cumplen con los estándares de programación del área? | Sí | Filtrado por SonarQube, con coverage al 87.5% |
| CL-03-06 | ¿Existen estándares documentados de codificación en una wiki colaborativa? | No | Parcialmente, ya que no cuenta con un README dentro del código, pero sí la estructura de carpetas |
| CL-03-07 | ¿El equipo trabaja con un diseñador desde el inicio del proyecto? | Sí | — |
| CL-03-08 | ¿Se utiliza un sistema de control de versiones conforme a las mejores prácticas? | Sí | — |
| CL-03-09 | ¿Existe integración continua / despliegue continuo (CI/CD)? | Sí | — |

### PROCEDIMIENTOS DE PRUEBA

| Código | Pregunta | Cumple | Observaciones |
|---|---|---|---|
| CL-04-01 | ¿Existen procedimientos documentados de prueba de sistemas y programas? | Sí | — |
| CL-04-02 | ¿Los procedimientos de prueba, datos de prueba y resultados son comprensivos y siguen los estándares? | Sí | — |
| CL-04-03 | ¿Son adecuadas las pruebas realizadas sobre las fases manuales de la aplicación? | Sí | — |
| CL-04-04 | ¿Existe una estrategia de pruebas documentada? | No | Solo se gestionan las pruebas unitarias y de integración respecto al sistema |
| CL-04-05 | ¿Hay proceso de revisión de calidad y métricas de software? | Sí | — |
| CL-04-06 | ¿Se gestiona la integración de software y la documentación de pruebas? | Sí | — |
| CL-04-07 | ¿Existe un sistema de gestión de defectos? | Sí | Se prueban reestructurando las pruebas unitarias |
| CL-04-08 | ¿Cómo funciona el proceso de revisión de código? | Sí | En GitHub, de forma colaborativa |

### PROCEDIMIENTOS DE IMPLEMENTACIÓN

| Código | Pregunta | Cumple | Observaciones |
|---|---|---|---|
| CL-05-01 | ¿Existen procedimientos formales de promoción e implementación de programas? | Sí | De forma automatizada |
| CL-05-02 | ¿La documentación del procedimiento de promoción muestra que los estándares se siguen? | Sí | En las historias de GitHub Actions |
| CL-05-03 | ¿Los cambios seleccionados tienen registros de soporte que evidencian aprobación adecuada? | Sí | El jefe de la empresa |
| CL-05-04 | ¿La documentación de la implementación de nuevas aplicaciones muestra que se siguieron los procedimientos? | Sí | — |
| CL-05-05 | ¿Existe un proceso de gestión de cambios con autorización formal? | No | No hay documentos para la gestión de cambios |
| CL-05-06 | ¿Cómo funciona el workflow configurado para el despliegue? | Sí | Existe el documento de pruebas y despliegue |

### REVISIÓN POST-IMPLEMENTACIÓN

| Código | Pregunta | Cumple | Observaciones |
|---|---|---|---|
| CL-06-01 | ¿Existen procedimientos formales de revisión post-implementación? | No | No se cuenta con procedimientos después del despliegue |
| CL-06-02 | ¿Las modificaciones de programas, procedimientos de prueba y documentación de soporte siguen los estándares? | No | Todavía no se hicieron |
| CL-06-03 | ¿Se documentan y gestionan las lecciones aprendidas? | Sí | Se anotaron los cambios y procedimientos |
| CL-06-04 | ¿Se realiza seguimiento de los objetivos del sistema tras la implementación? | Sí | Parcialmente, cumpliendo con las expectativas |

### MANTENIMIENTO DE APLICACIONES

| Código | Pregunta | Cumple | Observaciones |
|---|---|---|---|
| CL-07-01 | ¿Existen procedimientos formales de mantenimiento de aplicaciones? | No | No se tienen procedimientos |
| CL-07-02 | ¿Las modificaciones de programas, pruebas y documentación siguen los estándares? | Sí | Cada módulo cuenta con su tabla de auditoría |
| CL-07-03 | ¿Se gestiona el mantenimiento evolutivo y correctivo de manera diferenciada? | Sí | En cada merge, antes de unificarlos |
| CL-07-04 | ¿Existe un catálogo de componentes software reutilizables accesible y actualizado? | Sí | — |

### CONTROL SOBRE SOFTWARE DE SISTEMA

| Código | Pregunta | Cumple | Observaciones |
|---|---|---|---|
| CL-08-01 | ¿Existen procedimientos formales de modificación de software de sistema? | Sí | — |
| CL-08-02 | ¿Las modificaciones de software de sistema tienen pruebas y documentación de soporte siguiendo los estándares? | Sí | Con las pruebas integrales |
| CL-08-03 | ¿Existe documentación del software de sistema desarrollado internamente y de las características del software propietario? | Sí | — |
| CL-08-04 | ¿Los lenguajes, compiladores y herramientas CASE han sido previamente homologados? | Sí | Las herramientas son estándares |

### ESTÁNDARES DE DOCUMENTACIÓN

| Código | Pregunta | Cumple | Observaciones |
|---|---|---|---|
| CL-09-01 | ¿Los estándares de documentación son completos y cubren todos los artefactos del SDLC? | No | No completos en un formato, pero sí para la construcción inicial |
| CL-09-02 | ¿Existe un estándar general para documentación técnica (análisis, diseño, programas, cuadernos de carga)? | Sí | Existen documentos requeridos para documentar todo |
| CL-09-03 | ¿Existe un estándar para manuales de usuario y procedimientos de operación? | Sí | El manual de usuario |
| CL-09-04 | ¿Los estándares son conocidos y respetados en el área? | Sí | — |
| CL-09-05 | ¿Las modificaciones a estándares se difunden oportunamente dentro del área? | No | Cambia de reglas, pero no hay dónde avisar excepto en GitHub, de forma colaborativa |
| CL-09-06 | ¿La documentación permite la trazabilidad completa a lo largo del ciclo de vida? | Sí | Sí, porque cuenta con documentos para eso |
