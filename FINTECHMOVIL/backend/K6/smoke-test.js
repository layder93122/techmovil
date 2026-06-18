/**
 * Smoke test: verifica que todos los endpoints principales responden correctamente.
 * Carga mínima — solo valida disponibilidad del sistema.
 */
import http from 'k6/http';
import { sleep, check } from 'k6';

export const options = { vus: 2, duration: '30s' };

const BASE_URL = 'http://localhost:8080';
const JSON_HEADERS = { 'Content-Type': 'application/json' };

export default function () {
    // Auth
    const auth = http.post(`${BASE_URL}/api/auth/login`,
        JSON.stringify({ username: 'admin', password: 'admin123' }),
        { headers: JSON_HEADERS });
    check(auth, { '[smoke] auth login 200': (r) => r.status === 200 });

    // Productos
    check(http.get(`${BASE_URL}/api/productos`),
        { '[smoke] productos 200': (r) => r.status === 200 });
    check(http.get(`${BASE_URL}/api/productos/alertas`),
        { '[smoke] alertas 200': (r) => r.status === 200 });

    // Ventas
    check(http.get(`${BASE_URL}/api/ventas`),
        { '[smoke] ventas 200': (r) => r.status === 200 });

    // Usuarios
    check(http.get(`${BASE_URL}/api/usuarios`),
        { '[smoke] usuarios 200': (r) => r.status === 200 });

    // Reportes
    check(http.get(`${BASE_URL}/api/reportes/diario`),
        { '[smoke] reportes diario 200': (r) => r.status === 200 });

    sleep(1);
}
