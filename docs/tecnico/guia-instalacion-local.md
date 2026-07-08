# Guía de Instalación Local

*TechMovil ERP v1.0.0 — Guía completa de instalación y puesta en marcha. Universidad Nacional del Altiplano, Juliaca, 2026.*

Desde cero hasta el sistema funcionando en 15 minutos.

## Acceso rápido — todas las URLs

| Servicio | URL | Credenciales |
|---|---|---|
| Frontend (App Web) | `http://localhost:5173` | admin / 1234 |
| Backend API REST | `http://localhost:8080` | admin / admin123 |
| Swagger UI (Docs API) | `http://localhost:8080/swagger-ui/index.html` | Sin credenciales |
| API Productos (JSON) | `http://localhost:8080/api/productos` | Sin autenticación |
| API Login (JWT) | `http://localhost:8080/api/auth/login` | POST `{username, password}` |
| SonarQube (Calidad) | `http://localhost:9000` | admin / admin |
| Grafana (K6 Métricas) | `http://localhost:3001` | Sin contraseña (anónimo) |

## Paso 0 — Prerrequisitos (instalar antes de todo)

| Programa | Versión | Requerido | Enlace de descarga |
|---|---|---|---|
| Java JDK Corretto 17 | 17 LTS | Sí | https://corretto.aws/downloads/latest/amazon-corretto-17-x64-windows-jdk.msi |
| Node.js | 20 LTS | Sí | https://nodejs.org/en/download |
| MySQL Community Server | 8.0 | Sí | https://dev.mysql.com/downloads/mysql |
| MySQL Workbench | 8.0 | Recomendado | https://dev.mysql.com/downloads/workbench |
| Git | 2.x | Recomendado | https://git-scm.com/downloads |
| Postman | 11.x | Opcional | https://www.postman.com/downloads |
| Docker Desktop | 4.x | Opcional | https://www.docker.com/get-started |

Verificar que estén instalados (abrir PowerShell):

```
java -version      # Debe mostrar: openjdk version 17 o 21
node -v            # Debe mostrar: v20.x.x
npm -v             # Debe mostrar: 10.x.x
mysql --version    # Debe mostrar: Ver 8.0.x
```

## Paso 1 — Descomprimir el proyecto

Descargar el archivo de la entrega `TECHMOVIL_FINAL_COMPLETO.zip`.

1. Click derecho sobre el ZIP → Extraer todo.
2. Elegir una ruta corta sin espacios. Recomendado: `C:\TechMovil\`.

Estructura de carpetas:

```
C:\TechMovil\1_BACKEND_CON_TESTS\      <- Código Java + Spring Boot
C:\TechMovil\2_FRONTEND\               <- Código React 19 + Vite
C:\TechMovil\3_POSTMAN\                <- Colección de pruebas API
C:\TechMovil\4_DOCUMENTOS_WORD_EXCEL\  <- Todos los informes
C:\TechMovil\6_K6_GRAFANA\             <- Load testing con Docker
```

## Paso 2 — Configurar la base de datos MySQL

**Opción A — Con MySQL Workbench (más fácil):**

1. Abrir MySQL Workbench.
2. Click en la conexión `localhost` (usuario: `root`, contraseña: `root`).
3. Menú superior: File → New Query Tab.
4. Escribir y presionar `Ctrl+Enter`:

```sql
CREATE DATABASE IF NOT EXISTS techmovil_db CHARACTER SET utf8mb4;
```

5. Verificar que dice: `1 row(s) affected`. Listo.

**Opción B — PowerShell (más rápido):**

```
mysql -u root -proot -e "CREATE DATABASE IF NOT EXISTS techmovil_db;"
```

### Datos de conexión MySQL

| Campo | Valor |
|---|---|
| Host | `localhost` |
| Puerto | `3306` |
| Base de Datos | `techmovil_db` |
| Usuario | `root` |
| Contraseña | `root` |

Si tu MySQL tiene una contraseña diferente a `root`, editar el archivo `1_BACKEND_CON_TESTS\src\main\resources\application.properties`:

```properties
spring.datasource.username=root
spring.datasource.password=TU_CONTRASENA_REAL
```

## Paso 3 — Iniciar el backend (Spring Boot)

Abrir PowerShell → Ventana 1:

```
cd C:\TechMovil\1_BACKEND_CON_TESTS
.\mvnw.cmd spring-boot:run
```

La primera vez descarga dependencias Maven (2-5 min según tu internet). Las siguientes veces arranca en solo 15-30 segundos.

El backend está listo cuando ves en la consola:

```
Started TechmovilApplication in 8.234 seconds (process running for 9.1)
Tomcat started on port(s): 8080 (http)
```

Verificar en el navegador (deben responder):

- `http://localhost:8080/api/productos` — Lista JSON de productos
- `http://localhost:8080/swagger-ui/index.html` — Documentación interactiva

### Credenciales del backend

| Campo | Valor |
|---|---|
| Usuario (`application.properties`) | `admin` |
| Contraseña (`application.properties`) | `admin123` |
| JWT Token para Swagger/Postman | `Bearer <token_que_retorna_el_login>` |

## Paso 4 — Iniciar el frontend (React 19 + Vite)

Abrir PowerShell → Ventana 2 (nueva ventana, dejar el backend corriendo en la anterior):

```
cd C:\TechMovil\2_FRONTEND
npm install          <- SOLO la primera vez (instala React + Vite)
npm run dev          <- Inicia el servidor de desarrollo
```

