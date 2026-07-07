package com.example.techmovil.modelo;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PagoTest {

    @Test
    void settersGetters_FuncionanCorrectamente() {
        Pago pago = new Pago();
        LocalDateTime fecha = LocalDateTime.now();

        pago.setId(1L);
        pago.setMetodoPago("TARJETA");
        pago.setTransaccionId("TXN-00123");
        pago.setEstadoPago("APROBADO");
        pago.setFechaPago(fecha);

        assertEquals(1L, pago.getId());
        assertEquals("TARJETA", pago.getMetodoPago());
        assertEquals("TXN-00123", pago.getTransaccionId());
        assertEquals("APROBADO", pago.getEstadoPago());
        assertEquals(fecha, pago.getFechaPago());
    }

    @Test
    void pago_InstanciaVacia_CamposNulos() {
        Pago pago = new Pago();
        assertNull(pago.getMetodoPago());
        assertNull(pago.getEstadoPago());
    }

    @Test
    void pago_MetodosValidos_SonAceptados() {
        Pago pago = new Pago();
        String[] metodos = {"TARJETA", "YAPE", "PLIN", "EFECTIVO"};
        for (String metodo : metodos) {
            pago.setMetodoPago(metodo);
            assertEquals(metodo, pago.getMetodoPago());
        }
    }

    @Test
    void toString_NoEsNulo() {
        Pago pago = new Pago();
        pago.setMetodoPago("EFECTIVO");
        assertNotNull(pago.toString());
    }

    @Test
    void equals_MismosValores_SonIguales() {
        Pago p1 = new Pago();
        p1.setId(1L);
        p1.setMetodoPago("YAPE");

        Pago p2 = new Pago();
        p2.setId(1L);
        p2.setMetodoPago("YAPE");

        assertEquals(p1, p2);
    }
}
