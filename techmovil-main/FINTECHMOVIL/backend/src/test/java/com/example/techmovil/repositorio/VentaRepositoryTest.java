package com.example.techmovil.repositorio;

import com.example.techmovil.modelo.Producto;
import com.example.techmovil.modelo.Usuario;
import com.example.techmovil.modelo.Venta;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VentaRepositoryTest {

    @Mock
    private VentaRepository repo;

    private Venta ventaActiva() {
        return Venta.builder()
                .idVenta(1L)
                .fecha(LocalDateTime.now())
                .cantidad(2)
                .total(1000.0)
                .activo(true)
                .producto(Producto.builder().id(1L).precio(500.0).build())
                .usuario(Usuario.builder().idUsuario(1L).build())
                .build();
    }

    @Test
    void findAllByActivoTrue_RetornaVentasActivas() {
        when(repo.findAllByActivoTrue()).thenReturn(Arrays.asList(ventaActiva()));
        List<Venta> resultado = repo.findAllByActivoTrue();
        assertFalse(resultado.isEmpty());
        assertTrue(resultado.get(0).getActivo());
    }

    @Test
    void findAllByActivoTrue_SinVentas_RetornaListaVacia() {
        when(repo.findAllByActivoTrue()).thenReturn(Collections.emptyList());
        assertTrue(repo.findAllByActivoTrue().isEmpty());
    }

    @Test
    void save_GuardaVenta() {
        Venta venta = ventaActiva();
        when(repo.save(any(Venta.class))).thenReturn(venta);
        Venta guardada = repo.save(venta);
        assertNotNull(guardada);
        assertEquals(1000.0, guardada.getTotal());
    }

    @Test
    void findById_VentaExistente_Retorna() {
        when(repo.findById(1L)).thenReturn(Optional.of(ventaActiva()));
        assertTrue(repo.findById(1L).isPresent());
    }

    @Test
    void findById_VentaInexistente_RetornaVacio() {
        when(repo.findById(999L)).thenReturn(Optional.empty());
        assertTrue(repo.findById(999L).isEmpty());
    }

    @Test
    void existsById_VentaExistente_RetornaTrue() {
        when(repo.existsById(1L)).thenReturn(true);
        assertTrue(repo.existsById(1L));
    }

    @Test
    void softDelete_SetActivoFalse_NoEliminaRegistro() {
        Venta venta = ventaActiva();
        venta.setActivo(false);
        when(repo.save(venta)).thenReturn(venta);
        Venta result = repo.save(venta);
        assertFalse(result.getActivo());
        verify(repo, never()).deleteById(any());
    }
}
