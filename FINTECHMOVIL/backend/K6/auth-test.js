import http from 'k6/http';
import { sleep, check } from 'k6';

export const options = {
    stages: [
        { duration: '10s', target: 10 },
        { duration: '20s', target: 20 },
        { duration: '10s', target: 0 },
    ],
    thresholds: {
        http_req_failed: ['rate<0.05'],
        http_req_duration: ['p(95)<500'],
    },
};

const BASE_URL = 'http://localhost:8080';

export default function () {
    const payload = JSON.stringify({ username: 'admin', password: 'admin123' });
    const headers = { 'Content-Type': 'application/json' };

    // Login exitoso
    const loginOk = http.post(`${BASE_URL}/api/auth/login`, payload, { headers });
    check(loginOk, {
        'login exitoso status 200': (r) => r.status === 200,
        'token presente': (r) => JSON.parse(r.body).token !== undefined,
    });

    // Login con credenciales incorrectas
    const badPayload = JSON.stringify({ username: 'wrong', password: 'wrong' });
    const loginFail = http.post(`${BASE_URL}/api/auth/login`, badPayload, { headers });
    check(loginFail, {
        'login fallido status 401': (r) => r.status === 401,
    });

    sleep(1);
}
