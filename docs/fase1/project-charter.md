# Project Charter de Auditoría SDLC

!!! info "Ficha del documento"
    **Código:** AUD-SDLC-ASTRA-LOGIII-2026-001 (el pie del documento consigna además el código AUD-SDLC-CODE-SISTEM-SOLUTION-2026-001)
    **Versión:** 1.0
    **Estado:** Documento de uso interno — clasificación Confidencial

    [:material-file-pdf-box: Descargar documento original (PDF)](../assets/entregables/fase1/project-charter-auditoria-sdlc.pdf)

## Portada

- **Universidad Peruana Unión** — Ingeniería de Sistemas — Ciclo VII G1
- **Título:** Project Charter de Auditoría del Ciclo de Vida del Desarrollo de Software (SDLC)
- **Profesor responsable:** Ing. Ruben Roque Sucari
- **Nombre del equipo:** Techmovil
- **Nombre del producto:** Techmovil – Solución Logística Centralizada
- **Grupo:** 5
- **Integrantes:**
    - Yohan Layder Escarcena Pancca — 202312728
    - Idonis Mijael Paye Trujillo — 202312049
    - Kevin Edwin Sucapuca Calcinay — 202312058
    -  — 202310096
- **Lugar y fecha:** Juliaca, junio 2026

## 1. Información General del Proyecto

| Campo | Detalle |
|---|---|
| Nombre del Proyecto | Techmovil – Solución Logística Centralizada |
| Código del Proyecto | AUD-SDLC-ASTRA-LOGIII-2026-001 |
| Versión del documento | 1.0 |
| Fecha de elaboración | 23 de junio de 2026 |
| Fecha de inicio estimada | 23 de junio de 2026 |
| Fecha de cierre estimada | 09 de septiembre de 2026 |
| Patrocinador | Universidad Peruana Unión - Facultad de Ingeniería y Arquitectura - Gerencia de Techmovil |
| Auditor líder | Yohan Layder Escarcena Pancca |
| Equipo auditor | Idonis Mijael Paye Trujillo, Kevin Edwin Sucapuca Calcinay,  |
| Clasificación | Proyecto de Desarrollo e Implementación de Sistema de Información Logístico |
| Sistema auditado | Techmovil – Solución Logística Centralizada |
| Organización auditada | Techmovil |

## 2. Antecedentes

### 2.1 Descripción del Sistema Techmovil

Techmovil es un Sistema Centralizado de Gestión Logística y Comercial desarrollado por equipo de desarrollo, conformado por estudiantes del VII ciclo de la carrera de Ingeniería de Sistemas de la Universidad Peruana Unión, en el contexto del curso de Ingeniería de Software II, bajo la supervisión del Ing. Ruben Roque Sucari.

El sistema ha sido concebido para dar solución a las necesidades operativas de la empresa Techmovil, dedicada al transporte y distribución de materiales de construcción, la cual presenta dificultades en la gestión de ventas, asignación de transportistas y control logístico debido a procesos manuales y poco estandarizados. Estas limitaciones generan problemas como tarifas ambiguas, distribución desigual de servicios, falta de trazabilidad en las entregas y escaso control sobre las operaciones diarias.

Techmovil propone centralizar la gestión de ventas, la asignación equitativa de servicios de transporte y el seguimiento de entregas mediante una plataforma tecnológica integrada. El sistema permite registrar pedidos, calcular tarifas dinámicamente según criterios de negocio, asignar automáticamente los servicios a los transportistas disponibles, enviar hojas de ruta digitales mediante una aplicación móvil y validar las entregas a través de mecanismos de control y evidencia digital. Asimismo, la solución incorpora funcionalidades de monitoreo, auditoría y generación de reportes que permiten disponer de información confiable en tiempo real para apoyar la toma de decisiones y mejorar la eficiencia operativa de Techmovil.

El sistema ha sido diseñado bajo principios de calidad de software, mantenibilidad y seguridad, contribuyendo a la transformación digital de los procesos logísticos de la organización.

### 2.2 Arquitectura y Tecnología

El sistema Techmovil adopta una arquitectura monolítica modular, documentada mediante el modelo C4 (Contexto, Contenedores y Componentes), permitiendo representar de forma estructurada los elementos que conforman la solución tecnológica. Esta arquitectura centraliza la lógica de negocio en una única aplicación, organizada en módulos funcionales independientes que favorecen la mantenibilidad, escalabilidad y control del sistema.

Los principales componentes de la arquitectura son:

- **Aplicación Web (Frontend):** utilizada por administradores logísticos y gerencia para la gestión de pedidos, monitoreo de operaciones, administración de usuarios y consulta de reportes.
- **Aplicación Móvil:** utilizada por los transportistas para recibir hojas de ruta, actualizar estados de entrega y registrar evidencias de cumplimiento del servicio.
- **Backend Monolítico (Java Spring Boot):** núcleo de procesamiento de las reglas de negocio y responsable de la gestión centralizada de las operaciones del sistema.
- **Módulo de Autenticación:** encargado de la validación de credenciales, generación de tokens JWT y control de sesiones de usuario.
- **Módulo de Usuarios:** gestión de perfiles, roles y permisos de acceso al sistema.
- **Módulo de Ventas y Pedidos:** registro de pedidos, cálculo dinámico de tarifas y asignación equitativa de servicios a transportistas.
- **Módulo de Inventario:** control de materiales de construcción, validación de capacidad de carga y restricciones operativas.
- **Módulo de Auditoría:** registro de acciones realizadas en el sistema, trazabilidad de cambios y almacenamiento de evidencias para fines de control y seguimiento.
- **Base de Datos Centralizada:** almacenamiento seguro de la información del sistema mediante MySQL, garantizando la integridad y consistencia de los datos.

**Tecnologías utilizadas:**

