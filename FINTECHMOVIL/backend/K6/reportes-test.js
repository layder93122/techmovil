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
        http_req_duration: ['p(95)<1500'],
    },
};

const BASE_URL = 'http://localhost:8080';

export default function () {
    const endpoints = [
        '/api/reportes/diario',
        '/api/reportes/semanal',
        '/api/reportes/mensual',
        '/api/reportes/anual',
    ];

    for (const endpoint of endpoints) {
        const res = http.get(`${BASE_URL}${endpoint}`);
        check(res, {
            [`GET ${endpoint} status 200`]: (r) => r.status === 200,
            [`GET ${endpoint} es array`]: (r) => {
                try { return Array.isArray(JSON.parse(r.body)); } catch { return false; }
            },
        });
    }

    sleep(1);
}
