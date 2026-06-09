package com.example.techmovil.modelo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CaracteristicaTest {

    @Test
    void settersGetters_FuncionanCorrectamente() {
        Caracteristica c = new Caracteristica();
        c.setId(1L);
        c.setProcesador("Snapdragon 8 Gen 3");
        c.setRam("12GB");
        c.setAlmacenamiento("256GB");
        c.setBateria("5000mAh");
        c.setCamaras("200MP + 12MP + 10MP");
        c.setPantalla("6.8 pulgadas AMOLED");

        assertEquals(1L, c.getId());
        assertEquals("Snapdragon 8 Gen 3", c.getProcesador());
        assertEquals("12GB", c.getRam());
        assertEquals("256GB", c.getAlmacenamiento());
        assertEquals("5000mAh", c.getBateria());
        assertEquals("200MP + 12MP + 10MP", c.getCamaras());
        assertEquals("6.8 pulgadas AMOLED", c.getPantalla());
    }

    @Test
    void caracteristica_InstanciaVacia_CamposNulos() {
        Caracteristica c = new Caracteristica();
        assertNull(c.getProcesador());
        assertNull(c.getRam());
    }

    @Test
    void toString_NoEsNulo() {
        Caracteristica c = new Caracteristica();
        c.setProcesador("A17 Bionic");
        assertNotNull(c.toString());
    }

    @Test
    void equals_MismosValores_SonIguales() {
        Caracteristica c1 = new Caracteristica();
        c1.setId(1L);
        c1.setProcesador("A17");

        Caracteristica c2 = new Caracteristica();
        c2.setId(1L);
        c2.setProcesador("A17");

        assertEquals(c1, c2);
    }
}