- Backend: Java Spring Boot.
- Frontend Web: Angular (desarrollado mediante WebStorm).
- Aplicación Móvil: Flutter.
- Base de Datos: MySQL.
- Seguridad: JSON Web Token (JWT).
- Documentación de API: Swagger / OpenAPI.
- Control de Versiones: Git y GitHub.
- Calidad de Software: SonarCloud.
- Pruebas de Rendimiento: K6.
- Gestión y Seguimiento del Proyecto: Jira bajo prácticas Scrum y CMMI.

### 2.3 Contexto de la Auditoría

La presente auditoría tiene como finalidad evaluar el cumplimiento de los procesos, prácticas y controles aplicados durante el desarrollo del sistema Techmovil para Techmovil. La evaluación se realizará considerando buenas prácticas de Ingeniería de Software y estándares como ISO/IEC 12207, ISO/IEC 25010 y CMMI-DEV, con el propósito de verificar la calidad, trazabilidad y conformidad del ciclo de vida del software.

## 3. Justificación

### 3.1 Necesidad de la Auditoría SDLC

La auditoría SDLC es necesaria para verificar que el desarrollo del sistema Techmovil haya cumplido con los procesos, controles y buenas prácticas establecidos durante el ciclo de vida del software. Esta evaluación permitirá validar la calidad del producto, la correcta implementación de los requisitos, la trazabilidad de los cambios y la aplicación de mecanismos de seguridad y auditoría. Asimismo, la auditoría permitirá comprobar la adecuada gestión del proyecto, la documentación generada y el cumplimiento de estándares de calidad, contribuyendo a garantizar la confiabilidad y mantenibilidad del sistema implementado para Techmovil.

En el caso específico de Techmovil, la auditoría es necesaria por las siguientes razones:

- El sistema gestiona información crítica relacionada con pedidos, transportistas, inventario y operaciones logísticas de Techmovil.
- El sistema incorpora mecanismos de autenticación, control de acceso y auditoría que requieren verificación para garantizar la seguridad y trazabilidad de la información.
- El proyecto fue desarrollado en un contexto académico aplicando prácticas de Ingeniería de Software, Scrum y CMMI, por lo que es necesario evaluar el cumplimiento de los procesos establecidos.
- Se requiere validar que las actividades de análisis, diseño, desarrollo, pruebas y documentación hayan sido ejecutadas adecuadamente durante el ciclo de vida del software.
- Es necesario identificar oportunidades de mejora y posibles riesgos que puedan afectar la calidad, mantenibilidad y confiabilidad del sistema.

### 3.2 Beneficios de la Auditoría

- Verificación del cumplimiento de los requisitos funcionales y no funcionales definidos para el sistema Techmovil.
- Evaluación de la correcta aplicación de estándares, metodologías y buenas prácticas de Ingeniería de Software durante el desarrollo del proyecto.
- Identificación temprana de riesgos, vulnerabilidades y oportunidades de mejora que puedan afectar la calidad y confiabilidad del sistema.
- Validación de los mecanismos de seguridad, autenticación, control de acceso y trazabilidad implementados en la solución.
- Generación de recomendaciones y acciones de mejora para fortalecer la mantenibilidad, escalabilidad y rendimiento del sistema.
- Provisión de evidencia objetiva sobre la calidad del software para los stakeholders y responsables del proyecto.
- Establecimiento de una línea base de calidad que sirva como referencia para futuras mejoras y versiones de Techmovil.

### 3.3 Riesgos que se Pretenden Mitigar

- Riesgo de incumplimiento de los requisitos funcionales y no funcionales definidos para el sistema Techmovil.
- Vulnerabilidades de seguridad relacionadas con la autenticación de usuarios, gestión de sesiones y control de acceso mediante JWT.
- Inconsistencias entre la arquitectura documentada y la implementación real del sistema.
- Deficiencias en la calidad del software que puedan afectar la confiabilidad, mantenibilidad y rendimiento de la solución.
- Errores en los procesos de gestión de pedidos, asignación de transportistas y control de inventario.
- Falta de trazabilidad entre los requisitos, el diseño, la implementación y las pruebas realizadas durante el desarrollo.
- Ausencia o insuficiencia de evidencia documental que respalde las actividades ejecutadas durante el ciclo de vida del software.

## 4. Problema u Oportunidad

### 4.1 Situación Identificada

El análisis preliminar de la documentación del proyecto Techmovil permite identificar las siguientes situaciones que justifican la realización de la auditoría:

- Necesidad de verificar que los requisitos funcionales y no funcionales definidos para el sistema hayan sido implementados correctamente.
- Necesidad de evaluar la aplicación de las prácticas de gestión del proyecto, desarrollo y aseguramiento de la calidad utilizadas durante el ciclo de vida del software.
- Requerimiento de validar la consistencia entre la arquitectura documentada, los componentes implementados y el comportamiento real del sistema.
- Necesidad de revisar la evidencia de pruebas, control de versiones, revisión de código y gestión de cambios generada durante el desarrollo.
- Requerimiento de comprobar la correcta implementación de los mecanismos de seguridad, autenticación, autorización y auditoría incorporados en la solución.
- Necesidad de identificar oportunidades de mejora que permitan fortalecer la calidad, mantenibilidad, rendimiento y confiabilidad del sistema Techmovil.

### 4.2 Oportunidad

La realización de esta auditoría representa una oportunidad para validar la calidad del sistema Techmovil, verificar el cumplimiento de las buenas prácticas de Ingeniería de Software aplicadas durante su desarrollo y fortalecer los procesos de gestión, control y aseguramiento de la calidad. Asimismo, permitirá identificar oportunidades de mejora que contribuyan a incrementar la confiabilidad, mantenibilidad y seguridad del sistema antes de futuras versiones o despliegues operativos.

## 5. Objetivo General

Realizar una auditoría formal del Ciclo de Vida del Desarrollo de Software (SDLC) del proyecto Techmovil, mediante la evaluación sistemática de sus procesos, controles, documentación, arquitectura y entregables, con el fin de determinar el grado de cumplimiento de los estándares ISO/IEC 12207, ISO/IEC 25010, CMMI-DEV y las buenas prácticas de Ingeniería de Software, identificar riesgos y oportunidades de mejora, y emitir recomendaciones que contribuyan a garantizar la calidad, seguridad, mantenibilidad y confiabilidad del sistema implementado para Techmovil.

