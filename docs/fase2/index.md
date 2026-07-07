# Fase 2 · Describir el Proceso de Desarrollo en Detalle (Describe the Development Process)

En esta fase el equipo auditor levantó, de primera mano, cómo se ejecutó realmente el ciclo de vida de desarrollo del sistema **AstraLog** para **ASTRAMACO III**: se entrevistó al equipo de desarrollo, se revisó la documentación técnica, el repositorio GitHub, el tablero Jira y los reportes de SonarCloud, y se recolectaron las evidencias que sustentan los hallazgos de la Fase 3.

El resultado de esta fase son tres documentos de auditoría (Registro de Entrevistas, Registro de Evidencias y Papeles de Trabajo) más una página adicional de este sitio — **Evidencia de Desarrollo** — donde se incrustan las capturas reales recopiladas por el equipo de desarrollo (Swagger/CI-CD, SonarQube, pruebas de seguridad, manual de usuario, pruebas E2E) para que el sustento visual de la auditoría quede accesible en un solo lugar.

## Documentos de esta fase

- **[Registro de Entrevistas](registro-entrevistas.md)** — 6 entrevistas estructuradas (Gestión del Proyecto, Arquitectura, Backend, Frontend, Mobile, Calidad y Pruebas) con preguntas, respuestas y conclusiones.
- **[Registro de Evidencias](registro-evidencias.md)** — 34 evidencias codificadas (EV-001 a EV-034) organizadas por área auditada, con su fuente y estado de conformidad.
- **[Papeles de Trabajo de Auditoría](papeles-trabajo.md)** — 8 papeles de trabajo (PT-001 a PT-008) que documentan el procedimiento, la evidencia revisada y la conclusión por cada área del SDLC.
- **[Evidencia de Desarrollo (capturas)](evidencia-desarrollo.md)** — galería curada de capturas reales: CI/CD (GitHub Actions), SonarQube/cobertura, pruebas de seguridad, manual de usuario y pruebas E2E manuales — incluyendo, con honestidad, en qué áreas **no** hay captura disponible (pruebas unitarias).

!!! tip "Cómo se relacionan estos documentos"
    Registro de Entrevistas → alimenta → Registro de Evidencias → alimenta → Papeles de Trabajo → alimenta → [Matriz de Hallazgos](../fase3/matriz-hallazgos.md) e [Informe Final](../fase3/informe-final.md) de la Fase 3.
