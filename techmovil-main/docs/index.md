# Auditoría SDLC · AstraLog (ASTRAMACO III)

Este sitio reúne los **entregables de la Unidad 3**: la Auditoría del Ciclo de Vida del Desarrollo de Software (SDLC) realizada por el equipo auditor **CoreSystems Solutions** sobre el sistema **AstraLog — Solución Logística Centralizada**, desarrollado para la empresa **ASTRAMACO III**, como trabajo del curso de Ingeniería de Software II (Universidad Peruana Unión, Ciclo VII‑G1).

La auditoría tomó como referencia **ISO/IEC 12207:2017**, **ISO/IEC 25010**, **CMMI‑DEV** y un Checklist SDLC adaptado, y se ejecutó en 4 fases durante 8 semanas.

## Resultado en una mirada

| Clasificación | Cantidad |
|---|---|
| Conformidades | 6 |
| Oportunidades de mejora | 3 |
| No conformidades menores | 1 |
| No conformidades mayores | 0 |

**Opinión del equipo auditor:** favorable — AstraLog cumple satisfactoriamente los criterios definidos para la auditoría SDLC, con una no conformidad menor relacionada a la **limitada documentación de pruebas unitarias automatizadas** (ver [evidencia de desarrollo](fase2/evidencia-desarrollo.md#pruebas-unitarias-hallazgo)).

## Navegación por fase

- **[Fase 1 · Preparar y Planificar](fase1/index.md)** — Project Charter, Plan de Auditoría, Checklist SDLC, Comunicación de Inicio.
- **[Fase 2 · Describir el Proceso de Desarrollo](fase2/index.md)** — Entrevistas, Registro de Evidencias, Papeles de Trabajo y la **galería de capturas reales** (CI/CD, SonarQube, seguridad, manual de usuario, pruebas E2E).
- **[Fase 3 · Evaluar y Reportar](fase3/index.md)** — **Informe Preliminar** e **Informe Final** de auditoría, Matriz de Hallazgos y Matriz de Riesgos.
- **[Fase 4 · Seguimiento](fase4/index.md)** — Acta de Cierre, Archivo de Papeles de Trabajo y Plan de Acción Correctiva.

## Sobre el contenido de este sitio

Cada página trae una ficha con el código y versión del documento original, un enlace de descarga al PDF fuente (en `docs/assets/entregables/`) y el contenido convertido a Markdown para lectura en línea. Las capturas de evidencia en la Fase 2 son imágenes reales extraídas de los documentos de desarrollo (no generadas para este sitio); donde no existe evidencia visual real — como en Pruebas Unitarias — se indica explícitamente en vez de rellenarlo con capturas inventadas.

!!! note "Cómo generar el sitio estático"
    Este repositorio no tiene Python/MkDocs instalados. Para previsualizar o publicar el sitio:

    ```bash
    pip install mkdocs-material
    mkdocs serve   # vista previa local en http://127.0.0.1:8000
    mkdocs build   # genera el sitio estático en site/
    ```