## 6. Objetivos Específicos

- **OE1 – Metodología y Gestión del Proyecto:** Evaluar si el proyecto Techmovil fue planificado, ejecutado y controlado adecuadamente, verificando la aplicación de prácticas Scrum, CMMI, gestión de cambios, seguimiento de actividades y documentación del proyecto.
- **OE2 – Análisis de Requisitos:** Verificar que el proceso de levantamiento, documentación y validación de los requisitos funcionales y no funcionales del sistema Techmovil fue realizado de manera adecuada y cuenta con trazabilidad.
- **OE3 – Diseño del Sistema:** Evaluar si la arquitectura monolítica modular, el diseño funcional y el diseño técnico del sistema son coherentes con los requisitos definidos e incorporan mecanismos adecuados de seguridad, auditoría y rendimiento.
- **OE4 – Construcción y Codificación:** Verificar que el desarrollo del sistema siguió estándares de programación, buenas prácticas de codificación y mecanismos de control de versiones, asegurando la calidad y mantenibilidad del software.
- **OE5 – Pruebas:** Determinar si el sistema fue sometido a pruebas funcionales, de integración, rendimiento y validación, verificando que los resultados fueron documentados y analizados adecuadamente.
- **OE6 – Implementación y Despliegue:** Evaluar si los procedimientos de implementación, configuración y despliegue del sistema fueron documentados y ejecutados de forma controlada.
- **OE7 – Mantenimiento y Soporte:** Verificar la existencia de procedimientos para la gestión de incidencias, control de versiones, corrección de errores y futuras actualizaciones del sistema.
- **OE8 – Documentación:** Evaluar si la documentación técnica, funcional, arquitectónica y de usuario es suficiente, coherente y accesible para los interesados del proyecto.
- **OE9 – Seguridad:** Verificar la efectividad de los mecanismos de autenticación, autorización, gestión de roles, protección de datos y trazabilidad implementados en Techmovil.
- **OE10 – Calidad del Producto:** Evaluar las características de calidad del sistema conforme al modelo ISO/IEC 25010, considerando funcionalidad, fiabilidad, usabilidad, eficiencia, mantenibilidad y seguridad.

## 7. Alcance de la Auditoría

La auditoría SDLC del proyecto Techmovil abarcará las siguientes áreas y aspectos:

### 7.1 Gestión del Proyecto

- Definición y aprobación de los objetivos, alcance y entregables del proyecto.
- Conformación del equipo de desarrollo, asignación de roles y responsabilidades.
- Planificación y seguimiento de actividades mediante herramientas de gestión de proyectos.
- Aplicación de prácticas Scrum y CMMI para la organización, monitoreo y control del desarrollo.
- Gestión de cambios, incidencias y riesgos durante el ciclo de vida del software.
- Evidencia de reuniones, seguimiento de tareas y cumplimiento de hitos del proyecto.
- Participación de los stakeholders en la validación de requisitos y entregables.
- Documentación generada para el cierre y evaluación final del proyecto.

### 7.2 Requisitos

- Procedimientos utilizados para el levantamiento, análisis y documentación de requisitos.
- Participación de los stakeholders de Techmovil en la identificación y validación de necesidades del negocio.
- Existencia y documentación de los requisitos funcionales y no funcionales del sistema.
- Clasificación, priorización y validación de los requisitos definidos para el proyecto.
- Trazabilidad de los requisitos hacia el diseño, desarrollo y pruebas del sistema.
- Evidencia de aprobación y validación de los requisitos por parte de los interesados.
- Procedimientos aplicados para la gestión de cambios y actualización de requisitos durante el desarrollo del proyecto.

### 7.3 Diseño

- Existencia y calidad de la especificación funcional del sistema.
- Coherencia entre los requisitos definidos y la arquitectura monolítica modular documentada mediante el modelo C4.
- Diseño del modelo de datos y su correspondencia con los procesos de negocio de Techmovil.
- Especificación de los componentes, módulos e interfaces del sistema.
- Incorporación de requisitos de seguridad, rendimiento y auditoría dentro del diseño de la solución.
- Definición de roles de usuario, permisos y flujos de interacción entre los diferentes actores del sistema.
- Consistencia entre los diagramas de diseño, la arquitectura documentada y la implementación realizada.
- Evidencia de revisión y aprobación de los documentos de diseño generados durante el proyecto.

### 7.4 Desarrollo

- Estándares de programación aplicados durante el desarrollo de los módulos del sistema.
- Preparación y configuración de los entornos de desarrollo, pruebas y despliegue.
- Aplicación de buenas prácticas de Ingeniería de Software y principios de diseño orientados a la mantenibilidad y calidad del código.
- Uso de herramientas de control de versiones y gestión de configuración del código fuente.
- Procedimientos de revisión de código y análisis estático de calidad mediante herramientas especializadas.
- Documentación técnica generada durante la fase de construcción del sistema.
- Implementación de mecanismos de manejo de errores, excepciones y registro de eventos (logs).
- Consistencia entre el código fuente desarrollado y los requisitos definidos para el sistema.

### 7.5 Pruebas

- Existencia de un plan de pruebas y procedimientos de validación documentados.
- Tipos de pruebas realizadas: unitarias, integración, sistema, aceptación y rendimiento.
- Datos de prueba utilizados y evidencia de los resultados obtenidos.
- Pruebas de integración entre los diferentes módulos, servicios y componentes del sistema.
- Pruebas de seguridad relacionadas con la autenticación, autorización y control de acceso mediante JWT.
- Participación de usuarios y stakeholders en las pruebas de validación y aceptación del sistema.
- Gestión, seguimiento y corrección de defectos identificados durante las pruebas.
- Evidencia de pruebas de rendimiento y carga realizadas mediante herramientas especializadas.

### 7.6 Implementación

