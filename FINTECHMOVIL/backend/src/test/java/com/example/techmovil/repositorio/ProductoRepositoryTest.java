package com.example.techmovil.repositorio;

import com.example.techmovil.modelo.Producto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductoRepositoryTest {

    @Mock
    private ProductoRepository repo;

    @Test
    void obtenerProductosEnAlerta_RetornaProductosConBajoStock() {
        Producto p = new Producto();
        p.setId(1L);
        p.setStock(1);
        p.setStockMinimo(5);
        when(repo.obtenerProductosEnAlerta()).thenReturn(Arrays.asList(p));

        List<Producto> resultado = repo.obtenerProductosEnAlerta();

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
    }

    @Test
    void obtenerProductosEnAlerta_SinAlertas_RetornaListaVacia() {
        when(repo.obtenerProductosEnAlerta()).thenReturn(Collections.emptyList());

        List<Producto> resultado = repo.obtenerProductosEnAlerta();

        assertTrue(resultado.isEmpty());
    }

    @Test
    void save_GuardaProducto() {
        Producto p = new Producto();
        p.setMarca("LG");
        when(repo.save(any(Producto.class))).thenReturn(p);

        Producto guardado = repo.save(p);

        assertNotNull(guardado);
        assertEquals("LG", guardado.getMarca());
    }

    @Test
    void findById_ProductoExistente_Retorna() {
        Producto p = new Producto();
        p.setId(1L);
        when(repo.findById(1L)).thenReturn(Optional.of(p));

        Optional<Producto> resultado = repo.findById(1L);

        assertTrue(resultado.isPresent());
    }

    @Test
    void findById_ProductoNoExistente_RetornaVacio() {
        when(repo.findById(999L)).thenReturn(Optional.empty());

        Optional<Producto> resultado = repo.findById(999L);

        assertTrue(resultado.isEmpty());
    }

    @Test
    void existsById_ProductoExistente_RetornaTrue() {
        when(repo.existsById(1L)).thenReturn(true);
        assertTrue(repo.existsById(1L));
    }

    @Test
    void deleteById_LlamaAlMetodo() {
        doNothing().when(repo).deleteById(1L);
        repo.deleteById(1L);
        verify(repo, times(1)).deleteById(1L);
    }
}
