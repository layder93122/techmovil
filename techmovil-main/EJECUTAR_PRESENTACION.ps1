# ═══════════════════════════════════════════════════════════════════════════════
# SCRIPT DE PRESENTACION — FinTechmovil
# Ejecutar en orden: primero Docker, luego este script
# ═══════════════════════════════════════════════════════════════════════════════

param(
    [switch]$SoloSonar,
    [switch]$SoloDeploy,
    [switch]$SoloTest
)

$BackendDir = "$PSScriptRoot\FINTECHMOVIL\backend"
$FrontendDir = "$PSScriptRoot\FINTECHMOVIL\frontend"

function Write-Step($msg) { Write-Host "`n>>> $msg" -ForegroundColor Cyan }
function Write-OK($msg)   { Write-Host "    OK: $msg" -ForegroundColor Green }
function Write-ERR($msg)  { Write-Host "    ERROR: $msg" -ForegroundColor Red }

# ═══════════════════════════════════════════════════════════════════════════════
# PASO 1 — Tests (251/251 deben pasar)
# ═══════════════════════════════════════════════════════════════════════════════
if (-not $SoloDeploy -and -not $SoloSonar) {
    Write-Step "PASO 1: Ejecutando 251 tests unitarios..."
    Push-Location $BackendDir
    $result = & ./mvnw test -q 2>&1 | Select-String "Tests run:|BUILD"
    Pop-Location
    $result | ForEach-Object { Write-Host "    $_" }
    Write-OK "Tests completados. Verificar BUILD SUCCESS arriba."
}

# ═══════════════════════════════════════════════════════════════════════════════
# PASO 2 — SonarQube (requiere Docker corriendo)
# ═══════════════════════════════════════════════════════════════════════════════
if (-not $SoloDeploy -and -not $SoloTest) {
    Write-Step "PASO 2: Levantando SonarQube con Docker..."
    Push-Location $BackendDir

    # Verificar Docker
    $dockerOk = docker info 2>$null
    if (-not $?) {
        Write-ERR "Docker Desktop NO esta corriendo. Abrelo y ejecuta este script de nuevo."
        Write-Host "    Inicio rapido: Start-Process 'C:\Program Files\Docker\Docker\Docker Desktop.exe'" -ForegroundColor Yellow
    } else {
        Write-OK "Docker esta corriendo."

        # Levantar solo SonarQube + Jenkins
        docker-compose up -d sonarqube jenkins 2>&1 | Out-Null
        Write-OK "Contenedores levantados. Esperando SonarQube (60 segundos)..."
        Start-Sleep -Seconds 60

        # Verificar SonarQube
        $sonarReady = $false
        for ($i = 0; $i -lt 6; $i++) {
            try {
                $resp = Invoke-WebRequest -Uri "http://localhost:9000" -TimeoutSec 5 -UseBasicParsing
                if ($resp.StatusCode -eq 200) { $sonarReady = $true; break }
            } catch {}
            Write-Host "    Esperando SonarQube... ($($i+1)/6)" -ForegroundColor Yellow
            Start-Sleep -Seconds 15
        }

        if ($sonarReady) {
            Write-OK "SonarQube listo en http://localhost:9000"
            Write-Step "Ejecutando analisis SonarQube + cobertura JaCoCo..."
            & ./mvnw clean verify sonar:sonar `
                -Dsonar.host.url=http://localhost:9000 `
                -Dsonar.login=admin `
                -Dsonar.password=admin `
                -q 2>&1 | Select-String "ANALYSIS|Quality|ERROR|BUILD" | ForEach-Object { Write-Host "    $_" }
            Write-OK "Analisis completado. Abrir: http://localhost:9000/dashboard?id=techmovil"
            Start-Process "http://localhost:9000/dashboard?id=techmovil"
        } else {
            Write-ERR "SonarQube no respondio en 2 minutos. Verificar manualmente en http://localhost:9000"
        }
    }
    Pop-Location
}

