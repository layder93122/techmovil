package com.example.techmovil.modelo;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class FacturaTest {

    @Test
    void builder_CreaFacturaCorrectamente() {
        Factura f = Factura.builder()
                .id(1L)
                .numeroFactura("FAC-000001")
                .cliente("Maria Quispe")
                .total(1180.0)
                .subtotal(1000.0)
                .igv(180.0)
                .fechaEmision(LocalDateTime.now())
                .build();

        assertEquals(1L, f.getId());
        assertEquals("FAC-000001", f.getNumeroFactura());
        assertEquals("Maria Quispe", f.getCliente());
        assertEquals(1180.0, f.getTotal());
        assertEquals(1000.0, f.getSubtotal());
        assertEquals(180.0, f.getIgv());
    }

    @Test
    void noArgsConstructor_FuncionaCorrectamente() {
        Factura f = new Factura();
        assertNotNull(f);
        assertNull(f.getNumeroFactura());
    }

    @Test
    void settersGetters_FuncionanCorrectamente() {
        Factura f = new Factura();
        f.setId(2L);
        f.setNumeroFactura("FAC-000002");
        f.setCliente("Luis Ramirez");
        f.setTotal(590.0);
        f.setSubtotal(500.0);
        f.setIgv(90.0);

        assertEquals(2L, f.getId());
        assertEquals("FAC-000002", f.getNumeroFactura());
        assertEquals("Luis Ramirez", f.getCliente());
    }

    @Test
    void detalles_PuedenSetearse() {
        Factura f = new Factura();
        DetalleFactura detalle = new DetalleFactura();
        detalle.setCantidad(1);
        f.setDetalles(Arrays.asList(detalle));

        assertNotNull(f.getDetalles());
        assertEquals(1, f.getDetalles().size());
    }

    @Test
    void pago_PuedeSetearse() {
        Factura f = new Factura();
        Pago pago = new Pago();
        pago.setMetodoPago("YAPE");
        pago.setEstadoPago("APROBADO");
        f.setDetallePago(pago);

        assertNotNull(f.getDetallePago());
        assertEquals("YAPE", f.getDetallePago().getMetodoPago());
    }

    @Test
    void toString_NoEsNulo() {
        Factura f = Factura.builder().id(1L).numeroFactura("FAC-001").build();
        assertNotNull(f.toString());
    }
}