El frontend está listo cuando ves en la consola:

```
  VITE v5.x.x  ready in 800 ms

  Local:   http://localhost:5173/
  Network: http://192.168.x.x:5173/
```

Abrir el navegador en `http://localhost:5173`.

### Credenciales del frontend

| Campo | Valor |
|---|---|
| Usuario | `admin` |
| Contraseña | `1234` |
| Email alternativo | `admin@techmovil.com` |
| Password alternativo | `Admin123!` |

!!! note "Modo demo sin backend"
    El frontend funciona 100% SIN el backend (modo demo con datos de prueba incorporados). Si el backend está corriendo, también conecta automáticamente a MySQL.

## Paso 5 — Resumen completo de URLs

(Ver tabla "Acceso rápido — todas las URLs" al inicio de esta página.)

## Paso 6 — Ejecutar las pruebas unitarias

```
cd C:\TechMovil\1_BACKEND_CON_TESTS

.\mvnw.cmd test                  <- Solo ejecuta los 196 tests
.\mvnw.cmd verify                <- Tests + reporte de cobertura JaCoCo
.\EJECUTAR.ps1                   <- Tests + SonarQube completo (auto)
```

Resultado esperado:

```
Tests run: 196, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

Ver el reporte de cobertura visual (**83.7%**) en el navegador:

```
1_BACKEND_CON_TESTS\target\site\jacoco\index.html
```

## Paso 7 — Postman (pruebas de integración API)

1. Descargar Postman: https://www.postman.com/downloads
2. Abrir Postman → Click en "Import" (botón azul arriba a la izquierda).
3. Seleccionar el archivo `3_POSTMAN\TechMovil_Postman_Collection.json`.
4. Click en "Import".
5. Expandir la colección en la barra lateral izquierda.
6. Ejecutar primero: `1. AUTH > Login Admin` (guarda el token JWT automáticamente).
7. Luego ejecutar el resto de grupos.
8. Para ejecutar todo: Click derecho en la colección → Run collection.

## Paso 8 — SonarQube (análisis de calidad)

Iniciar SonarQube con Docker (si no está corriendo):

```
docker run -d --name sonarqube -p 9000:9000 sonarqube:community
```

Esperar 2 minutos. Acceder a `http://localhost:9000` (admin / admin).

Ejecutar el análisis completo desde el backend:

```
cd C:\TechMovil\1_BACKEND_CON_TESTS
.\EJECUTAR.ps1
```

Ver resultado en `http://localhost:9000/dashboard?id=techmovil`.

## Paso 9 — K6 + Grafana (load testing)

```
cd C:\TechMovil\6_K6_GRAFANA
.\EJECUTAR_K6.ps1
```

Grafana disponible en `http://localhost:3001` (sin contraseña).

Ejecutar el Smoke Test (con el backend corriendo):

```
docker-compose -f docker-compose-k6.yml run --rm k6 run ^
  --out influxdb=http://influxdb:8086/k6 /scripts/01_smoke_test.js
```

## Paso extra — subir a GitHub

```
cd C:\TechMovil
git init
git add .
git commit -m "feat: TechMovil ERP v1.0.0 - Sistema ERP completo"
git branch -M main
git remote add origin https://github.com/TU_USUARIO/techmovil-erp.git
git push -u origin main
```

El código quedará en `https://github.com/TU_USUARIO/techmovil-erp`.

## Solución de problemas comunes

| Error común | Causa probable | Solución |
|---|---|---|
| `'java' is not recognized` | Java no instalado o PATH incompleto | Instalar Corretto 17 y reiniciar PC |
| `Communications link failure` (MySQL) | MySQL no está corriendo | Iniciar MySQL desde Servicios de Windows |
| `Access denied for user root` | Contraseña MySQL incorrecta | Editar `application.properties` |
| `Port 8080 already in use` | Otro proceso usa el puerto 8080 | `netstat -ano \| findstr 8080` |
| `'npm' is not recognized` | Node.js no instalado | Instalar Node.js 20 LTS |
| `localhost:5173` no carga | Frontend no iniciado | `cd 2_FRONTEND` y `npm run dev` |
| `BUILD FAILURE` en Maven | Error de compilación o dependencias | Verificar Java 17+ instalado |
| Login falla (frontend) | Credenciales incorrectas | Usar exactamente `admin` / `1234` |

## Cheat sheet — comandos rápidos

**Backend (Ventana PowerShell 1)**

```
cd C:\TechMovil\1_BACKEND_CON_TESTS
.\mvnw.cmd spring-boot:run       # Iniciar
.\mvnw.cmd test                  # 196 tests
.\mvnw.cmd verify                # + cobertura
.\EJECUTAR.ps1                   # + SonarQube
.\mvnw.cmd package -DskipTests   # JAR
```

**Frontend (Ventana PowerShell 2)**

```
cd C:\TechMovil\2_FRONTEND
npm install     # Solo la primera vez
npm run dev     # localhost:5173
npm run build   # Build producción
npm run preview # Ver build local
```

**Docker (opcional)**

```
docker-compose up -d --build   # Todo
docker run -d -p 9000:9000 sonarqube
docker ps                      # Ver activos
docker stop $(docker ps -q)    # Parar todo
```

**K6 + Grafana**

```
cd C:\TechMovil\6_K6_GRAFANA
.\EJECUTAR_K6.ps1               # Grafana
# Grafana: http://localhost:3001
# Smoke/Load/Stress test scripts
```
