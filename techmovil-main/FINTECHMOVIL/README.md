<div align="center">

# 💳 FinTechmovil ERP

### Sistema de Gestión de Inventario y Ventas de Celulares

![Build](https://img.shields.io/badge/build-passing-brightgreen?style=flat-square)
![Coverage](https://img.shields.io/badge/coverage-85.4%25-green?style=flat-square)
![Quality Gate](https://img.shields.io/badge/Quality%20Gate-A-brightgreen?style=flat-square)
![Java](https://img.shields.io/badge/Java-17-orange?style=flat-square)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.5-brightgreen?style=flat-square)
![React](https://img.shields.io/badge/React-19-61DAFB?style=flat-square)
![Tests](https://img.shields.io/badge/tests-196%20passed-brightgreen?style=flat-square)

**🌐 Demo → [fintechmovil.vercel.app](https://fintechmovil.vercel.app)**

</div>

---

## 🚀 Correr localmente

```bash
# Backend (ventana 1)
cd backend
./mvnw spring-boot:run
# ✅ http://localhost:8080

# Frontend (ventana 2)
cd frontend
npm install && npm run dev
# ✅ http://localhost:5173  |  admin / 1234
```

## 🌐 Deploy en la nube

| Servicio | URL | Credenciales |
|---------|-----|-------------|
| Frontend | https://fintechmovil.vercel.app | admin / 1234 |
| Backend API | https://fintechmovil.up.railway.app/api | — |
| Swagger | https://fintechmovil.up.railway.app/swagger-ui/index.html | — |

## 📦 Variables de entorno Railway (backend)

```
MYSQLURL       = (Railway lo inyecta automático)
MYSQLUSER      = (Railway lo inyecta automático)
MYSQLPASSWORD  = (Railway lo inyecta automático)
JWT_SECRET     = fintechmovil-sistema-inventario-2026-ok!
ADMIN_USER     = admin
ADMIN_PASS     = admin123
CORS_ORIGINS   = https://fintechmovil.vercel.app
```

## 📊 Calidad

| Métrica | Resultado |
|---------|-----------|
| Tests JUnit 5 | 196 / 196 ✅ |
| Cobertura JaCoCo | 85.4% ✅ |
| Security Rating | A ✅ |
| OWASP Top 10 | Mitigado ✅ |

---
Universidad Nacional del Altiplano · Juliaca, Perú 🇵🇪
