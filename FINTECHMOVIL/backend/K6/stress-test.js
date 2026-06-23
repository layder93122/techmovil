/**
 * Stress Test — FinTechMovil API (todos los controllers)
 * Cubre: Auth, Productos, Ventas, Facturas, Reportes, Usuarios
 *
 * Características del servidor (Railway.app free tier):
 *   - 1 vCPU compartida, 512 MB RAM
 *   - Latencia base ~100-200ms (servidor en USA)
 *   - Conexiones MySQL: pool de 10
 *
 * Estrategia: rampa suave → carga sostenida → pico → enfriamiento
 * Ejecutar: k6 run stress-test.js
 */

import http from 'k6/http';
import { sleep, check, group } from 'k6';
import { Counter, Rate, Trend } from 'k6/metrics';

// ── Métricas personalizadas ──────────────────────────────────────────────────
const errores        = new Counter('errores_totales');
const tasaExito      = new Rate('tasa_exito');
const duracionAuth   = new Trend('duracion_auth_ms');
const duracionLectura = new Trend('duracion_lectura_ms');
const duracionEscritura = new Trend('duracion_escritura_ms');

// ── Configuración del escenario ──────────────────────────────────────────────
export const options = {
    stages: [
        // Calentamiento (servidor Railway arranca conexiones, JIT compila)
        { duration: '30s', target: 5  },
        // Carga normal (simula horario comercial)
        { duration: '60s', target: 15 },
        // Carga alta (pico de uso, ej. campaña de ventas)
        { duration: '60s', target: 30 },
        // Estrés (límite del servidor)
        { duration: '30s', target: 50 },
        // Enfriamiento
        { duration: '30s', target: 0  },
    ],
    thresholds: {
        // Disponibilidad mínima requerida
        http_req_failed: ['rate<0.05'],             // < 5% de errores HTTP

        // SLAs por módulo (ajustados a servidor Railway + latencia red)
        'http_req_duration{controller:auth}':      ['p(95)<800'],
        'http_req_duration{controller:productos}': ['p(95)<1000'],
        'http_req_duration{controller:ventas}':    ['p(95)<1200'],
        'http_req_duration{controller:facturas}':  ['p(95)<2000'],
        'http_req_duration{controller:reportes}':  ['p(95)<2000'],
        'http_req_duration{controller:usuarios}':  ['p(95)<1000'],

        // Métricas globales
        http_req_duration: ['p(90)<2000', 'p(99)<5000'],
        tasa_exito:        ['rate>0.95'],
    },
};

const BASE_URL     = __ENV.BASE_URL || 'http://localhost:8080';
const ADMIN_USER   = __ENV.ADMIN_USER || 'admin';
const ADMIN_PASS   = __ENV.ADMIN_PASS || 'admin123';
const JSON_HEADERS = { 'Content-Type': 'application/json' };

// ── Setup: obtener token una sola vez por VU ─────────────────────────────────
export function setup() {
    const res = http.post(
        `${BASE_URL}/api/auth/login`,
        JSON.stringify({ username: ADMIN_USER, password: ADMIN_PASS }),
        { headers: JSON_HEADERS }
    );
    if (res.status === 200) {
        return { token: res.json('token'), productoId: 1 };
    }
    return { token: null, productoId: 1 };
}

