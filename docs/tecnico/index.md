# Documentación Técnica

Esta sección reúne los **entregables técnicos y de calidad reales** del sistema TechMovil ERP (Spring Boot + React), tal como fueron producidos por el propio equipo de desarrollo: guía de instalación, manual de CI/CD, informes de pruebas E2E, pruebas de rendimiento K6, informe de seguridad OWASP, análisis de calidad SonarQube, casos de prueba IEEE 829, manual de usuario, gestión de proyecto en Jira y guía de despliegue a producción.

A diferencia de las páginas de **Fase 1** a **Fase 4**, que documentan la auditoría del ciclo de vida de desarrollo (SDLC) realizada *sobre* el proyecto Techmovil, los documentos de esta sección **no son entregables de auditoría**: son la documentación técnica y de QA que el propio equipo de FinTechmovil/TechMovil generó durante la construcción, prueba y despliegue del sistema. Sirven como fuente primaria — muchos de ellos son, de hecho, la evidencia que la auditoría revisó y referenció en sus propios papeles de trabajo.

El sistema documentado es un ERP de gestión de inventario y ventas de celulares, construido con **Spring Boot 3.2.5** (backend, Java 17/21) y **React 19 + Vite** (frontend), con MySQL 8.0 como base de datos, JWT para autenticación, y una batería de calidad que incluye 196 pruebas unitarias JUnit 5, análisis SonarQube, pruebas de integración con Postman, pruebas E2E manuales, pruebas de carga con K6/Grafana y una evaluación de seguridad OWASP Top 10:2021.

Las cifras clave que aparecen de forma recurrente en estos documentos — 196 tests unitarios, 83.7% de cobertura JaCoCo, Quality Gate aprobado, 45 assertions de integración Postman, Nivel A en seguridad OWASP — se reproducen tal como constan en cada entregable, incluso cuando existen pequeñas inconsistencias entre documentos (por ejemplo, cifras de cobertura ligeramente distintas entre el manual de CI/CD y la guía de instalación), ya que cada informe fue redactado en un momento distinto del proyecto.

## Índice de documentos

- [Guía de Instalación Local](guia-instalacion-local.md) — Puesta en marcha completa del sistema (backend, frontend, base de datos, Postman, SonarQube, K6) en un entorno local, paso a paso.
- [Manual de CI/CD](manual-cicd.md) — Configuración del pipeline de integración y despliegue continuo con GitHub Actions, Docker Compose, SonarQube y despliegue en Railway/Vercel.
- [Informe de Pruebas E2E (Manuales)](informe-e2e.md) — Detalle de los casos de prueba End-to-End ejecutados manualmente sobre los módulos de Autenticación, Productos, Inventario, Ventas POS, Reportes y Clientes.
- [Informe de Pruebas E2E + Rendimiento K6 (Resumen)](informe-e2e-k6-resumen.md) — Resumen combinado de pruebas funcionales E2E, integración Postman y rendimiento K6 en un solo informe consolidado.
- [Informe de Rendimiento K6](informe-rendimiento-k6.md) — Informe dedicado de pruebas de carga Smoke/Load/Stress sobre el backend desplegado en producción (Railway).
- [Informe de Seguridad OWASP](informe-seguridad-owasp.md) — Evaluación de las 10 categorías de OWASP Top 10:2021 sobre el sistema FinTechmovil ERP.
- [Informe SonarQube](informe-sonarqube.md) — Resumen del análisis SonarQube (Quality Gate, cobertura 83.7%) y detalle de las 196 pruebas unitarias por clase.
- [Casos de Prueba IEEE 829](casos-prueba-ieee829.md) — 196 casos de prueba IEEE 829 (17 casos funcionales/sistema, 1 defecto real encontrado: exportación a Excel no implementada, dashboard de métricas por módulo).
- [Manual de Usuario](manual-usuario.md) — Guía de uso del sistema para el usuario final: login, dashboard, productos, inventario, ventas POS, reportes y clientes.
- [Gestión de Proyecto (Jira)](gestion-proyecto-jira.md) — Backlog, sprints, métricas de velocidad y definición de listo (DoD) del proyecto gestionado con metodología Scrum en Jira Software.
- [Guía de Deploy (Railway / Vercel)](guia-deploy-railway-vercel.md) — Despliegue del backend en Railway y del frontend en Vercel para obtener una URL pública funcional.
