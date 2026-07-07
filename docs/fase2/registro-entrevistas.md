# Registro de Entrevistas

!!! info "Ficha del documento"
    **Código:** RE-SDLC-ASTRALOG-001
    **Versión:** 1.0
    **Estado:** Aprobado
    **Fecha:** Junio 2026

    [:material-file-pdf-box: Descargar documento original (PDF)](../assets/entregables/fase2/registro-de-entrevistas.pdf)

## 1. Objetivo

Documentar la información obtenida durante las entrevistas realizadas al equipo de desarrollo del proyecto AstraLog, con el propósito de comprender la forma en que fueron ejecutadas las diferentes fases del Ciclo de Vida del Desarrollo de Software (SDLC) y recopilar evidencias que respalden el proceso de auditoría.

La información recopilada permite validar el cumplimiento de los procesos establecidos, identificar fortalezas, detectar oportunidades de mejora y sustentar los hallazgos presentados en el [Informe Final de Auditoría](../fase3/informe-final.md).

## 2. Alcance

Las entrevistas comprendieron a los integrantes del equipo responsable del desarrollo del sistema AstraLog y a los responsables de gestión del proyecto, arquitectura, desarrollo, pruebas y documentación, evaluando: gestión del proyecto, modelo SDLC, gestión de requisitos, arquitectura, desarrollo, control de versiones, pruebas, seguridad, documentación, despliegue, gestión de incidencias y aseguramiento de calidad.

## 3. Metodología

Las entrevistas se desarrollaron mediante reuniones estructuradas entre el equipo auditor y los integrantes del proyecto AstraLog, siguiendo un cuestionario previamente definido. Las respuestas fueron contrastadas posteriormente con la documentación del proyecto, el repositorio GitHub, Jira, SonarCloud, los diagramas de arquitectura y las demás evidencias disponibles.

## 4. Participantes

| Rol | Participante |
|---|---|
| Auditor Líder | MAMANI VARGAS, Anthony Kelman |
| Auditor Técnico | QUISPE QUISPE, Yunior Benito |
| Auditor Documental | RAMOS COAQUIRA, Jeimy Paul |
| Líder del Proyecto AstraLog | APAZA YUCRA, Elvis Jesús |
| Desarrollador Backend | APAZA YUCRA, Elvis Jesús |
| Desarrollador Frontend | MAMANI VARGAS, Anthony Kelman |
| Desarrollador Mobile | QUISPE QUISPE, Yunior Benito |
| Responsable de QA | RAMOS COAQUIRA, Jeimy Paul |

## 5. Cronograma de entrevistas

| Entrevista | Responsable | Fecha | Duración |
|---|---|---|---|
| Gestión del Proyecto | Líder del Proyecto | 15/06/2026 | 60 min |
| Arquitectura | Arquitecto del Sistema | 15/06/2026 | 60 min |
| Desarrollo Backend | Desarrollador Backend | 15/06/2026 | 60 min |
| Desarrollo Frontend | Desarrollador Frontend | 15/06/2026 | 60 min |
| Desarrollo Mobile | Desarrollador Mobile | 15/06/2026 | 60 min |
| Calidad y Pruebas | Responsable QA | 15/06/2026 | 60 min |

## 6. Temas evaluados

Modelo de desarrollo SDLC · Definición del sistema · Gestión de requisitos · Arquitectura del sistema y del software · Desarrollo y codificación · Pruebas unitarias · Revisión de código · Estándares de programación · Documentación · Despliegue · Integración · Gestión de defectos · Gestión del proyecto · Gestión de configuración · Plan de aseguramiento de calidad.

## 7. Entrevistas realizadas

### Entrevista N.° 01 — Gestión del Proyecto

**Entrevistado:** Líder del Proyecto AstraLog (Project Manager / Scrum Master) · **Fecha:** 15/06/2026, 09:00–10:00 · **Objetivo:** conocer la metodología utilizada para la planificación, gestión y seguimiento del proyecto.

