package com.example.techmovil.modelo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DetalleFacturaTest {

    @Test
    void builder_CreaDetalleCorrectamente() {
        Producto producto = Producto.builder().id(1L).marca("Sony").precio(1500.0).build();

        DetalleFactura d = DetalleFactura.builder()
                .id(1L)
                .producto(producto)
                .cantidad(2)
                .precioUnitario(1500.0)
                .subtotal(3000.0)
                .build();

        assertEquals(1L, d.getId());
        assertEquals(2, d.getCantidad());
        assertEquals(1500.0, d.getPrecioUnitario());
        assertEquals(3000.0, d.getSubtotal());
        assertNotNull(d.getProducto());
    }

    @Test
    void noArgsConstructor_FuncionaCorrectamente() {
        DetalleFactura d = new DetalleFactura();
        assertNotNull(d);
        assertNull(d.getCantidad());
    }

    @Test
    void settersGetters_FuncionanCorrectamente() {
        DetalleFactura d = new DetalleFactura();
        d.setId(5L);
        d.setCantidad(3);
        d.setPrecioUnitario(999.0);
        d.setSubtotal(2997.0);

        assertEquals(5L, d.getId());
        assertEquals(3, d.getCantidad());
        assertEquals(2997.0, d.getSubtotal());
    }

    @Test
    void toString_NoEsNulo() {
        DetalleFactura d = DetalleFactura.builder().id(1L).cantidad(1).build();
        assertNotNull(d.toString());
    }
}
