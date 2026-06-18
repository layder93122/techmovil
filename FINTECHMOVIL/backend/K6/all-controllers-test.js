/**
 * Test integral que cubre todos los controllers del sistema FinTechMovil.
 * Ejecutar con: k6 run all-controllers-test.js
 */
import http from 'k6/http';
import { sleep, check, group } from 'k6';

export const options = {
    stages: [
        { duration: '15s', target: 10 },
        { duration: '30s', target: 20 },
        { duration: '15s', target: 0 },
    ],
    thresholds: {
        http_req_failed: ['rate<0.10'],
        http_req_duration: ['p(95)<2000'],
        'http_req_duration{controller:auth}': ['p(95)<500'],
        'http_req_duration{controller:productos}': ['p(95)<800'],
        'http_req_duration{controller:ventas}': ['p(95)<1000'],
        'http_req_duration{controller:facturas}': ['p(95)<2000'],
        'http_req_duration{controller:reportes}': ['p(95)<1500'],
        'http_req_duration{controller:usuarios}': ['p(95)<800'],
    },
};

const BASE_URL = 'http://localhost:8080';
const JSON_HEADERS = { 'Content-Type': 'application/json' };

export default function () {

    group('AuthControlador - POST /api/auth/login', () => {
        const res = http.post(
            `${BASE_URL}/api/auth/login`,
            JSON.stringify({ username: 'admin', password: 'admin123' }),
            { headers: JSON_HEADERS, tags: { controller: 'auth' } }
        );
        check(res, {
            'auth login 200': (r) => r.status === 200,
            'auth token presente': (r) => JSON.parse(r.body).token !== undefined,
        });
    });

    group('ProductoController - GET /api/productos', () => {
        const res = http.get(`${BASE_URL}/api/productos`, { tags: { controller: 'productos' } });
        check(res, { 'productos list 200': (r) => r.status === 200 });

        const alertas = http.get(`${BASE_URL}/api/productos/alertas`, { tags: { controller: 'productos' } });
        check(alertas, { 'productos alertas 200': (r) => r.status === 200 });
    });

    group('VentaController - GET /api/ventas', () => {
        const res = http.get(`${BASE_URL}/api/ventas`, { tags: { controller: 'ventas' } });
        check(res, { 'ventas list 200': (r) => r.status === 200 });
    });

    group('FacturaController - POST /api/facturas/emitir', () => {
        const res = http.post(
            `${BASE_URL}/api/facturas/emitir`,
            JSON.stringify({ cliente: 'Test', detalles: [] }),
            { headers: JSON_HEADERS, tags: { controller: 'facturas' } }
        );
        check(res, { 'facturas emitir 400 o 200': (r) => r.status === 400 || r.status === 200 });
    });

    group('ReporteController - GET /api/reportes/*', () => {
        ['diario', 'semanal', 'mensual', 'anual'].forEach((periodo) => {
            const res = http.get(`${BASE_URL}/api/reportes/${periodo}`, { tags: { controller: 'reportes' } });
            check(res, { [`reportes ${periodo} 200`]: (r) => r.status === 200 });
        });
    });

    group('UsuarioController - GET /api/usuarios', () => {
        const res = http.get(`${BASE_URL}/api/usuarios`, { tags: { controller: 'usuarios' } });
        check(res, { 'usuarios list 200': (r) => r.status === 200 });
    });

    sleep(1);
}
