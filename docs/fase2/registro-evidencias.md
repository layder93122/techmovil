# Registro de Evidencias

!!! info "Ficha del documento"
    **Código:** REV-SDLC-TECHMOVIL-001
    **Versión:** 1.0
    **Estado:** Aprobado
    **Fecha:** Junio 2026

    [:material-file-pdf-box: Descargar documento original (PDF)](../assets/entregables/fase2/registro-de-evidencias.pdf)

## 1. Objetivo

Documentar y organizar todas las evidencias recopiladas durante la Auditoría del Ciclo de Vida del Desarrollo de Software (SDLC) del sistema Techmovil, permitiendo sustentar objetivamente las conclusiones, observaciones y hallazgos, y garantizar la trazabilidad entre los criterios evaluados y la información analizada.

## 2. Alcance

Evidencias obtenidas mediante: entrevistas al equipo de desarrollo, revisión de documentación técnica, revisión del repositorio GitHub, revisión del tablero Jira, revisión de SonarCloud, revisión de la arquitectura del sistema, revisión del código fuente, revisión de casos de prueba, revisión de manuales del sistema y revisión de la configuración del proyecto.

## 3. Criterios para el registro de evidencias

Cada evidencia registrada contiene: código único, área auditada, descripción, fuente, responsable, estado y observaciones.

## 4. Registro de evidencias

### 4.1 Gestión del Proyecto

| Código | Evidencia | Fuente | Responsable | Estado | Observaciones |
|---|---|---|---|---|---|
| EV-001 | Project Charter del proyecto Techmovil | Documentación del Proyecto | Líder del Proyecto | Conforme | Documento disponible y aprobado. |
| EV-002 | Product Backlog | Jira | Scrum Master | Conforme | Contiene las historias de usuario del proyecto. |
| EV-003 | Sprint Backlog | Jira | Scrum Master | Conforme | Se evidencia la planificación por sprints. |
| EV-004 | Cronograma del Proyecto | Documentación | Líder del Proyecto | Conforme | Cronograma actualizado. |
| EV-005 | Registro de reuniones Scrum | Actas de reunión | Scrum Master | Conforme | Evidencia de reuniones periódicas. |

### 4.2 Requisitos

| Código | Evidencia | Fuente | Responsable | Estado | Observaciones |
|---|---|---|---|---|---|
| EV-006 | Documento de Requisitos Funcionales | Documentación | Analista | Conforme | Define funcionalidades principales del sistema. |
| EV-007 | Documento de Requisitos No Funcionales | Documentación | Analista | Conforme | Incluye requisitos de rendimiento y seguridad. |
| EV-008 | Historias de Usuario | Jira | Product Owner | Conforme | Requisitos gestionados mediante Scrum. |
| EV-009 | Casos de Uso | Documentación UML | Analista | Conforme | Casos de uso correctamente documentados. |

### 4.3 Diseño

| Código | Evidencia | Fuente | Responsable | Estado | Observaciones |
|---|---|---|---|---|---|
| EV-010 | Diagrama de Contexto C4 | Documentación | Arquitecto | Conforme | Arquitectura correctamente definida. |
| EV-011 | Diagrama de Contenedores C4 | Documentación | Arquitecto | Conforme | Presenta la estructura del sistema. |
| EV-012 | Diagramas UML | PlantUML | Arquitecto | Conforme | Diagramas consistentes con la implementación. |
| EV-013 | Modelo de Base de Datos MySQL | MySQL Workbench | Arquitecto | Conforme | Modelo lógico disponible. |

!!! warning "Diagramas C4/UML no incluidos como imagen en este sitio"
    EV-010, EV-011 y EV-012 se verificaron contra la documentación/repositorio propio de Techmovil, pero no llegaron como archivos de imagen entre los documentos entregados para construir este MkDocs. Si el equipo dispone de los `.png`/`.svg` de los diagramas C4 y UML, deben añadirse a `docs/assets/img/arquitectura/` y enlazarse aquí y en [Evidencia de Desarrollo](evidencia-desarrollo.md) para completar el sustento visual de esta fase.

### 4.4 Desarrollo