- Existencia de un plan de implementación y despliegue del sistema.
- Procedimientos de instalación, configuración y puesta en marcha de los componentes de la solución.
- Estrategias utilizadas para la creación, configuración e inicialización de la base de datos MySQL.
- Capacitación y orientación proporcionada a los usuarios para la utilización del sistema.
- Evidencia de validación y aceptación del sistema por parte de los stakeholders de Techmovil.
- Procedimientos de respaldo, recuperación y protección de la información almacenada.
- Verificación de la correcta configuración de los entornos de producción y pruebas.
- Documentación de incidencias y acciones correctivas realizadas durante la implementación.

### 7.7 Mantenimiento

- Existencia de procedimientos para la gestión y seguimiento de incidencias posteriores a la implementación.
- Definición de mecanismos para el mantenimiento correctivo, preventivo y evolutivo del sistema.
- Control de versiones y gestión de actualizaciones del software mediante herramientas de control de código fuente.
- Documentación de las modificaciones, correcciones y mejoras realizadas al sistema.
- Registro y seguimiento de solicitudes de cambio e incidencias reportadas por los usuarios.
- Disponibilidad de procedimientos para la actualización, monitoreo y soporte continuo del sistema.

### 7.8 Documentación

- Completitud y calidad de la documentación técnica, funcional y arquitectónica del sistema.
- Existencia de manuales de usuario para los diferentes perfiles del sistema (administrador, gerencia y transportista).
- Documentación de los procedimientos de instalación, configuración, operación y mantenimiento del sistema.
- Aplicación de estándares y buenas prácticas para la elaboración de la documentación del proyecto.
- Control de versiones, actualización y accesibilidad de la documentación técnica y de usuario.
- Consistencia entre la documentación generada y la implementación real del sistema.

### 7.9 Seguridad

- Mecanismos de autenticación, gestión de credenciales y generación de tokens JWT.
- Control de acceso basado en roles y permisos para los diferentes perfiles de usuario.
- Protección y confidencialidad de la información relacionada con pedidos, transportistas, inventario y operaciones logísticas.
- Registro y trazabilidad de las acciones realizadas por los usuarios mediante mecanismos de auditoría.
- Seguridad en la comunicación entre el frontend, la aplicación móvil y el backend del sistema.
- Procedimientos de respaldo, recuperación y protección de la base de datos MySQL.
- Gestión de vulnerabilidades y revisión de la calidad del código mediante herramientas de análisis estático.

### 7.10 Calidad

- Evaluación de la funcionalidad del sistema conforme a los requisitos funcionales y no funcionales definidos.
- Fiabilidad: manejo de errores, disponibilidad y estabilidad del sistema.
- Usabilidad: facilidad de uso de las interfaces web y móvil para los diferentes perfiles de usuario.
- Eficiencia: rendimiento, tiempos de respuesta y consumo de recursos del sistema.
- Mantenibilidad: modularidad, calidad del código, facilidad de mantenimiento y evolución del sistema.
- Seguridad: protección de la información, autenticación, autorización y control de acceso.
- Portabilidad: capacidad del sistema para ser desplegado y ejecutado en diferentes entornos de operación.

## 8. Exclusiones

La presente auditoría NO abarcará los siguientes aspectos:

- La evaluación detallada del código fuente a nivel de línea de programación.
- La infraestructura física de hardware, redes y equipos utilizados por Techmovil.
- Los procesos operativos y administrativos de Techmovil que no estén directamente relacionados con el sistema Techmovil.
- La auditoría financiera, contable o administrativa del proyecto de desarrollo.
- La evaluación de proveedores externos de software, servicios tecnológicos o infraestructura de terceros.
- Las actividades de mantenimiento, soporte y operación del sistema posteriores a la finalización de la auditoría y del plan de mejora propuesto.

## 9. Criterios de Auditoría

La auditoría se basará en los siguientes marcos normativos y de referencia:

- **ISO/IEC 12207:2017 – Ciclo de Vida del Software:** Estándar internacional que establece los procesos para la gestión, desarrollo, operación y mantenimiento del software. Será utilizado para evaluar el cumplimiento de las actividades realizadas durante el ciclo de vida del sistema Techmovil.
- **ISO/IEC 25010 – Calidad del Producto de Software:** Modelo de calidad que define las características de adecuación funcional, eficiencia del desempeño, compatibilidad, usabilidad, fiabilidad, seguridad, mantenibilidad y portabilidad, las cuales servirán como referencia para evaluar la calidad del sistema Techmovil.
- **CMMI-DEV:** Modelo de madurez para el desarrollo de software, utilizado para evaluar la aplicación de buenas prácticas en la gestión de requisitos, planificación del proyecto, aseguramiento de la calidad, gestión de la configuración y mejora de los procesos implementados durante el desarrollo de Techmovil.
- **Checklist SDLC:** Lista de verificación basada en las fases del Ciclo de Vida del Desarrollo de Software (SDLC), que comprende la evaluación de la gestión del proyecto, requisitos, diseño, desarrollo, pruebas, implementación, mantenimiento, documentación, seguridad y calidad del producto.

## 10. Metodología de Auditoría

La auditoría SDLC del proyecto Techmovil – Solución Logística Centralizada se ejecutará siguiendo el procedimiento general de auditoría del Ciclo de Vida del Desarrollo de Software (SDLC), estructurado en cuatro fases:

### Fase 1: Preparar y Planificar (Prepare and Plan)

**Duración estimada:** 2 días

**Actividades:**