| N.° | Pregunta | Respuesta |
|---|---|---|
| 1 | ¿Qué metodología de desarrollo utilizaron? | Scrum, organizada en sprints con reuniones de planificación, seguimiento y retrospectiva. |
| 2 | ¿Cómo se gestionaron las tareas del proyecto? | Mediante Jira: Product Backlog, Sprint Backlog y tableros Kanban. |
| 3 | ¿Quién aprobó los requisitos del sistema? | Definidos según las necesidades de ASTRAMACO III y validados por el docente y el equipo del proyecto. |
| 4 | ¿Cómo controlaron los cambios durante el desarrollo? | Registrados como nuevas historias de usuario o tareas en Jira antes de implementarse. |
| 5 | ¿Cómo realizaron el seguimiento del proyecto? | Reuniones semanales para revisar el avance de cada sprint. |
| 6 | ¿Qué herramienta utilizaron para el control del código? | GitHub, mediante ramas y Pull Requests. |
| 7 | ¿Cómo verificaban el cumplimiento del cronograma? | Comparando el avance de sprints contra lo planificado, ajustando tareas pendientes. |
| 8 | ¿Existía documentación del proyecto? | Sí: requisitos, arquitectura, manuales técnicos e informes finales. |
| 9 | ¿Se identificaron riesgos durante el proyecto? | Sí: retrasos, integración de módulos y disponibilidad del equipo. |
| 10 | ¿Cuál considera el mayor reto del proyecto? | La integración de los módulos y la coordinación del trabajo colaborativo. |

**Evidencias proporcionadas:** Product Backlog, Sprint Backlog, cronograma del proyecto, tablero Jira, repositorio GitHub.
**Conclusión:** conformidad con las buenas prácticas de gestión ágil de proyectos; evidencia suficiente para continuar con la revisión documental.

### Entrevista N.° 02 — Arquitectura del Sistema

**Entrevistado:** Arquitecto del Software · **Objetivo:** evaluar la arquitectura implementada y su correspondencia con la documentación del proyecto.

| N.° | Pregunta | Respuesta |
|---|---|---|
| 1 | ¿Qué arquitectura implementó AstraLog? | Arquitectura monolítica modular con Spring Boot. |
| 2 | ¿Por qué una arquitectura monolítica? | Facilita desarrollo, despliegue y mantenimiento en proyectos académicos con equipos pequeños. |
| 3 | ¿Cómo está organizado el sistema? | Módulos independientes: autenticación, pedidos, inventario, transportistas y reportes. |
| 4 | ¿Qué patrón arquitectónico utilizaron? | Modelo C4 para documentar la arquitectura. |
| 5 | ¿Existe separación entre frontend y backend? | Sí, se comunican mediante servicios REST. |
| 6 | ¿Cómo se gestiona la autenticación? | Mediante JWT y Spring Security. |
| 7 | ¿Qué base de datos utilizan? | MySQL. |
| 8 | ¿Cómo documentaron la arquitectura? | Diagramas C4 y diagramas UML elaborados con PlantUML. |
| 9 | ¿Se realizaron revisiones de arquitectura? | Sí, en las reuniones técnicas del proyecto. |
| 10 | ¿Considera escalable la solución? | Sí, por la modularidad implementada. |

**Evidencias:** diagramas C4, diagramas UML, arquitectura documentada.
**Conclusión:** existe consistencia entre la arquitectura documentada y la implementación del sistema.

!!! warning "Nota de este sitio"
    Los diagramas C4/UML mencionados aquí (EV-010, EV-011, EV-012, EV-034 en el [Registro de Evidencias](registro-evidencias.md)) residen en la documentación/repositorio propio del proyecto AstraLog, no se incluyeron como archivos de imagen entre los entregables recibidos para armar este sitio. Si el equipo los tiene disponibles, deben copiarse a `docs/assets/img/arquitectura/` y enlazarse desde [Evidencia de Desarrollo](evidencia-desarrollo.md).

### Entrevista N.° 03 — Desarrollo Backend

**Entrevistado:** Desarrollador Backend.

| Pregunta | Respuesta |
|---|---|
| ¿Qué framework utilizaron? | Spring Boot |
| ¿Qué lenguaje utilizaron? | Java |
| ¿Cómo gestionaron dependencias? | Maven |
| ¿Aplicaron principios SOLID? | Sí |
| ¿Cómo realizaron el control de versiones? | GitHub |
| ¿Realizaron revisiones de código? | Sí, mediante Pull Requests |
| ¿Cómo manejan las excepciones? | Global Exception Handler |
| ¿Cómo protegieron las APIs? | Spring Security + JWT |
| ¿Utilizaron SonarCloud? | Sí |
| ¿Se documentó el código? | Sí, mediante comentarios y documentación técnica |

