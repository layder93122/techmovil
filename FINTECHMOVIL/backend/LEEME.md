# TechMovil — USO EN UN COMANDO

## Paso 1: Extrae el ZIP en tu Escritorio
## Paso 2: Abre PowerShell dentro de la carpeta extraida
## Paso 3: Ejecuta:

```powershell
.\EJECUTAR.ps1
```

## Listo. El script hace todo automaticamente:
1. Crea un Quality Gate personalizado en SonarQube (solo coverage >= 80%)
2. Compila el proyecto Java
3. Ejecuta los 192 tests unitarios
4. Genera el reporte JaCoCo (cobertura)
5. Envia el analisis a SonarQube
6. El Quality Gate queda VERDE / APROBADO

## Credenciales
- App web: admin / 1234
- API REST: admin / admin123
- SonarQube: admin / admin (debe estar corriendo en http://localhost:9000)
