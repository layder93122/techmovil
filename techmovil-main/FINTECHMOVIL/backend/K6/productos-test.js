import http from 'k6/http';
import { sleep, check } from 'k6';

export const options = {
    stages: [
        { duration: '10s', target: 10 },
        { duration: '30s', target: 30 },
        { duration: '10s', target: 0 },
    ],
    thresholds: {
        http_req_failed: ['rate<0.05'],
        http_req_duration: ['p(95)<800'],
    },
};

const BASE_URL = 'http://localhost:8080';
const headers = { 'Content-Type': 'application/json' };

export default function () {
    // GET catalogo de productos
    const listRes = http.get(`${BASE_URL}/api/productos`);
    check(listRes, {
        'GET /api/productos status 200': (r) => r.status === 200,
        'respuesta es array': (r) => Array.isArray(JSON.parse(r.body)),
    });

    // GET alertas de stock bajo
    const alertasRes = http.get(`${BASE_URL}/api/productos/alertas`);
    check(alertasRes, {
        'GET /api/productos/alertas status 200': (r) => r.status === 200,
    });

    // POST nuevo producto
    const producto = JSON.stringify({
        marca: 'Samsung',
        modelo: `Galaxy-K6-${__VU}`,
        precio: 899.99,
        stock: 20,
        stockMinimo: 3,
    });
    const postRes = http.post(`${BASE_URL}/api/productos`, producto, { headers });
    check(postRes, {
        'POST /api/productos status 200': (r) => r.status === 200,
    });

    sleep(1);
}
