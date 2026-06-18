import http from 'k6/http';
import { sleep, check } from 'k6';

export const options = {
    stages: [
        { duration: '10s', target: 5 },
        { duration: '20s', target: 10 },
        { duration: '10s', target: 0 },
    ],
    thresholds: {
        http_req_failed: ['rate<0.05'],
        http_req_duration: ['p(95)<1000'],
    },
};

const BASE_URL = 'http://localhost:8080';
const headers = { 'Content-Type': 'application/json' };

export default function () {
    // GET listado de ventas
    const listRes = http.get(`${BASE_URL}/api/ventas`);
    check(listRes, {
        'GET /api/ventas status 200': (r) => r.status === 200,
        'respuesta es array': (r) => Array.isArray(JSON.parse(r.body)),
    });

    // POST registrar venta (producto ID 1 debe existir en DB)
    const venta = JSON.stringify({
        cantidad: 1,
        producto: { id: 1 },
        usuario: { idUsuario: 1 },
    });
    const postRes = http.post(`${BASE_URL}/api/ventas`, venta, { headers });
    check(postRes, {
        'POST /api/ventas status 200 o 400': (r) => r.status === 200 || r.status === 400,
    });

    sleep(1);
}
