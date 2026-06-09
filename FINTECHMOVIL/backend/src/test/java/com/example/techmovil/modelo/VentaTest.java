package com.example.techmovil.modelo;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class VentaTest {

    @Test
    void builder_CreaVentaCorrectamente() {
        Producto producto = Producto.builder().id(1L).marca("Samsung").precio(2000.0).build();
        Usuario usuario = Usuario.builder().idUsuario(1L).nombre("Juan").build();
        LocalDateTime fecha = LocalDateTime.now();

        Venta v = Venta.builder()
                .idVenta(1L)
                .fecha(fecha)
                .total(2000.0)
                .cantidad(1)
                .producto(producto)
                .usuario(usuario)
                .build();

        assertEquals(1L, v.getIdVenta());
        assertEquals(2000.0, v.getTotal());
        assertEquals(1, v.getCantidad());
        assertNotNull(v.getProducto());
        assertNotNull(v.getUsuario());
    }

    @Test
    void noArgsConstructor_FuncionaCorrectamente() {
        Venta v = new Venta();
        assertNotNull(v);
        assertNull(v.getTotal());
    }

    @Test
    void settersGetters_FuncionanCorrectamente() {
        Venta v = new Venta();
        LocalDateTime fecha = LocalDateTime.of(2026, 5, 31, 10, 0);
        v.setIdVenta(3L);
        v.setFecha(fecha);
        v.setTotal(4500.0);
        v.setCantidad(2);

        assertEquals(3L, v.getIdVenta());
        assertEquals(fecha, v.getFecha());
        assertEquals(4500.0, v.getTotal());
        assertEquals(2, v.getCantidad());
    }

    @Test
    void toString_NoEsNulo() {
        Venta v = Venta.builder().idVenta(1L).total(100.0).build();
        assertNotNull(v.toString());
    }
}
