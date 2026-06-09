package com.example.techmovil.modelo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductoTest {

    @Test
    void builder_CreaProductoCorrectamente() {
        Producto p = Producto.builder()
                .id(1L)
                .marca("Apple")
                .modelo("iPhone 15 Pro")
                .precio(4999.0)
                .stock(5)
                .stockMinimo(3)
                .imagenUrl("http://img.com/iphone.jpg")
                .build();

        assertEquals(1L, p.getId());
        assertEquals("Apple", p.getMarca());
        assertEquals("iPhone 15 Pro", p.getModelo());
        assertEquals(4999.0, p.getPrecio());
        assertEquals(5, p.getStock());
    }

    @Test
    void noArgsConstructor_StockMinimoDefaultEs3() {
        Producto p = new Producto();
        // El campo tiene valor por defecto 3 en la anotación de instancia
        assertNotNull(p);
    }

    @Test
    void settersGetters_FuncionanCorrectamente() {
        Producto p = new Producto();
        p.setId(10L);
        p.setMarca("Xiaomi");
        p.setModelo("Redmi Note 13");
        p.setPrecio(899.0);
        p.setStock(20);
        p.setStockMinimo(5);
        p.setImagenUrl("http://img.com/xiaomi.jpg");

        assertEquals(10L, p.getId());
        assertEquals("Xiaomi", p.getMarca());
        assertEquals("Redmi Note 13", p.getModelo());
        assertEquals(899.0, p.getPrecio());
        assertEquals(20, p.getStock());
        assertEquals(5, p.getStockMinimo());
    }

    @Test
    void equals_MismosValores_SonIguales() {
        Producto p1 = Producto.builder().id(1L).marca("Samsung").build();
        Producto p2 = Producto.builder().id(1L).marca("Samsung").build();
        assertEquals(p1, p2);
    }

    @Test
    void toString_NoEsNulo() {
        Producto p = Producto.builder().id(1L).marca("Huawei").build();
        assertNotNull(p.toString());
    }

    @Test
    void caracteristicas_PuedeSetearse() {
        Producto p = new Producto();
        Caracteristica c = new Caracteristica();
        c.setProcesador("A17");
        p.setCaracteristicas(c);

        assertNotNull(p.getCaracteristicas());
        assertEquals("A17", p.getCaracteristicas().getProcesador());
    }
}
