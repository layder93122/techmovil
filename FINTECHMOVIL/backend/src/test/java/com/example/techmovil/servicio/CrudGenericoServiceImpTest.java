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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CrudGenericoServiceImpTest {

    @Mock private ProductoRepository repo;
    @InjectMocks private ProductoServiceImp service;
    private Producto producto;

    @BeforeEach
    void setUp() {
        producto = Producto.builder().id(1L).marca("Samsung").modelo("Galaxy A55").precio(1200.0).stock(15).build();
    }

    @Test
    void save_GuardaYRetornaProducto() {
        when(repo.save(any())).thenReturn(producto);
        assertNotNull(service.save(producto));
        verify(repo, times(1)).save(producto);
    }

    @Test
    void findAll_RetornaLista() {
        when(repo.findAll()).thenReturn(Arrays.asList(producto));
        assertEquals(1, service.findAll().size());
    }

    @Test
    void findAll_ListaVacia() {
        when(repo.findAll()).thenReturn(Arrays.asList());
        assertTrue(service.findAll().isEmpty());
    }

    @Test
    void findById_Existente_RetornaProducto() {
        when(repo.findById(1L)).thenReturn(Optional.of(producto));
        assertEquals(1L, service.findById(1L).getId());
    }

    @Test
    void findById_NoExistente_LanzaEntityNotFoundException() {
        when(repo.findById(99L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.findById(99L));
    }

    @Test
    void findById_MensajeContieneId() {
        when(repo.findById(42L)).thenReturn(Optional.empty());
        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> service.findById(42L));
        assertTrue(ex.getMessage().contains("42"));
    }

    @Test
    void update_ExistingId_RetornaActualizado() {
        when(repo.existsById(1L)).thenReturn(true);
        when(repo.save(any())).thenReturn(producto);
        assertNotNull(service.update(producto, 1L));
    }

    @Test
    void update_IdNoExistente_LanzaException() {
        when(repo.existsById(99L)).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> service.update(producto, 99L));
    }

    @Test
    void update_MensajeContieneId() {
        when(repo.existsById(88L)).thenReturn(false);
        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> service.update(producto, 88L));
        assertTrue(ex.getMessage().contains("88"));
    }

    @Test
    void delete_Existente_Retorna200() {
        when(repo.existsById(1L)).thenReturn(true);
        doNothing().when(repo).deleteById(1L);
        CustomResponse r = service.delete(1L);
        assertEquals(200, r.getStatusCode());
        assertEquals("Exito", r.getMessage());
    }

    @Test
    void delete_NoExistente_LanzaException() {
        when(repo.existsById(99L)).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> service.delete(99L));
    }

    @Test
    void delete_MensajeContieneId() {
        when(repo.existsById(77L)).thenReturn(false);
        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> service.delete(77L));
        assertTrue(ex.getMessage().contains("77"));
    }

    @Test
    void delete_ResponseTieneFechaNoNula() {
        when(repo.existsById(1L)).thenReturn(true);
        doNothing().when(repo).deleteById(1L);
        assertNotNull(service.delete(1L).getDatetime());
    }
}
