package com.example.techmovil.dtos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductoDTOTest {

    @Test
    void constructor_AllArgs_FuncionaCorrectamente() {
        ProductoDTO dto = new ProductoDTO(1L, "iPhone 15", "Apple", 4500.0, 5);

        assertEquals(1L, dto.getIdProducto());
        assertEquals("iPhone 15", dto.getNombre());
        assertEquals("Apple", dto.getMarca());
        assertEquals(4500.0, dto.getPrecio());
        assertEquals(5, dto.getStock());
    }

    @Test
    void constructor_NoArgs_CreaCamposNulos() {
        ProductoDTO dto = new ProductoDTO();

        assertNull(dto.getIdProducto());
        assertNull(dto.getNombre());
        assertNull(dto.getMarca());
    }

    @Test
    void builder_CreaProductoDTO() {
        ProductoDTO dto = ProductoDTO.builder()
                .idProducto(2L)
                .nombre("Galaxy S24")
                .marca("Samsung")
                .precio(3000.0)
                .stock(8)
                .build();

        assertEquals(2L, dto.getIdProducto());
        assertEquals("Samsung", dto.getMarca());
    }

    @Test
    void setters_FuncionanCorrectamente() {
        ProductoDTO dto = new ProductoDTO();
        dto.setIdProducto(5L);
        dto.setNombre("Pixel 9");
        dto.setMarca("Google");
        dto.setPrecio(3500.0);
        dto.setStock(12);

        assertEquals(5L, dto.getIdProducto());
        assertEquals("Google", dto.getMarca());
    }

    @Test
    void equals_MismosValores_SonIguales() {
        ProductoDTO dto1 = new ProductoDTO(1L, "Test", "Marca", 100.0, 5);
        ProductoDTO dto2 = new ProductoDTO(1L, "Test", "Marca", 100.0, 5);

        assertEquals(dto1, dto2);
    }

    @Test
    void toString_NoEsNulo() {
        ProductoDTO dto = new ProductoDTO(1L, "Test", "Marca", 100.0, 5);
        assertNotNull(dto.toString());
    }
}
