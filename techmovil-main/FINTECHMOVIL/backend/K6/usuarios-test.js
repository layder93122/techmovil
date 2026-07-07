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
        http_req_duration: ['p(95)<800'],
    },
};

const BASE_URL = 'http://localhost:8080';
const headers = { 'Content-Type': 'application/json' };

export default function () {
    // GET listado de usuarios
    const listRes = http.get(`${BASE_URL}/api/usuarios`);
    check(listRes, {
        'GET /api/usuarios status 200': (r) => r.status === 200,
        'respuesta es array': (r) => Array.isArray(JSON.parse(r.body)),
    });

    // POST crear usuario
    const usuario = JSON.stringify({
        nombre: `Usuario K6 ${__VU}`,
        username: `k6user${__VU}${Date.now()}`,
        password: 'password123',
        rol: 'VENDEDOR',
    });
    const postRes = http.post(`${BASE_URL}/api/usuarios`, usuario, { headers });
    check(postRes, {
        'POST /api/usuarios status 200': (r) => r.status === 200,
    });

    sleep(1);
}
