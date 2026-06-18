import http from 'k6/http';
import { sleep, check } from 'k6';

export const options = {
    stages: [
        { duration: '10s', target: 5 },
        { duration: '20s', target: 10 },
        { duration: '10s', target: 0 },
    ],
    thresholds: {
        http_req_failed: ['rate<0.10'],
        http_req_duration: ['p(95)<2000'],
    },
};

const BASE_URL = 'http://localhost:8080';
const headers = { 'Content-Type': 'application/json' };

export default function () {
    // POST emitir factura (carrito vacío → 400 esperado)
    const facturaVacia = JSON.stringify({
        cliente: `Cliente-K6-${__VU}`,
        detalles: [],
        detallePago: { metodoPago: 'EFECTIVO' },
    });
    const emptyRes = http.post(`${BASE_URL}/api/facturas/emitir`, facturaVacia, { headers });
    check(emptyRes, {
        'POST /api/facturas/emitir carrito vacio status 400': (r) => r.status === 400,
    });

    // POST emitir factura con detalle (producto ID 1 debe existir)
    const facturaConDetalle = JSON.stringify({
        cliente: `Cliente-K6-${__VU}`,
        numeroFactura: `FAC-K6-${__VU}-${Date.now()}`,
        detalles: [{ producto: { id: 1 }, cantidad: 1 }],
        detallePago: { metodoPago: 'EFECTIVO', estadoPago: 'APROBADO' },
    });
    const postRes = http.post(`${BASE_URL}/api/facturas/emitir`, facturaConDetalle, { headers });
    check(postRes, {
        'POST /api/facturas/emitir status 200 o 400': (r) => r.status === 200 || r.status === 400,
    });

    sleep(1);
}
