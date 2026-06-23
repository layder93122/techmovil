# ============================================================================
#  FinTechMovil — Orquestador del Ambiente de Pruebas
#  Ejecuta en orden:
#    1. Build del JAR backend
#    2. Levanta MySQL + Backend (Docker)
#    3. Pruebas de integracion con Newman (Postman)
#    4. Pruebas de rendimiento con K6 (smoke → load → stress)
#    5. Genera resumen de resultados
#
#  Uso: cd FINTECHMOVIL\backend && .\run-ambiente-pruebas.ps1
#  Requisito: Docker Desktop corriendo
# ============================================================================

param(
    [string]$BaseUrl  = "http://localhost:8080",
    [switch]$Stress,            # Incluir stress test (tarda ~3.5 min extra)
    [switch]$SkipBuild,         # Saltar mvn package si el JAR ya existe
    [switch]$KeepUp             # No bajar los contenedores al finalizar
)

Set-Location $PSScriptRoot

$OK    = "[OK]"
$ERR   = "[ERROR]"
$INFO  = "[INFO]"
$WARN  = "[WARN]"
$totalErrores = 0
$ReportDir = Join-Path $PSScriptRoot "test-reports"

function Write-Step([int]$n, [string]$msg) {
    Write-Host ""
    Write-Host "═══════════════════════════════════════════════" -ForegroundColor Cyan
    Write-Host "  PASO $n — $msg" -ForegroundColor Cyan
    Write-Host "═══════════════════════════════════════════════" -ForegroundColor Cyan
}

function Write-Ok([string]$msg)   { Write-Host "  $OK  $msg" -ForegroundColor Green  }
function Write-Err([string]$msg)  { Write-Host "  $ERR $msg" -ForegroundColor Red    ; $script:totalErrores++ }
function Write-Info([string]$msg) { Write-Host "  $INFO $msg" -ForegroundColor White }
function Write-Warn([string]$msg) { Write-Host "  $WARN $msg" -ForegroundColor Yellow }

# ── Directorio de reportes ───────────────────────────────────────────────────
if (-not (Test-Path $ReportDir)) {
    New-Item -ItemType Directory -Path $ReportDir -Force | Out-Null
}

Write-Host ""
Write-Host "╔═══════════════════════════════════════════════╗" -ForegroundColor Magenta
Write-Host "║   FINTECHMOVIL — AMBIENTE DE PRUEBAS          ║" -ForegroundColor Magenta
Write-Host "║   $(Get-Date -Format 'yyyy-MM-dd HH:mm')                       ║" -ForegroundColor Magenta
Write-Host "╚═══════════════════════════════════════════════╝" -ForegroundColor Magenta

# ═══════════════════════════════════════════════════════════════════════════════
# PASO 1 — Build del JAR
# ═══════════════════════════════════════════════════════════════════════════════
Write-Step 1 "Build del JAR (Spring Boot)"

$jarPath = "target\techmovil-0.0.1-SNAPSHOT.jar"

if ($SkipBuild -and (Test-Path $jarPath)) {
    Write-Ok "JAR encontrado, saltando build (--SkipBuild)"
} else {
    Write-Info "Compilando con Maven (puede tomar ~60 segundos)..."
    & .\mvnw.cmd clean package -DskipTests --no-transfer-progress
    if ($LASTEXITCODE -ne 0) {
        Write-Err "Maven build FALLÓ. Abortando ambiente de pruebas."
        exit 1
    }
    Write-Ok "JAR construido: $jarPath"
}

# ═══════════════════════════════════════════════════════════════════════════════
# PASO 2 — Levantar infraestructura Docker
# ═══════════════════════════════════════════════════════════════════════════════
Write-Step 2 "Levantando MySQL + Backend (Docker)"

Write-Info "Deteniendo contenedores previos si existen..."
docker-compose -f docker-compose.test.yml down --volumes --remove-orphans 2>&1 | Out-Null

Write-Info "Construyendo imagen del backend..."
docker-compose -f docker-compose.test.yml build backend-test 2>&1 | Out-Null

Write-Info "Iniciando MySQL y Backend..."
docker-compose -f docker-compose.test.yml up -d db-test backend-test

Write-Info "Esperando que el backend esté listo (máx 120s)..."
$maxWait = 120
$waited  = 0
$ready   = $false

while ($waited -lt $maxWait) {
    try {
        $resp = Invoke-WebRequest -Uri "$BaseUrl/api/productos" -TimeoutSec 3 -EA Stop
        if ($resp.StatusCode -eq 200) { $ready = $true; break }
    } catch {}
    Start-Sleep -Seconds 5
    $waited += 5
    Write-Host "  ... esperando ($waited/$maxWait s)" -ForegroundColor DarkGray
}

if (-not $ready) {
    Write-Err "Backend no respondió en $maxWait segundos."
    docker-compose -f docker-compose.test.yml logs backend-test --tail 30
    exit 1
}
Write-Ok "Backend listo en $BaseUrl"

# ═══════════════════════════════════════════════════════════════════════════════
# PASO 3 — Pruebas de Integración con Newman (Postman)
# ═══════════════════════════════════════════════════════════════════════════════
Write-Step 3 "Pruebas Integrales — Newman (Postman)"