- Definición de los criterios de auditoría con base en ISO/IEC 12207, ISO/IEC 25010, CMMI-DEV y el Checklist SDLC establecido para el proyecto Techmovil.
- Definición de los objetivos de la auditoría alineados con el presente Project Charter y las necesidades de Techmovil.
- Definición del alcance de la auditoría, estableciendo la extensión, límites y exclusiones conforme a la sección 7 del Project Charter.
- Identificación del modelo SDLC utilizado durante el desarrollo del sistema Techmovil.
- Identificación de la arquitectura y del stack tecnológico implementado: arquitectura monolítica modular, backend Java Spring Boot, frontend Angular, aplicación móvil Flutter, base de datos MySQL, autenticación mediante JWT, documentación API con Swagger / OpenAPI.
- Identificación de los estándares, metodologías y herramientas aplicadas durante el desarrollo: modelo C4, Scrum, CMMI, SonarCloud para análisis de calidad, K6 para pruebas de rendimiento.
- Identificación de los roles y responsabilidades del equipo de desarrollo y de los representantes de Techmovil involucrados en el proyecto.
- Elaboración del Plan de Auditoría detallado.
- Elaboración del Checklist SDLC adaptado al contexto del sistema Techmovil.
- Comunicación formal del inicio de la auditoría al patrocinador y al equipo auditado.

**Entregables:** Plan de Auditoría, Checklist SDLC adaptado a Techmovil, Comunicación formal de inicio de la auditoría.

### Fase 2: Describir el Proceso de Desarrollo en Detalle (Describe the Development Process)

**Duración estimada:** 3 días

**Actividades:**

- Entrevistas al equipo de desarrollo  para comprender cómo se ejecutó cada fase del Ciclo de Vida del Desarrollo de Software (SDLC), la aplicación de Scrum y las prácticas de calidad implementadas durante el proyecto.
- Revisión de la documentación disponible: Project Charter, informe final del proyecto, documentación de requisitos, diagramas C4, diagramas UML/PlantUML, manuales técnicos y demás artefactos generados durante el desarrollo.
- Revisión de las herramientas utilizadas durante el proyecto: Jira para la gestión de actividades, Git y GitHub para el control de versiones, SonarCloud para el análisis de calidad del código, Swagger / OpenAPI para la documentación de servicios y K6 para las pruebas de rendimiento.
- Revisión de la arquitectura del sistema para validar la coherencia, integridad y correcta implementación de la arquitectura monolítica modular, verificando la interacción entre el frontend web, la aplicación móvil, el backend y la base de datos.
- Solicitud y verificación de evidencias adicionales conforme al Checklist SDLC, incluyendo registros de pruebas, repositorios, documentación técnica y evidencias de validación.
- Documentación del estado actual de cada proceso del SDLC, identificando fortalezas, debilidades y oportunidades de mejora durante el desarrollo del sistema Techmovil.

**Temas para describir y evaluar:** SDLC model, System definition, System requirements, System architecture, Software requirements, Software architecture, Coding, Unit testing, Code review, Coding guidelines, Documentation, Deployment & Integration, Defect management, Project management, Configuration management, Quality Assurance Plan.

**Entregables:** Registro de entrevistas, Registro de evidencias, Papeles de trabajo de auditoría.

### Fase 3: Evaluar y Reportar (Evaluate and Report)

**Duración estimada:** 1 día

**Actividades:**

- Evaluación de la evidencia recopilada frente a los criterios de auditoría establecidos (ISO/IEC 12207, ISO/IEC 25010, CMMI-DEV y Checklist SDLC), determinando el grado de cumplimiento de los objetivos de control definidos para el sistema Techmovil.
- Identificación de los hallazgos de auditoría, comparando las evidencias obtenidas con los criterios establecidos para determinar conformidades, no conformidades, observaciones y oportunidades de mejora en el desarrollo del sistema.
- Clasificación de los hallazgos según su nivel de criticidad e impacto para Techmovil, diferenciando entre hallazgos menores, significativos y críticos.
- Elaboración de la Matriz de Hallazgos, documentando para cada hallazgo la descripción, criterio de referencia, evidencia objetiva, causa, impacto, nivel de riesgo y recomendación correspondiente.
- Elaboración de la Matriz de Riesgos, identificando y evaluando los riesgos derivados de las brechas detectadas durante la auditoría, considerando aspectos relacionados con la calidad, seguridad, mantenibilidad y operación del sistema Techmovil.
- Elaboración del Informe Preliminar de Auditoría, consolidando los resultados obtenidos, los hallazgos identificados y las recomendaciones iniciales.
- Revisión del Informe Preliminar con el equipo de desarrollo y los representantes de Techmovil, recopilando observaciones, comentarios y aclaraciones antes de emitir el informe definitivo.
- Elaboración del Informe Final de Auditoría, incorporando las observaciones recibidas, las conclusiones finales y las recomendaciones orientadas al fortalecimiento de los procesos de desarrollo, la calidad del software y la mejora continua del sistema Techmovil.

**Entregables:** Matriz de Hallazgos, Matriz de Riesgos, Informe Preliminar de Auditoría, Informe Final de Auditoría.

### Fase 4: Seguimiento (Follow-up)

**Duración estimada:** 1 día

**Actividades:**

- Presentación del Informe Final de Auditoría al patrocinador, a los representantes de Techmovil y al equipo de desarrollo, exponiendo los hallazgos, conclusiones y recomendaciones obtenidas durante la auditoría.
- Elaboración del Plan de Acción Correctiva por parte del equipo auditado, definiendo las acciones necesarias para atender las no conformidades y oportunidades de mejora identificadas en el sistema Techmovil.
- Organización de reuniones o talleres de mejora, cuando sea necesario, para explicar los hallazgos, fortalecer las buenas prácticas de desarrollo y orientar la implementación de las acciones correctivas.
- Seguimiento a la implementación del Plan de Acción Correctiva, verificando el cumplimiento de las actividades, responsables y plazos establecidos para la mejora del sistema Techmovil.
- Verificación del cierre de los hallazgos mediante la revisión de evidencias que demuestren la implementación efectiva de las acciones correctivas y la mitigación de los riesgos identificados.
- Elaboración del Acta de Cierre de la Auditoría, dejando constancia del cumplimiento de los objetivos de la auditoría, los resultados obtenidos y el cierre formal del proceso.
- Organización y archivo de los papeles de trabajo, evidencias, informes y demás documentación generada durante la auditoría, garantizando su trazabilidad, disponibilidad y confidencialidad para futuras consultas o auditorías.

