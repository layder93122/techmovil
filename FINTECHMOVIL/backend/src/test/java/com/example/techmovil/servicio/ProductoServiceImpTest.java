package com.example.techmovil.servicio;

import com.example.techmovil.excepciones.CustomResponse;
import com.example.techmovil.modelo.Producto;
import com.example.techmovil.repositorio.ProductoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductoServiceImpTest {

    @Mock
    private ProductoRepository repo;

    @InjectMocks
    private ProductoServiceImp service;

    private Producto producto;

    @BeforeEach
    void setUp() {
        producto = Producto.builder()
                .id(1L)
                .marca("Motorola")
                .modelo("Edge 50")
                .precio(1800.0)
                .stock(8)
                .build();
    }

    @Test
    void save_GuardaProducto() {
        when(repo.save(any(Producto.class))).thenReturn(producto);

        Producto resultado = service.save(producto);

        assertNotNull(resultado);
        assertEquals("Motorola", resultado.getMarca());
        verify(repo, times(1)).save(producto);
    }

    @Test
    void findAll_RetornaListaCompleta() {
        when(repo.findAll()).thenReturn(Arrays.asList(producto));

        List<Producto> lista = service.findAll();

        assertFalse(lista.isEmpty());
        assertEquals(1, lista.size());
    }

    @Test
    void findAll_ListaVacia_RetornaVacia() {
        when(repo.findAll()).thenReturn(Collections.emptyList());

        List<Producto> lista = service.findAll();

        assertTrue(lista.isEmpty());
    }

    @Test
    void findById_Existente_RetornaProducto() {
        when(repo.findById(1L)).thenReturn(Optional.of(producto));

        Producto resultado = service.findById(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
    }

    @Test
    void findById_NoExistente_LanzaEntityNotFoundException() {
        when(repo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.findById(99L));
    }

    @Test
    void update_ExistingId_Actualiza() {
        when(repo.existsById(1L)).thenReturn(true);
        when(repo.save(any(Producto.class))).thenReturn(producto);

        Producto resultado = service.update(producto, 1L);

        assertNotNull(resultado);
        verify(repo).save(producto);
    }

    @Test
    void update_IdNoExistente_LanzaException() {
        when(repo.existsById(99L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> service.update(producto, 99L));
    }

    @Test
    void delete_Existente_RetornaCustomResponse200() {
        when(repo.existsById(1L)).thenReturn(true);
        doNothing().when(repo).deleteById(1L);

        CustomResponse response = service.delete(1L);

        assertEquals(200, response.getStatusCode());
        verify(repo, times(1)).deleteById(1L);
    }

    @Test
    void delete_NoExistente_LanzaEntityNotFoundException() {
        when(repo.existsById(99L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> service.delete(99L));
    }

    @Test
    void productoService_EsInstanciable() {
        assertNotNull(service);
    }
}