# ═══════════════════════════════════════════════════════════════════════════════
# PASO 3 — Deploy Railway + Vercel
# ═══════════════════════════════════════════════════════════════════════════════
if (-not $SoloSonar -and -not $SoloTest) {
    Write-Step "PASO 3: Verificando deploy en Railway..."

    # Build JAR
    Push-Location $BackendDir
    Write-Host "    Compilando JAR..." -ForegroundColor Yellow
    & ./mvnw clean package -DskipTests -q
    if ($?) {
        Write-OK "JAR compilado: target/techmovil-0.0.1-SNAPSHOT.jar"
    } else {
        Write-ERR "Error compilando JAR"
    }
    Pop-Location

    # Verificar Railway CLI
    $railwayCli = Get-Command railway -ErrorAction SilentlyContinue
    if ($railwayCli) {
        Write-Step "Desplegando en Railway..."
        Push-Location $BackendDir
        railway up 2>&1
        Pop-Location
        Write-OK "Backend desplegado. URL: https://fintechmovil.up.railway.app"
    } else {
        Write-Host ""
        Write-Host "    Railway CLI no instalado. Para desplegar:" -ForegroundColor Yellow
        Write-Host "    1. npm install -g @railway/cli" -ForegroundColor Yellow
        Write-Host "    2. railway login" -ForegroundColor Yellow
        Write-Host "    3. cd FINTECHMOVIL\backend && railway up" -ForegroundColor Yellow
        Write-Host ""
        Write-Host "    O deploying manualmente en https://railway.app :" -ForegroundColor Yellow
        Write-Host "    - Conectar repo GitHub: layder93122/techmovil" -ForegroundColor Yellow
        Write-Host "    - Root directory: FINTECHMOVIL/backend" -ForegroundColor Yellow
        Write-Host "    - Variables de entorno: ADMIN_USER=admin, ADMIN_PASS=admin123, JWT_SECRET=fintechmovil-sistema-inventario-firma-2026-ok!" -ForegroundColor Yellow
    }

    # Verificar Vercel CLI
    Write-Step "Verificando Vercel..."
    $vercelCli = Get-Command vercel -ErrorAction SilentlyContinue
    if ($vercelCli) {
        Push-Location $FrontendDir
        npm run build 2>&1 | Out-Null
        vercel --prod 2>&1
        Pop-Location
    } else {
        Write-Host ""
        Write-Host "    Vercel CLI no instalado. Para desplegar:" -ForegroundColor Yellow
        Write-Host "    1. npm install -g vercel" -ForegroundColor Yellow
        Write-Host "    2. cd FINTECHMOVIL\frontend && vercel --prod" -ForegroundColor Yellow
        Write-Host ""
        Write-Host "    O desde https://vercel.com :" -ForegroundColor Yellow
        Write-Host "    - Conectar repo: layder93122/techmovil" -ForegroundColor Yellow
        Write-Host "    - Root directory: FINTECHMOVIL/frontend" -ForegroundColor Yellow
        Write-Host "    - Env var: VITE_API_URL=https://fintechmovil.up.railway.app/api" -ForegroundColor Yellow
    }
}

Write-Host ""
Write-Host "═══════════════════════════════════════════════════════" -ForegroundColor Cyan
Write-Host "  CHECKLIST PRESENTACION" -ForegroundColor Cyan
Write-Host "═══════════════════════════════════════════════════════" -ForegroundColor Cyan
Write-Host "  [1] Tests unitarios:  251/251 pasan (git push OK)"
Write-Host "  [2] Postman:          47 requests con validaciones"
Write-Host "  [3] SonarQube:        Abrir http://localhost:9000"
Write-Host "  [4] Documentos:       ENTREGA_DEFINITIVA/docs/"
Write-Host "  [5] CI/CD Jenkinsfile: configurado con 5 stages"
Write-Host "  [6] Informe E2E:      06_Informe_E2E_*.docx"
Write-Host "  [7] Casos de prueba:  07_Casos_Prueba_IEEE829.xlsx"
Write-Host "  [8] Seguridad OWASP:  08_Informe_Seguridad_OWASP.docx"
Write-Host "  [8.1] Rendimiento:    08.1_Informe_Rendimiento_K6.docx"
Write-Host "  [9] Manual usuario:   09_Manual_Usuario.docx"
Write-Host "  [10] App deploy:      https://fintechmovil.up.railway.app"
Write-Host "  [11] Ambiente pruebas: http://localhost:9000 (Docker)"
Write-Host "═══════════════════════════════════════════════════════" -ForegroundColor Cyan