**Entregables:** Plan de Acción Correctiva, Acta de Cierre de Auditoría, Archivo de papeles de trabajo.

## 11. Entregables de la Auditoría

| N.° | Entregable | Descripción | Fase |
|---|---|---|---|
| 1 | Project Charter de Auditoría | Documento de inicio formal del proyecto de auditoría | Planificación |
| 2 | Plan de Auditoría | Objetivos, alcance, cronograma, recursos y procedimientos detallados | Fase 1 |
| 3 | Checklist SDLC Adaptado | Lista de verificación adaptada al contexto del sistema auditado | Fase 1 |
| 4 | Registro de Evidencias | Compilación sistemática de todas las evidencias recopiladas | Fase 2 |
| 5 | Papeles de Trabajo | Documentación de los procedimientos aplicados y sus resultados | Fases 2 y 3 |
| 6 | Matriz de Hallazgos | Hallazgos con descripción, criterio, evidencia, impacto y recomendación | Fase 3 |
| 7 | Matriz de Riesgos | Identificación y valoración de riesgos derivados de los hallazgos | Fase 3 |
| 8 | Informe Preliminar de Auditoría | Versión borrador para revisión y comentarios del auditado | Fase 3 |
| 9 | Informe Final de Auditoría | Informe completo con conclusiones y recomendaciones | Fase 3 |
| 10 | Plan de Acción Correctiva | Acciones, responsables y plazos para atender los hallazgos | Fase 4 |
| 11 | Acta de Cierre de Auditoría | Documento formal de conclusión del proyecto de auditoría | Fase 4 |

*Nota: la fuente original de esta tabla identifica al entregable N.° 3 como "Checklist SDLC Adaptado" y su descripción menciona el contexto de "SYS PROFAR", nombre de un proyecto de plantilla del curso; en el contexto de este documento (Techmovil) se entiende referido al Checklist SDLC Adaptado a Techmovil.*

## 12. Stakeholders

### 12.1 Stakeholders del Proyecto de Auditoría

| Stakeholder | Rol en la Auditoría | Interés / Expectativa |
|---|---|---|
| Auditor líder — Idonis Mijael Paye Trujillo | Dirección y coordinación | Emitir un informe objetivo, completo y fundamentado |
| Equipo Auditor — Kevin Edwin Sucapuca Calcinay; Yohan Layder Escarcena Pancca | Ejecución de procedimientos | Recopilar evidencia suficiente y apropiada |
| Patrocinador (UPU) | Autoriza y financia | Garantizar la calidad académica y profesional del sistema |
| Ing. Ruben Roque Sucari | Profesor / Supervisor del proyecto auditado | Verificar el cumplimiento de los estándares de ingeniería de software |
| Grupo equipo de desarrollo | Auditado | Recibir retroalimentación constructiva y mejorar sus prácticas |

### 12.2 Stakeholders del Sistema Techmovil (Usuarios Finales)

| Stakeholder | Descripción | Interacción con el Sistema |
|---|---|---|
| Administrador | Administra el sistema y gestiona usuarios, roles y configuraciones. | Aplicación web (PC) y aplicación móvil. |
| Personal de Almacén | Gestiona el inventario y el control de existencias de productos. | Aplicación web (PC). |
| Coordinador Logístico | Planifica y supervisa los pedidos y las operaciones logísticas. | Aplicación web (PC). |
| Transportista | Realiza la distribución y entrega de los pedidos asignados. | Aplicación móvil. |
| Gerencia | Supervisa las operaciones logísticas y consulta indicadores del negocio. | Aplicación web (PC) y aplicación móvil. |

## 13. Cronograma de Alto Nivel

| N.° | Actividad / Hito | Duración | Fecha inicio | Fecha fin | Responsable |
|---|---|---|---|---|---|
| 1 | Inicio del proyecto de auditoría | 1 día | 24/06/2026 | 24/06/2026 | Auditor Líder |
| 2 | FASE 1 – Preparación y Planificación | 2 días | 24/06/2026 | 25/06/2026 | Equipo Auditor |
| 2.1 | Definición de criterios, objetivos y alcance | 1 día | 24/06/2026 | 24/06/2026 | Auditor Líder |
| 2.2 | Identificación del modelo SDLC, arquitectura, tecnologías y estándares | 1 día | 24/06/2026 | 24/06/2026 | Equipo Auditor |
| 2.3 | Elaboración del Plan de Auditoría | 1 día | 25/06/2026 | 25/06/2026 | Auditor Líder |
| 2.4 | Adaptación del Checklist SDLC | 1 día | 25/06/2026 | 25/06/2026 | Equipo Auditor |
| 2.5 | Comunicación de inicio al equipo del proyecto Techmovil | 1 día | 25/06/2026 | 25/06/2026 | Auditor Líder |
| 3 | FASE 2 – Descripción del Proceso de Desarrollo | 2 días | 26/06/2026 | 27/06/2026 | Equipo Auditor |
| 3.1 | Revisión de la documentación del proyecto Techmovil | 1 día | 26/06/2026 | 26/06/2026 | Equipo Auditor |
| 3.2 | Registro de entrevistas y recopilación de evidencias | 1 día | 26/06/2026 | 26/06/2026 | Equipo Auditor |
| 3.3 | Elaboración de los Papeles de Trabajo de Auditoría | 1 día | 27/06/2026 | 27/06/2026 | Equipo Auditor |
| 4 | FASE 3 – Evaluación y Reporte | 1 día | 28/06/2026 | 28/06/2026 | Equipo Auditor |
| 4.1 | Evaluación de evidencias frente a los criterios de auditoría | — | 28/06/2026 | 28/06/2026 | Equipo Auditor |
| 4.2 | Elaboración de la Matriz de Hallazgos y Matriz de Riesgos | — | 28/06/2026 | 28/06/2026 | Equipo Auditor |
| 4.3 | Elaboración del Informe Preliminar e Informe Final de Auditoría | — | 28/06/2026 | 28/06/2026 | Auditor Líder |
| 5 | FASE 4 – Seguimiento y Cierre | 1 día | 29/06/2026 | 29/06/2026 | Equipo Auditor |
| 5.1 | Elaboración del Plan de Acción Correctiva | — | 29/06/2026 | 29/06/2026 | Equipo Auditor |
| 5.2 | Elaboración del Acta de Cierre de Auditoría | — | 29/06/2026 | 29/06/2026 | Auditor Líder |
| 5.3 | Archivo de Papeles de Trabajo | — | 29/06/2026 | 29/06/2026 | Equipo Auditor |
| 6 | Cierre del proyecto de auditoría | 1 día | 29/06/2026 | 29/06/2026 | Auditor Líder |