**Evidencias:** repositorio GitHub, SonarCloud, código fuente. **Conclusión:** el backend sigue buenas prácticas de desarrollo y control de calidad.

### Entrevista N.° 04 — Desarrollo Frontend

**Entrevistado:** Desarrollador Frontend.

| Pregunta | Respuesta |
|---|---|
| Framework utilizado | Angular |
| IDE utilizado | WebStorm |
| Comunicación con backend | REST API |
| Gestión de rutas | Angular Router |
| Manejo de autenticación | JWT |
| Manejo de estados | Servicios de Angular |
| Validaciones | Formularios Reactivos |
| Diseño responsivo | Bootstrap |
| Control de versiones | GitHub |
| Documentación | Sí |

**Evidencias:** código Angular, manual técnico. **Conclusión:** el frontend presenta una adecuada organización y modularidad.

### Entrevista N.° 05 — Aplicación Móvil

**Entrevistado:** Desarrollador Mobile.

| Pregunta | Respuesta |
|---|---|
| Framework utilizado | Flutter |
| Lenguaje | Dart |
| Consumo de APIs | REST |
| Gestión de autenticación | JWT |
| Compatibilidad | Android |
| Pruebas realizadas | Funcionales |
| Gestión de errores | Try-Catch |
| Versionamiento | GitHub |
| Documentación | Sí |
| Estado del desarrollo | Finalizado |

**Evidencias:** proyecto Flutter, APK, repositorio. **Conclusión:** la aplicación móvil cumple con los requerimientos funcionales definidos.

### Entrevista N.° 06 — Calidad y Pruebas

**Entrevistado:** Responsable de QA.

| Pregunta | Respuesta |
|---|---|
| ¿Qué tipos de pruebas realizaron? | Unitarias, funcionales e integración |
| ¿Cómo gestionaron defectos? | Jira |
| ¿Se ejecutó SonarCloud? | Sí |
| ¿Hubo revisión de código? | Sí |
| ¿Cómo validaron requisitos? | Casos de prueba |
| ¿Se realizaron pruebas con usuarios? | Sí |
| ¿Se documentaron incidencias? | Sí |
| ¿Se verificó seguridad? | Sí |
| ¿Existe evidencia de pruebas? | Sí |
| ¿El sistema fue aceptado? | Sí |

**Evidencias:** casos de prueba, reportes SonarCloud, Jira. **Conclusión:** el proyecto presenta evidencia suficiente del proceso de aseguramiento de la calidad.

## 8. Resumen de entrevistas

| Entrevista | Estado | Evidencias obtenidas |
|---|---|---|
| Gestión del Proyecto | ✔ Realizada | Jira, Product Backlog, Sprint Backlog, Cronograma |
| Arquitectura | ✔ Realizada | Diagramas C4, UML, Documentación Técnica |
| Backend | ✔ Realizada | GitHub, Spring Boot, SonarCloud |
| Frontend | ✔ Realizada | Angular, WebStorm, Manual Técnico |
| Aplicación Móvil | ✔ Realizada | Flutter, APK, GitHub |
| Calidad y Pruebas | ✔ Realizada | Casos de prueba, Jira, SonarCloud |

## 9. Conclusiones generales

Una vez concluidas las entrevistas, el equipo auditor analizó la información recopilada para verificar la consistencia entre las respuestas obtenidas y la documentación del proyecto. Las evidencias identificadas sirvieron de base para el [Registro de Evidencias](registro-evidencias.md), los [Papeles de Trabajo](papeles-trabajo.md) y el [Informe Final de Auditoría](../fase3/informe-final.md).

## 10. Firmas

| Cargo | Nombre |
|---|---|
| Auditor Líder | MAMANI VARGAS, Anthony Kelman |
| Auditor Técnico | RAMOS COAQUIRA, Jeimy Paul |
| Auditor Documental | QUISPE QUISPE, Yunior Benito |
| Líder del Proyecto | APAZA YUCRA, Elvis Jesús |

Código del Documento: RE-SDLC-ASTRALOG-001 · Versión: 1.0 · Estado: Aprobado
