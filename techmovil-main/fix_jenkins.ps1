# ================================================================
#  TechMovil — Fix completo: SonarQube + Jenkins pipeline
#  Corre desde: D:\FINTECHMOVIL_TODO_FINAL\
# ================================================================
$BACKEND = "D:\FINTECHMOVIL_TODO_FINAL\FINTECHMOVIL\backend"
$JENKINS  = "http://localhost:8081"
$SONAR    = "http://localhost:9000"
$JUSER    = ""
$JPASS    = ""

function log($m, $c="Cyan")  { Write-Host "  $m" -ForegroundColor $c }
function ok($m)               { Write-Host "  [OK] $m" -ForegroundColor Green }
function err($m)              { Write-Host "  [!!] $m" -ForegroundColor Red }
function hdr($m)              { Write-Host "`n=== $m ===" -ForegroundColor Cyan }

hdr "1. REINICIANDO SONARQUBE"
log "Matando y recreando el contenedor..."
docker kill sonarqube 2>$null | Out-Null
docker rm   sonarqube 2>$null | Out-Null

Set-Location $BACKEND
docker compose up -d sonarqube 2>&1 | Select-Object -Last 5
ok "Contenedor nuevo creado con JVM opts correctos"

hdr "2. ESPERANDO SONARQUBE (hasta 3 min)"
$ok = $false
for ($i = 1; $i -le 22; $i++) {
    Start-Sleep -Seconds 8
    try {
        $s = (Invoke-RestMethod "$SONAR/api/system/status" -TimeoutSec 5 -EA Stop).status
        log "SonarQube: $s ($($i*8)s)"
        if ($s -eq "UP") { $ok = $true; break }
    } catch { log "Esperando... $($i*8)s" }
}
if (-not $ok) { err "SonarQube no arrancó — corre: docker logs sonarqube"; exit 1 }
ok "SonarQube UP en $SONAR"

hdr "3. CREDENCIALES JENKINS"
$initPass = docker exec jenkins cat /var/jenkins_home/secrets/initialAdminPassword 2>$null
if ($initPass -match "^[a-f0-9]{32}$") {
    $JUSER = "admin"; $JPASS = $initPass.Trim()
    log "Password inicial: $JPASS"
    log "Ve a $JENKINS y completa la configuracion inicial primero" "Yellow"
    log "Usuario sugerido: admin  Password: admin123"
    log "Cuando termines presiona ENTER aqui..."; Read-Host
    # Re-intentar con admin123
    $JPASS = "admin123"
} else {
    foreach ($c in @("admin:admin123","admin:admin","admin:techmovil","admin:password")) {
        $p = $c.Split(":"); $u=$p[0]; $pw=$p[1]
        $b = [Convert]::ToBase64String([Text.Encoding]::ASCII.GetBytes($c))
        $t = Invoke-RestMethod "$JENKINS/api/json" -Headers @{Authorization="Basic $b"} -EA SilentlyContinue
        if ($t) { $JUSER=$u; $JPASS=$pw; log "Credenciales: $JUSER / $JPASS"; break }
    }
}

if (-not $JUSER) {
    err "No pude autenticar en Jenkins."
    err "Ve a $JENKINS → crea usuario admin / admin123 → vuelve a correr."
    exit 1
}

$AUTH = [Convert]::ToBase64String([Text.Encoding]::ASCII.GetBytes("${JUSER}:${JPASS}"))
$HDR  = @{ Authorization = "Basic $AUTH"; "Content-Type" = "application/xml" }

# CSRF crumb
$crumb = Invoke-RestMethod "$JENKINS/crumbIssuer/api/json" -Headers $HDR -EA SilentlyContinue
if ($crumb) { $HDR[$crumb.crumbRequestField] = $crumb.crumb }

hdr "4. CREANDO JOB JENKINS"
$JOB  = "techmovil"
$XML  = @"
<?xml version='1.1' encoding='UTF-8'?>
<flow-definition plugin="workflow-job">
  <description>TechMovil CI/CD — Backend + SonarQube + Frontend</description>
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
    </scm>
    <scriptPath>FINTECHMOVIL/backend/Jenkinsfile</scriptPath>
    <lightweight>true</lightweight>
  </definition>
  <triggers/><disabled>false</disabled>
</flow-definition>
"@

$exists = Invoke-RestMethod "$JENKINS/job/$JOB/api/json" -Headers $HDR -EA SilentlyContinue
if ($exists) {
    Invoke-RestMethod "$JENKINS/job/$JOB/config.xml" -Method POST -Headers $HDR -Body $XML | Out-Null
    ok "Job '$JOB' actualizado"
} else {
    Invoke-RestMethod "$JENKINS/createItem?name=$JOB" -Method POST -Headers $HDR -Body $XML | Out-Null
    ok "Job '$JOB' creado"
}

hdr "5. DISPARANDO PIPELINE"
Invoke-RestMethod "$JENKINS/job/$JOB/build" -Method POST -Headers $HDR | Out-Null
Start-Sleep -Seconds 4
$num = (Invoke-RestMethod "$JENKINS/job/$JOB/lastBuild/api/json" -Headers $HDR -EA SilentlyContinue).number
ok "Build #$num iniciado"

Write-Host ""
Write-Host "==========================================" -ForegroundColor Green
Write-Host "  LISTO — Abre estos links en el browser  " -ForegroundColor Green
Write-Host "==========================================" -ForegroundColor Green
Write-Host ""
Write-Host "  Pipeline Jenkins:" -ForegroundColor White
Write-Host "  $JENKINS/job/$JOB/$num/console" -ForegroundColor Yellow
Write-Host ""
Write-Host "  SonarQube backend:" -ForegroundColor White
Write-Host "  $SONAR/dashboard?id=fintechmovil" -ForegroundColor Yellow
Write-Host ""
Write-Host "  SonarQube frontend:" -ForegroundColor White
Write-Host "  $SONAR/dashboard?id=fintechmovil-frontend" -ForegroundColor Yellow
Write-Host ""
Write-Host "  El pipeline tarda ~5-8 min. Primero sube el codigo a GitHub!" -ForegroundColor Gray
Write-Host ""