*Nota: en el documento fuente, las sub-actividades 4.1–4.3 y 5.1–5.3 no consignan una duración explícita en días (se marcan con "—" en esta tabla).*

## 14. Presupuesto Estimado

| Recurso | Rol | Horas Est. | Tarifa/Hora | Subtotal | % del Total |
|---|---|---|---|---|---|
| Idonis Mijael Paye Trujillo | Auditor Líder | 18 h | S/ 0.00 | S/ 0.00 | — |
| Kevin Edwin Sucapuca Calcinay | Auditor Documental | 14 h | S/ 0.00 | S/ 0.00 | — |
| Yohan Layder Escarcena Pancca | Líder del Proyecto Techmovil (Equipo Auditado) | 14 h | S/ 0.00 | S/ 0.00 | — |
| Herramientas utilizadas (GitHub, Jira, SonarCloud, Swagger, K6) | Recursos académicos | — | — | S/ 0.00 | — |
| **TOTAL** | | **46 h** | | **S/ 0.00** | **100%** |

## 15. Riesgos del Proyecto de Auditoría

| ID | Riesgo | Prob. | Impacto | Nivel | Estrategia de Respuesta |
|---|---|---|---|---|---|
| R-01 | Documentación incompleta del proyecto Techmovil. | Alta | Alto | Crítico | Solicitar toda la documentación disponible y registrar la ausencia de información como hallazgo de auditoría. |
| R-02 | Falta de evidencias de pruebas funcionales, integración o rendimiento. | Media | Alto | Alto | Revisar repositorios, registros de pruebas y entrevistar al equipo de desarrollo para complementar la evidencia. |
| R-03 | Información inconsistente entre la documentación y la implementación del sistema. | Media | Alto | Alto | Comparar la documentación con la arquitectura, el código fuente y las evidencias técnicas disponibles. |
| R-04 | Acceso limitado al repositorio o a los artefactos del proyecto. | Media | Medio | Medio | Coordinar con el equipo de desarrollo el acceso oportuno a los recursos necesarios para la auditoría. |
| R-05 | Cambios en el alcance durante la ejecución de la auditoría. | Baja | Alto | Medio | Gestionar cualquier cambio mediante autorización del Auditor Líder y actualizar el plan de auditoría cuando corresponda. |
| R-06 | Disponibilidad limitada de los integrantes del equipo para entrevistas o validaciones. | Media | Medio | Medio | Programar las reuniones con anticipación y utilizar medios virtuales cuando sea necesario. |
| R-07 | Sesgo en la evaluación al tratarse de un proyecto académico desarrollado por el mismo equipo. | Baja | Alto | Medio | Aplicar criterios objetivos basados en ISO/IEC 12207, ISO/IEC 25010, CMMI-DEV y el Checklist SDLC. |
| R-08 | Pérdida o eliminación accidental de evidencias y papeles de trabajo. | Baja | Alto | Medio | Mantener copias de seguridad de toda la documentación en un repositorio seguro y controlado. |
| R-09 | Retraso en la revisión y aprobación de los entregables de auditoría. | Media | Medio | Medio | Realizar revisiones periódicas y dar seguimiento al cronograma establecido. |
| R-10 | Errores en la interpretación de la evidencia recopilada. | Baja | Alto | Medio | Validar los hallazgos con múltiples fuentes de información y revisar los resultados antes de emitir el informe final. |

**Escala de Probabilidad:** Baja (<30%), Media (30–60%), Alta (>60%).

**Nivel de Riesgo:** Bajo, Medio, Alto y Crítico.

## 16. Factores Críticos de Éxito

- **Compromiso del patrocinador:** El respaldo de la Universidad Peruana Unión y del Ing. Rubén Roque Sucari es fundamental para garantizar el desarrollo adecuado de la auditoría y la disponibilidad de los recursos académicos necesarios.
- **Objetividad del equipo auditor:** El Auditor Líder y el Equipo Auditor deben aplicar los criterios de auditoría de manera imparcial, basándose en las normas ISO/IEC 12207, ISO/IEC 25010, CMMI-DEV y el Checklist SDLC, garantizando la objetividad de los hallazgos y conclusiones.
- **Cooperación del equipo de desarrollo:** La disposición del equipo equipo de desarrollo para proporcionar información, participar en entrevistas y facilitar el acceso a la documentación y evidencias del proyecto es esencial para el éxito de la auditoría.
- **Definición clara del alcance:** La correcta delimitación del alcance de la auditoría permite enfocar las actividades en los procesos del Ciclo de Vida del Desarrollo de Software (SDLC), evitando desviaciones o cambios no planificados.
- **Aplicación adecuada de la metodología de auditoría:** La utilización del Plan de Auditoría, el Checklist SDLC y los criterios establecidos garantiza la ejecución ordenada, consistente y completa de la auditoría.
- **Gestión adecuada de las evidencias:** La recopilación, organización, análisis y resguardo de las evidencias de auditoría permiten sustentar de manera objetiva los hallazgos, conclusiones y recomendaciones emitidas.
- **Comunicación efectiva entre los participantes:** La comunicación permanente entre el Equipo Auditor, el Equipo de Desarrollo, el patrocinador y el docente supervisor facilita la coordinación de actividades y la validación de los resultados obtenidos.
- **Disponibilidad de tiempo y recursos:** La ejecución de todas las fases de la auditoría conforme al cronograma establecido y la disponibilidad de los recursos humanos y tecnológicos son factores clave para cumplir los objetivos planteados.