$collectionFile = "..\FinTechmovil_Postman_Collection.json"
$newmanReport   = "$ReportDir\newman-results.xml"
$newmanHtml     = "$ReportDir\newman-report.html"

# Verificar si Newman está disponible
$newmanCmd = Get-Command newman -EA SilentlyContinue
if (-not $newmanCmd) {
    Write-Warn "Newman no encontrado. Instalando globalmente..."
    npm install -g newman newman-reporter-htmlextra 2>&1 | Out-Null
    $newmanCmd = Get-Command newman -EA SilentlyContinue
}

if ($newmanCmd) {
    Write-Info "Ejecutando colección Postman..."
    newman run $collectionFile `
        --env-var "BASE_URL=$BaseUrl" `
        --reporters cli,junit,htmlextra `
        --reporter-junit-export $newmanReport `
        --reporter-htmlextra-export $newmanHtml `
        --delay-request 300 `
        --timeout-request 15000

    if ($LASTEXITCODE -eq 0) {
        Write-Ok "Newman: todas las pruebas PASARON"
        Write-Info "Reporte HTML: $newmanHtml"
        Write-Info "Reporte JUnit: $newmanReport"
    } else {
        Write-Err "Newman: algunas pruebas FALLARON (ver reporte)"
    }
} else {
    Write-Warn "Newman no disponible. Ejecutando con Docker..."
    docker run --rm --network techmovil-test_test-net `
        -v "${PWD}\..\FinTechmovil_Postman_Collection.json:/collection.json:ro" `
        -v "${ReportDir}:/reports" `
        postman/newman:latest run /collection.json `
            --env-var "BASE_URL=http://backend-test:8080" `
            --reporters cli,junit `
            --reporter-junit-export /reports/newman-results.xml `
            --delay-request 300

    if ($LASTEXITCODE -eq 0) { Write-Ok "Newman (Docker): pruebas PASARON" }
    else { Write-Err "Newman (Docker): pruebas FALLARON" }
}

# ═══════════════════════════════════════════════════════════════════════════════
# PASO 4 — Pruebas de Rendimiento con K6
# ═══════════════════════════════════════════════════════════════════════════════
Write-Step 4 "Pruebas de Rendimiento — K6"

$k6Cmd = Get-Command k6 -EA SilentlyContinue

function Run-K6([string]$script, [string]$nombre) {
    $outputFile = "$ReportDir\k6-$nombre.json"
    Write-Info "Ejecutando: $script"
    if ($k6Cmd) {
        k6 run --env BASE_URL=$BaseUrl --out json=$outputFile "K6\$script"
    } else {
        docker run --rm --network host `
            -v "${PWD}\K6:/scripts:ro" `
            -v "${ReportDir}:/reports" `
            grafana/k6:latest run `
                --env BASE_URL=$BaseUrl `
                --out json=/reports/k6-$nombre.json `
                /scripts/$script
    }
    if ($LASTEXITCODE -eq 0) { Write-Ok "K6 $nombre: PASÓ (umbrales OK)" }
    else { Write-Err "K6 $nombre: FALLÓ (ver umbrales)" }
}

Run-K6 "smoke-test.js"           "smoke"
Run-K6 "all-controllers-test.js" "load"

if ($Stress) {
    Write-Warn "Ejecutando stress test (~3.5 min)..."
    Run-K6 "stress-test.js" "stress"
}

# ═══════════════════════════════════════════════════════════════════════════════
# PASO 5 — Limpieza y Resumen
# ═══════════════════════════════════════════════════════════════════════════════
Write-Step 5 "Resumen del Ambiente de Pruebas"

if (-not $KeepUp) {
    Write-Info "Deteniendo contenedores..."
    docker-compose -f docker-compose.test.yml down --volumes 2>&1 | Out-Null
    Write-Ok "Contenedores detenidos y volúmenes limpiados"
} else {
    Write-Warn "Contenedores mantenidos activos (--KeepUp). Detener manualmente:"
    Write-Host "    docker-compose -f docker-compose.test.yml down --volumes" -ForegroundColor DarkYellow
}

Write-Host ""
Write-Host "╔═══════════════════════════════════════════════╗" -ForegroundColor $(if ($totalErrores -eq 0) { "Green" } else { "Red" })
Write-Host "║   RESULTADO FINAL                              ║" -ForegroundColor $(if ($totalErrores -eq 0) { "Green" } else { "Red" })
Write-Host "║   Errores: $totalErrores                                  ║" -ForegroundColor $(if ($totalErrores -eq 0) { "Green" } else { "Red" })
Write-Host "╚═══════════════════════════════════════════════╝" -ForegroundColor $(if ($totalErrores -eq 0) { "Green" } else { "Red" })
Write-Host ""
Write-Host "  Reportes guardados en: $ReportDir" -ForegroundColor Cyan
Write-Host "  - newman-results.xml  (JUnit — importar en Jenkins/CI)" -ForegroundColor White
Write-Host "  - newman-report.html  (HTML visual)" -ForegroundColor White
Write-Host "  - k6-smoke.json       (métricas smoke test)" -ForegroundColor White
Write-Host "  - k6-load.json        (métricas carga — todos los controllers)" -ForegroundColor White
if ($Stress) {
    Write-Host "  - k6-stress.json      (métricas estrés)" -ForegroundColor White
}
Write-Host ""

exit $totalErrores
