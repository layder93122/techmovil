package com.example.techmovil.servicio;

import com.example.techmovil.modelo.Producto;
import com.example.techmovil.repositorio.ProductoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductoServiceTest {

    @Mock
    private ProductoRepository repo;

    @InjectMocks
    private ProductoServiceImp service;

    @Test
    void findById_Encontrado_RetornaProducto() {
        Producto p = Producto.builder().id(1L).marca("Sony").build();
        when(repo.findById(1L)).thenReturn(Optional.of(p));

        Producto resultado = service.findById(1L);

        assertNotNull(resultado);
        assertEquals("Sony", resultado.getMarca());
    }

    @Test
    void findById_NoEncontrado_LanzaEntityNotFoundException() {
        when(repo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.findById(99L));
    }

    @Test
    void findById_MensajeExcepcion_ContieneId() {
        when(repo.findById(42L)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(
                EntityNotFoundException.class, () -> service.findById(42L));

        assertTrue(ex.getMessage().contains("42"));
    }
}