// ── Escenario principal ──────────────────────────────────────────────────────
export default function (data) {
    const authHeader = data.token
        ? { ...JSON_HEADERS, Authorization: `Bearer ${data.token}` }
        : JSON_HEADERS;

    // ── 1. AUTENTICACIÓN ─────────────────────────────────────────────────────
    group('Auth — POST /api/auth/login', () => {
        const start = Date.now();
        const res = http.post(
            `${BASE_URL}/api/auth/login`,
            JSON.stringify({ username: ADMIN_USER, password: ADMIN_PASS }),
            { headers: JSON_HEADERS, tags: { controller: 'auth' } }
        );
        duracionAuth.add(Date.now() - start);
        const ok = check(res, {
            '[auth] status 200':        (r) => r.status === 200,
            '[auth] token en respuesta': (r) => r.json('token') !== undefined,
        });
        if (!ok) errores.add(1);
        tasaExito.add(ok);
    });

    // ── 2. PRODUCTOS — operaciones de lectura (60% del tráfico real) ─────────
    group('Productos — GET listado y detalle', () => {
        const start = Date.now();

        const listRes = http.get(
            `${BASE_URL}/api/productos`,
            { tags: { controller: 'productos' } }
        );
        duracionLectura.add(Date.now() - start);
        const okList = check(listRes, {
            '[productos] listado 200':       (r) => r.status === 200,
            '[productos] es array':          (r) => Array.isArray(r.json()),
            '[productos] latencia < 1000ms': (r) => r.timings.duration < 1000,
        });
        if (!okList) errores.add(1);
        tasaExito.add(okList);

        // Detalle de producto existente
        const detRes = http.get(
            `${BASE_URL}/api/productos/${data.productoId}`,
            { tags: { controller: 'productos' } }
        );
        const okDet = check(detRes, {
            '[productos] detalle 200 o 404': (r) => [200, 404].includes(r.status),
        });
        if (!okDet) errores.add(1);

        // Alertas de stock bajo
        const alertRes = http.get(
            `${BASE_URL}/api/productos/alertas`,
            { tags: { controller: 'productos' } }
        );
        check(alertRes, {
            '[productos] alertas 200': (r) => r.status === 200,
        });
    });

    // ── 3. VENTAS ────────────────────────────────────────────────────────────
    group('Ventas — GET y POST', () => {
        const start = Date.now();
        const getRes = http.get(
            `${BASE_URL}/api/ventas`,
            { tags: { controller: 'ventas' } }
        );
        duracionLectura.add(Date.now() - start);
        const ok = check(getRes, {
            '[ventas] listado 200':  (r) => r.status === 200,
            '[ventas] es array':     (r) => Array.isArray(r.json()),
        });
        if (!ok) errores.add(1);
        tasaExito.add(ok);

        // POST: sólo 30% de VUs registran venta (simula carga mixta lectura/escritura)
        if (__VU % 3 === 0) {
            const startW = Date.now();
            const postRes = http.post(
                `${BASE_URL}/api/ventas`,
                JSON.stringify({
                    cantidad: 1,
                    producto: { id: data.productoId },
                    usuario: { idUsuario: 1 },
                }),
                { headers: authHeader, tags: { controller: 'ventas' } }
            );
            duracionEscritura.add(Date.now() - startW);
            check(postRes, {
                '[ventas] POST 200 o 400': (r) => [200, 400].includes(r.status),
            });
        }
    });

    // ── 4. FACTURAS ──────────────────────────────────────────────────────────
    group('Facturas — GET y POST emitir', () => {
        const getRes = http.get(
            `${BASE_URL}/api/facturas`,
            { tags: { controller: 'facturas' } }
        );
        check(getRes, {
            '[facturas] listado 200': (r) => r.status === 200,
        });

        // POST emitir (sólo 20% de VUs — operación costosa)
        if (__VU % 5 === 0) {
            const startW = Date.now();
            const postRes = http.post(
                `${BASE_URL}/api/facturas/emitir`,
                JSON.stringify({
                    cliente: `Cliente Stress ${__VU}`,
                    numeroFactura: `FAC-STR-${__VU}-${Date.now()}`,
                    detalles: [{ producto: { id: data.productoId }, cantidad: 1 }],
                    detallePago: { metodoPago: 'EFECTIVO', estadoPago: 'APROBADO' },
                }),
                { headers: authHeader, tags: { controller: 'facturas' } }
            );
            duracionEscritura.add(Date.now() - startW);
            check(postRes, {
                '[facturas] emitir 200 o 400': (r) => [200, 400].includes(r.status),
            });
        }
    });

    // ── 5. REPORTES ──────────────────────────────────────────────────────────
    group('Reportes — todos los periodos', () => {
        const periodos = ['diario', 'semanal', 'mensual', 'anual'];
        // Solo 1 de 4 VUs consulta reportes (son costosos en DB)
        const periodo = periodos[__VU % periodos.length];
        const start = Date.now();
        const res = http.get(
            `${BASE_URL}/api/reportes/${periodo}`,
            { tags: { controller: 'reportes' } }
        );
        duracionLectura.add(Date.now() - start);
        check(res, {
            [`[reportes] ${periodo} 200`]: (r) => r.status === 200,
            '[reportes] es array':         (r) => Array.isArray(r.json()),
        });
    });

    // ── 6. USUARIOS ──────────────────────────────────────────────────────────
    group('Usuarios — GET listado', () => {
        const start = Date.now();
        const res = http.get(
            `${BASE_URL}/api/usuarios`,
            { tags: { controller: 'usuarios' } }
        );
        duracionLectura.add(Date.now() - start);
        const ok = check(res, {
            '[usuarios] listado 200': (r) => r.status === 200,
            '[usuarios] es array':    (r) => Array.isArray(r.json()),
        });
        if (!ok) errores.add(1);
        tasaExito.add(ok);
    });

    sleep(Math.random() * 2 + 1); // Think time: 1-3 segundos (patrón realista)
}

// ── Teardown: resumen de resultados ─────────────────────────────────────────
export function teardown(data) {
    console.log('=== RESULTADO STRESS TEST FinTechMovil ===');
    console.log(`Servidor probado: ${BASE_URL}`);
    console.log('Escenario: 5 + 15 + 30 + 50 VUs — 210s total');
    console.log('Controllers cubiertos: Auth, Productos, Ventas, Facturas, Reportes, Usuarios');
}