| Código | Evidencia | Fuente | Responsable | Estado | Observaciones |
|---|---|---|---|---|---|
| EV-014 | Repositorio GitHub | GitHub | Equipo de Desarrollo | Conforme | Control de versiones implementado. |
| EV-015 | Historial de Commits | GitHub | Equipo de Desarrollo | Conforme | Se evidencia desarrollo colaborativo. |
| EV-016 | Pull Requests | GitHub | Equipo de Desarrollo | Conforme | Revisiones de código registradas. |
| EV-017 | Proyecto Backend Spring Boot | Código Fuente | Backend Developer | Conforme | Código organizado por módulos. |
| EV-018 | Proyecto Frontend Angular | WebStorm | Frontend Developer | Conforme | Arquitectura modular implementada. |
| EV-019 | Proyecto Flutter | Código Fuente | Mobile Developer | Conforme | Aplicación móvil funcional. |

### 4.5 Calidad y Pruebas

| Código | Evidencia | Fuente | Responsable | Estado | Observaciones |
|---|---|---|---|---|---|
| EV-020 | Casos de Prueba | Documentación | QA | Conforme | Casos documentados. |
| EV-021 | Resultados de Pruebas | Evidencias | QA | Conforme | Resultados satisfactorios. |
| EV-022 | Reporte SonarCloud | SonarCloud | QA | Conforme | Calidad del código verificada. |
| EV-023 | Registro de Incidencias | Jira | QA | Conforme | Defectos registrados y atendidos. |

Ver capturas reales de SonarQube/SonarCloud en [Evidencia de Desarrollo → SonarQube y cobertura](evidencia-desarrollo.md#sonarqube-y-cobertura-de-codigo).

### 4.6 Seguridad

| Código | Evidencia | Fuente | Responsable | Estado | Observaciones |
|---|---|---|---|---|---|
| EV-024 | Configuración Spring Security | Backend | Backend Developer | Conforme | Seguridad implementada. |
| EV-025 | Autenticación JWT | Backend | Backend Developer | Conforme | Tokens correctamente configurados. |
| EV-026 | Gestión de Roles y Permisos | Código Fuente | Backend Developer | Conforme | Control de acceso basado en roles. |
| EV-027 | Configuración HTTPS | Configuración del Servidor | Administrador | Conforme | Comunicación protegida. |

Ver capturas de las pruebas de penetración (Nmap, OWASP ZAP, XSS, fuerza bruta) en [Evidencia de Desarrollo → Pruebas de seguridad](evidencia-desarrollo.md#pruebas-de-seguridad).

### 4.7 Base de Datos

| Código | Evidencia | Fuente | Responsable | Estado | Observaciones |
|---|---|---|---|---|---|
| EV-028 | Esquema MySQL | MySQL | DBA | Conforme | Estructura correctamente definida. |
| EV-029 | Scripts SQL | Repositorio GitHub | DBA | Conforme | Scripts versionados. |
| EV-030 | Procedimientos de Respaldo | Documentación | DBA | Conforme | Procedimientos documentados. |

### 4.8 Documentación

| Código | Evidencia | Fuente | Responsable | Estado | Observaciones |
|---|---|---|---|---|---|
| EV-031 | Informe Final del Proyecto | Documentación | Equipo de Desarrollo | Conforme | Documento completo. |
| EV-032 | Manual Técnico | Documentación | Equipo Técnico | Conforme | Disponible. |
| EV-033 | Manual de Usuario | Documentación | Equipo Técnico | Conforme | Elaborado para usuarios finales. |
| EV-034 | Diagramas PlantUML | Repositorio | Arquitecto | Conforme | Diagramas actualizados. |

## 5. Resumen general de evidencias

| Área Auditada | Evidencias Registradas |
|---|---|
| Gestión del Proyecto | 5 |
| Requisitos | 4 |
| Diseño | 4 |
| Desarrollo | 6 |
| Calidad y Pruebas | 4 |
| Seguridad | 4 |
| Base de Datos | 3 |
| Documentación | 4 |
| **Total** | **34 evidencias** |

## 6. Observaciones generales

Durante la revisión se verificó la existencia de documentación técnica, repositorios de código, herramientas de gestión del proyecto y registros de calidad que permiten sustentar el desarrollo del sistema Techmovil. Las evidencias recopiladas sirven como soporte para los [Papeles de Trabajo de Auditoría](papeles-trabajo.md) y para la elaboración del [Informe Final de Auditoría SDLC](../fase3/informe-final.md).

## 7. Aprobación

| Cargo | Nombre |
|---|---|
| Auditor Líder | Idonis Mijael Paye Trujillo |
| Auditor Documental | Kevin Edwin Sucapuca Calcinay |

Código del Documento: REV-SDLC-TECHMOVIL-001 · Versión: 1.0 · Estado: Aprobado
