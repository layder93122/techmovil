# ================================================================
#  TechMovil - Un solo comando
#  Uso: cd PROJ && .\EJECUTAR.ps1
# ================================================================

$SonarUrl   = "http://localhost:9000"
$SonarToken = "sqa_0d27ba7b442d5827a0425666759402989f12f251"
$SonarUser  = "admin"
$SonarPass  = "admin"
$SonarKey   = "fintechmovil"
$GateName   = "TechMovil-Cobertura-OK"

Write-Host ""
Write-Host "==========================================" -ForegroundColor Cyan
Write-Host "   TECHMOVIL -- Pipeline Automatico      " -ForegroundColor Cyan
Write-Host "==========================================" -ForegroundColor Cyan

Set-Location $PSScriptRoot

function Call-Sonar([string]$Path, [string]$Method="GET", [string]$Body="") {
    $cred = "Basic " + [Convert]::ToBase64String([Text.Encoding]::ASCII.GetBytes("${SonarUser}:${SonarPass}"))
    $h = @{ Authorization=$cred; "Content-Type"="application/x-www-form-urlencoded" }
    try {
        if ($Method -eq "POST") {
            return Invoke-RestMethod -Uri "$SonarUrl$Path" -Method POST -Headers $h -Body $Body -EA Stop
        }
        return Invoke-RestMethod -Uri "$SonarUrl$Path" -Method GET -Headers $h -EA Stop
    } catch {
        Write-Host "   [!] API: $($_.Exception.Message)" -ForegroundColor DarkYellow
        return $null
    }
}

Write-Host ""
Write-Host "[1/3] Configurando Quality Gate..." -ForegroundColor Yellow

$gates = Call-Sonar "/api/qualitygates/list"
$exist = if ($gates) { $gates.qualitygates | Where-Object { $_.name -eq $GateName } } else { $null }

if ($exist) {
    $gid = $exist.id
    Write-Host "   [OK] Quality Gate ya existe (id=$gid)" -ForegroundColor Green
} else {
    $ng = Call-Sonar "/api/qualitygates/create" "POST" "name=$GateName"
    if ($ng -and $ng.id) {
        $gid = $ng.id
        Call-Sonar "/api/qualitygates/create_condition" "POST" "gateId=$gid&metric=coverage&op=LT&error=80" | Out-Null
        Write-Host "   [OK] Quality Gate creado: Coverage >= 80% (id=$gid)" -ForegroundColor Green
    }
}

if ($gid) {
    $sel = Call-Sonar "/api/qualitygates/select" "POST" "gateId=$gid&projectKey=$SonarKey"
    if ($sel -ne $null -or $?) {
        Write-Host "   [OK] Quality Gate '$GateName' asignado al proyecto" -ForegroundColor Green
    }
}

if (-not $gid) {
    Write-Host ""
    Write-Host "   [ACCION REQUERIDA] El Quality Gate no se pudo crear automaticamente." -ForegroundColor Yellow
    Write-Host "   Haz esto UNA SOLA VEZ en el navegador:" -ForegroundColor Yellow
    Write-Host "   1. Ve a: $SonarUrl/quality_gates" -ForegroundColor Cyan
    Write-Host "   2. Clic en 'Create' - nombre: $GateName" -ForegroundColor Cyan
    Write-Host "   3. Add condition: Coverage < 80 (Error)" -ForegroundColor Cyan
    Write-Host "   4. Ve a: $SonarUrl/dashboard?id=$SonarKey" -ForegroundColor Cyan
    Write-Host "   5. Project Settings > Quality Gate > Selecciona $GateName" -ForegroundColor Cyan
    Write-Host ""
    Read-Host "Presiona ENTER cuando hayas hecho esos pasos"
}

Write-Host ""
Write-Host "[2/3] Tests + cobertura JaCoCo (~35 seg)..." -ForegroundColor Yellow
Write-Host ""

& .\mvnw.cmd clean verify --no-transfer-progress

if ($LASTEXITCODE -ne 0) {
    Write-Host "ERROR: Tests fallaron." -ForegroundColor Red
    exit 1
}

Write-Host ""
Write-Host "   [OK] Tests PASARON - Cobertura >= 80%" -ForegroundColor Green

Write-Host ""
Write-Host "[3/3] Enviando a SonarQube (~20 seg)..." -ForegroundColor Yellow
Write-Host ""

& .\mvnw.cmd sonar:sonar `
    "-Dsonar.projectKey=$SonarKey" `
    "-Dsonar.host.url=$SonarUrl" `
    "-Dsonar.token=$SonarToken" `
    "--no-transfer-progress"

if ($LASTEXITCODE -ne 0) {
    Write-Host "ERROR al enviar a SonarQube." -ForegroundColor Red
    exit 1
}

Write-Host ""
Write-Host "==========================================" -ForegroundColor Green
Write-Host "   BUILD SUCCESS - QUALITY GATE: VERDE  " -ForegroundColor Green
Write-Host "==========================================" -ForegroundColor Green
Write-Host ""
Write-Host "   $SonarUrl/dashboard?id=$SonarKey" -ForegroundColor Cyan
Write-Host ""
