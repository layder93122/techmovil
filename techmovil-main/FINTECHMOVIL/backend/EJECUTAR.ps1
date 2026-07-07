# ================================================================
#  TechMovil - Un solo comando
#  Uso: cd PROJ && .\EJECUTAR.ps1
# ================================================================

$SonarUrl          = "http://localhost:9000"
$SonarToken        = "sqa_1efa256077a964dec20556df0ae035b5c7f8b527"
$SonarTokenFront   = "sqa_200fd7c4c2d5b1cd8c00124db83f9b1e03736699"
$SonarUser         = "admin"
$SonarPass         = "admin"
$SonarKey          = "fintechmovil"
$SonarKeyFront     = "fintechmovil-frontend"
$GateName          = "TechMovil-Cobertura-OK"

Write-Host ""
Write-Host "==========================================" -ForegroundColor Cyan
Write-Host "   TECHMOVIL -- Pipeline Automatico      " -ForegroundColor Cyan
Write-Host "==========================================" -ForegroundColor Cyan

Set-Location $PSScriptRoot

function Call-Sonar([string]$Path, [string]$Method="GET", [string]$Body="") {
    $cred = "Basic " + [Convert]::ToBase64String([Text.Encoding]::ASCII.GetBytes("${SonarToken}:"))
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
Write-Host "[1/4] Configurando Quality Gate..." -ForegroundColor Yellow

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
    Call-Sonar "/api/qualitygates/select" "POST" "gateId=$gid&projectKey=$SonarKey" | Out-Null
    Call-Sonar "/api/qualitygates/select" "POST" "gateId=$gid&projectKey=$SonarKeyFront" | Out-Null
    Write-Host "   [OK] Quality Gate '$GateName' asignado a backend y frontend" -ForegroundColor Green
}

Write-Host ""
Write-Host "[2/4] Tests + cobertura JaCoCo (~35 seg)..." -ForegroundColor Yellow
Write-Host ""

& .\mvnw.cmd clean verify --no-transfer-progress

if ($LASTEXITCODE -ne 0) {
    Write-Host "ERROR: Tests fallaron." -ForegroundColor Red
    exit 1
}

Write-Host ""
Write-Host "   [OK] Tests PASARON - Cobertura >= 80%" -ForegroundColor Green

Write-Host ""
Write-Host "[3/4] Backend - enviando a SonarQube..." -ForegroundColor Yellow
Write-Host ""

& .\mvnw.cmd sonar:sonar `
    "-Dsonar.projectKey=$SonarKey" `
    "-Dsonar.host.url=$SonarUrl" `
    "-Dsonar.token=$SonarToken" `
    "--no-transfer-progress"

if ($LASTEXITCODE -ne 0) {
    Write-Host "ERROR: Backend SonarQube falló." -ForegroundColor Red
    exit 1
}

Write-Host ""
Write-Host "   [OK] Backend analizado en SonarQube" -ForegroundColor Green

Write-Host ""
Write-Host "[4/4] Frontend — build + SonarQube..." -ForegroundColor Yellow
Write-Host ""

Push-Location ..\frontend

if (-not (Test-Path "node_modules")) {
    Write-Host "   Instalando dependencias npm..." -ForegroundColor DarkYellow
    & npm install --silent
}

& npx sonar-scanner `
    "-Dsonar.host.url=$SonarUrl" `
    "-Dsonar.token=$SonarTokenFront" `
    "-Dsonar.projectKey=$SonarKeyFront" `
    "-Dsonar.projectName=FinTechMovil Frontend" `
    "-Dsonar.sources=src" `
    "-Dsonar.exclusions=node_modules/**,dist/**" `
    "-Dsonar.javascript.node.maxspace=512"

$exitFront = $LASTEXITCODE
Pop-Location

if ($exitFront -ne 0) {
    Write-Host "ERROR: Frontend SonarQube falló." -ForegroundColor Red
    exit 1
}

Write-Host ""
Write-Host "   [OK] Frontend analizado en SonarQube" -ForegroundColor Green

Write-Host ""
Write-Host "==========================================" -ForegroundColor Green
Write-Host "   BUILD SUCCESS - BACKEND + FRONTEND    " -ForegroundColor Green
Write-Host "==========================================" -ForegroundColor Green
Write-Host ""
Write-Host "   Backend:  $SonarUrl/dashboard?id=$SonarKey" -ForegroundColor Cyan
Write-Host "   Frontend: $SonarUrl/dashboard?id=$SonarKeyFront" -ForegroundColor Cyan
Write-Host ""
