# =============================================================
#  TechMovil — Levanta todo y dispara el pipeline Jenkins
#  Uso: cd FINTECHMOVIL/backend && .\reiniciar.ps1
# =============================================================
Set-Location $PSScriptRoot
$ErrorActionPreference = "SilentlyContinue"

function log($msg, $color="Cyan") { Write-Host "  $msg" -ForegroundColor $color }
function ok($msg)  { Write-Host "  [OK] $msg" -ForegroundColor Green }
function err($msg) { Write-Host "  [!!] $msg" -ForegroundColor Red }

Write-Host ""
Write-Host "============================================" -ForegroundColor Cyan
Write-Host "   TECHMOVIL - Iniciando servicios CI/CD  " -ForegroundColor Cyan
Write-Host "============================================" -ForegroundColor Cyan
Write-Host ""

# ── 1. REINICIAR CONTENEDORES ──────────────────────────────────
log "Reiniciando SonarQube..."
docker restart sonarqube | Out-Null
Start-Sleep -Seconds 3
ok "SonarQube reiniciado (iniciando en background)"

# ── 2. ESPERAR QUE SONARQUBE ESTE UP ──────────────────────────
log "Esperando SonarQube en http://localhost:9000 ..."
$maxWait = 180; $elapsed = 0; $sonarOk = $false
while ($elapsed -lt $maxWait) {
    Start-Sleep -Seconds 8
    $elapsed += 8
    try {
        $r = Invoke-RestMethod "http://localhost:9000/api/system/status" -TimeoutSec 5 -EA Stop
        if ($r.status -eq "UP") { $sonarOk = $true; break }
        log "SonarQube: $($r.status) (${elapsed}s)..."
    } catch { log "Esperando... ${elapsed}s" }
}

if ($sonarOk) { ok "SonarQube UP en http://localhost:9000" }
else { err "SonarQube no respondio en $maxWait s — revisa 'docker logs sonarqube'"; exit 1 }

# ── 3. OBTENER PASSWORD DE JENKINS ────────────────────────────
log "Buscando credenciales de Jenkins..."
$jenkinsUrl  = "http://localhost:8081"
$initPassFile = docker exec jenkins cat /var/jenkins_home/secrets/initialAdminPassword 2>&1
if ($initPassFile -match "^[a-f0-9]{32}$") {
    $JUSER = "admin"; $JPASS = $initPassFile.Trim()
    log "Password inicial encontrada"
} else {
    # Ya fue configurado — intenta con credenciales comunes
    foreach ($cred in @("admin:admin","admin:admin123","admin:techmovil")) {
        $pair = [Convert]::ToBase64String([Text.Encoding]::ASCII.GetBytes($cred))
        $headers = @{ Authorization = "Basic $pair" }
        $test = Invoke-RestMethod "$jenkinsUrl/api/json" -Headers $headers -EA SilentlyContinue
        if ($test) {
            $parts = $cred -split ":"
            $JUSER = $parts[0]; $JPASS = $parts[1]
            log "Credenciales encontradas: $JUSER / $JPASS"
            break
        }
    }
}

if (-not $JUSER) {
    err "No se pudo autenticar en Jenkins."
    err "Abre http://localhost:8081 y configura el usuario manualmente."
    exit 1
}

$pair    = [Convert]::ToBase64String([Text.Encoding]::ASCII.GetBytes("${JUSER}:${JPASS}"))
$headers = @{ Authorization = "Basic $pair"; "Content-Type" = "application/xml" }

# ── 4. CRUMB CSRF ─────────────────────────────────────────────
$crumbData = Invoke-RestMethod "$jenkinsUrl/crumbIssuer/api/json" -Headers $headers -EA SilentlyContinue
if ($crumbData) { $headers[$crumbData.crumbRequestField] = $crumbData.crumb }

# ── 5. CREAR / ACTUALIZAR JOB ─────────────────────────────────
$jobName = "techmovil"
$jobXml  = @"
<?xml version='1.1' encoding='UTF-8'?>
<flow-definition plugin="workflow-job">
  <description>TechMovil CI/CD Pipeline — Backend + Frontend + SonarQube</description>
  <keepDependencies>false</keepDependencies>
  <definition class="org.jenkinsci.plugins.workflow.cps.CpsScmFlowDefinition" plugin="workflow-cps">
    <scm class="hudson.plugins.git.GitSCM" plugin="git">
      <configVersion>2</configVersion>
      <userRemoteConfigs>
        <hudson.plugins.git.UserRemoteConfig>
          <url>https://github.com/layder93122/techmovil.git</url>
        </hudson.plugins.git.UserRemoteConfig>
      </userRemoteConfigs>
      <branches>
        <hudson.plugins.git.BranchSpec><name>*/main</name></hudson.plugins.git.BranchSpec>
      </branches>
      <doGenerateSubmoduleConfigurations>false</doGenerateSubmoduleConfigurations>
    </scm>
    <scriptPath>FINTECHMOVIL/backend/Jenkinsfile</scriptPath>
    <lightweight>true</lightweight>
  </definition>
  <triggers/>
  <disabled>false</disabled>
</flow-definition>
"@

$exists = Invoke-RestMethod "$jenkinsUrl/job/$jobName/api/json" -Headers $headers -EA SilentlyContinue
if ($exists) {
    log "Job '$jobName' ya existe — actualizando config..."
    Invoke-RestMethod "$jenkinsUrl/job/$jobName/config.xml" -Method POST -Headers $headers -Body $jobXml -EA Stop | Out-Null
    ok "Job actualizado"
} else {
    log "Creando job '$jobName'..."
    Invoke-RestMethod "$jenkinsUrl/createItem?name=$jobName" -Method POST -Headers $headers -Body $jobXml -EA Stop | Out-Null
    ok "Job creado"
}

# ── 6. DISPARAR BUILD ─────────────────────────────────────────
log "Disparando pipeline..."
Invoke-RestMethod "$jenkinsUrl/job/$jobName/build" -Method POST -Headers $headers -EA Stop | Out-Null
Start-Sleep -Seconds 3

$buildInfo = Invoke-RestMethod "$jenkinsUrl/job/$jobName/lastBuild/api/json" -Headers $headers -EA SilentlyContinue
$buildNum  = if ($buildInfo) { $buildInfo.number } else { "1" }

ok "Pipeline #$buildNum disparado"

Write-Host ""
Write-Host "============================================" -ForegroundColor Green
Write-Host "   TODO LISTO — abre estos links:          " -ForegroundColor Green
Write-Host "============================================" -ForegroundColor Green
Write-Host ""
Write-Host "  Jenkins pipeline:" -ForegroundColor White
Write-Host "  http://localhost:8081/job/$jobName/$buildNum/console" -ForegroundColor Yellow
Write-Host ""
Write-Host "  SonarQube backend:" -ForegroundColor White
Write-Host "  http://localhost:9000/dashboard?id=fintechmovil" -ForegroundColor Yellow
Write-Host ""
Write-Host "  SonarQube frontend:" -ForegroundColor White
Write-Host "  http://localhost:9000/dashboard?id=fintechmovil-frontend" -ForegroundColor Yellow
Write-Host ""
Write-Host "  El pipeline tarda ~5-8 minutos en terminar." -ForegroundColor Gray
Write-Host ""