## 17. Criterios de Aceptación

El proyecto de auditoría se considerará exitosamente completado cuando se cumplan los siguientes criterios:

- Se han ejecutado las cuatro fases de la auditoría SDLC conforme a la metodología definida en el Plan de Auditoría.
- Se ha recopilado evidencia suficiente, pertinente y confiable para sustentar los hallazgos documentados en la Matriz de Hallazgos.
- Se han elaborado y revisado todos los entregables establecidos en el Project Charter, incluyendo el Plan de Auditoría, Registro de Evidencias, Papeles de Trabajo, Matriz de Hallazgos, Matriz de Riesgos, Informes de Auditoría, Plan de Acción Correctiva y Acta de Cierre.
- El Informe Final de Auditoría cubre el cumplimiento de todos los objetivos generales y específicos definidos para la auditoría del sistema Techmovil.
- El equipo de desarrollo ha elaborado el Plan de Acción Correctiva en respuesta a las observaciones y recomendaciones emitidas durante la auditoría.
- El Acta de Cierre de la Auditoría ha sido elaborada y aprobada por el Auditor Líder y el patrocinador del proyecto.
- Todos los papeles de trabajo, evidencias y documentación generada durante la auditoría han sido organizados y archivados para futuras consultas o auditorías.
- El Informe Final de Auditoría cumple con los principios de objetividad, integridad, claridad, confidencialidad y trazabilidad, conforme a las buenas prácticas de auditoría y a los estándares ISO/IEC 12207, ISO/IEC 25010 y CMMI-DEV.

## 18. Supuestos

- El equipo de desarrollo colaborará activamente con el Equipo Auditor, proporcionando la información, documentación y evidencias requeridas durante la ejecución de la auditoría.
- El patrocinador y el docente supervisor facilitarán los recursos académicos necesarios para el desarrollo de la auditoría conforme al cronograma establecido.
- La documentación del proyecto Techmovil (Project Charter, documentación de requisitos, diagramas C4, diagramas UML, documentación técnica, repositorios y demás artefactos) es auténtica y representa fielmente el trabajo desarrollado por el equipo del proyecto.
- El Equipo Auditor cuenta con los conocimientos técnicos y metodológicos necesarios para evaluar el sistema Techmovil, desarrollado con una arquitectura monolítica modular utilizando Spring Boot, Angular, Flutter y MySQL.
- Los criterios de auditoría establecidos (ISO/IEC 12207, ISO/IEC 25010, CMMI-DEV y Checklist SDLC) son adecuados y aplicables al contexto académico del proyecto Techmovil.
- La auditoría se realizará sobre la versión final del sistema Techmovil, asumiendo que no se efectuarán cambios significativos en el software durante el proceso de auditoría.
- Los integrantes del proyecto estarán disponibles para atender entrevistas, resolver consultas y proporcionar las evidencias necesarias para el desarrollo de la auditoría.

## 19. Restricciones

- **Restricción de tiempo:** La auditoría deberá ejecutarse dentro del periodo comprendido entre el 24 de junio y el 29 de junio de 2026, de acuerdo con el cronograma establecido y las actividades programadas para cada fase.
- **Restricción de recursos:** El Equipo Auditor está conformado por un número limitado de integrantes, por lo que las actividades de auditoría deberán ejecutarse optimizando el tiempo y los recursos disponibles.
- **Restricción de acceso al sistema:** La auditoría se realizará sobre la versión disponible del sistema Techmovil, por lo que las pruebas y verificaciones dependerán del acceso a los módulos, la documentación y las evidencias proporcionadas por el equipo de desarrollo.
- **Restricción documental:** La auditoría se limitará a la documentación, artefactos, repositorios y evidencias generadas durante el desarrollo del proyecto Techmovil por el equipo equipo de desarrollo.
- **Restricción de confidencialidad:** Toda la información obtenida durante la auditoría será tratada de forma confidencial y utilizada exclusivamente para fines académicos y para la elaboración de los informes de auditoría.
- **Restricción de objetividad:** El Equipo Auditor deberá aplicar los criterios de evaluación establecidos en el presente Project Charter, garantizando la imparcialidad y el análisis objetivo de las evidencias recopiladas.
- **Restricción normativa:** La auditoría se desarrollará únicamente con base en los criterios definidos en el presente Project Charter y en los estándares ISO/IEC 12207, ISO/IEC 25010, CMMI-DEV y el Checklist SDLC, sin incorporar criterios adicionales que no hayan sido previamente establecidos.

## 20. Aprobaciones

El presente Project Charter de Auditoría SDLC del Proyecto Techmovil – Solución Logística Centralizada ha sido revisado y se somete a la aprobación formal de las partes responsables que participan en el proceso de auditoría. La aprobación del presente documento representa la conformidad con los objetivos, alcance, metodología, cronograma, recursos y demás aspectos establecidos para la ejecución de la Auditoría del Ciclo de Vida del Desarrollo de Software (SDLC) del proyecto Techmovil. Asimismo, las partes involucradas manifiestan su compromiso de colaborar con el desarrollo de la auditoría, proporcionando la información, documentación y evidencias necesarias para el cumplimiento de los objetivos definidos en este Project Charter.

| Rol | Nombre | Fecha |
|---|---|---|
| Patrocinador — Universidad Peruana Unión | UPEU | 26/06/2026 (firma pendiente) |
| Auditor líder | Idonis Mijael Paye Trujillo | 26/06/2026 (firma pendiente) |
| Representante del equipo auditado (Grupo equipo de desarrollo) | Yohan Layder Escarcena Pancca | 26/06/2026 (firma pendiente) |
| Profesor responsable | Ruben Roque Sucari | 26/06/2026 (firma pendiente) |

---

**Documento de uso interno — clasificación:** Confidencial · **Versión:** 1.0 · **Código:** AUD-SDLC-CODE-SISTEM-SOLUTION-2026-001
