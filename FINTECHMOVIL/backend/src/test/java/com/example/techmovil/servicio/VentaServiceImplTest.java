package com.example.techmovil.servicio;

import com.example.techmovil.excepciones.CustomResponse;
import com.example.techmovil.excepciones.StockInsuficienteException;
import com.example.techmovil.modelo.Producto;
import com.example.techmovil.modelo.Usuario;
import com.example.techmovil.modelo.Venta;
import com.example.techmovil.repositorio.ProductoRepository;
import com.example.techmovil.repositorio.VentaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VentaServiceImplTest {

    @Mock private VentaRepository repo;
    @Mock private ProductoRepository productoRepo;
    @InjectMocks private VentaServiceImpl service;

    private Producto producto;
    private Venta venta;

    @BeforeEach
    void setUp() {
        producto = Producto.builder().id(1L).marca("Samsung").modelo("Galaxy S24")
                .precio(2500.0).stock(10).build();
        venta = Venta.builder().cantidad(2)
                .producto(Producto.builder().id(1L).build())
                .usuario(Usuario.builder().idUsuario(1L).nombre("Carlos").build()).build();
    }

    @Test
    void save_VentaValida_DescuentaStockYGuarda() {
        when(productoRepo.findById(1L)).thenReturn(Optional.of(producto));
        when(productoRepo.save(any())).thenReturn(producto);
        when(repo.save(any())).thenAnswer(i -> i.getArguments()[0]);
        Venta r = service.save(venta);
        assertEquals(5000.0, r.getTotal(), 0.01);
        verify(productoRepo).save(argThat(p -> p.getStock() == 8));
    }

    @Test
    void save_CantidadNula_UsaUno() {
        venta.setCantidad(null);
        when(productoRepo.findById(1L)).thenReturn(Optional.of(producto));
        when(productoRepo.save(any())).thenReturn(producto);
        when(repo.save(any())).thenAnswer(i -> i.getArguments()[0]);
        assertEquals(2500.0, service.save(venta).getTotal(), 0.01);
    }

    @Test
    void save_ProductoNull_LanzaIllegalArgument() {
        venta.setProducto(null);
        assertThrows(IllegalArgumentException.class, () -> service.save(venta));
    }

    @Test
    void save_ProductoIdNull_LanzaIllegalArgument() {
        venta.setProducto(Producto.builder().id(null).build());
        assertThrows(IllegalArgumentException.class, () -> service.save(venta));
    }

    @Test
    void save_ProductoNoExistente_LanzaEntityNotFoundException() {
        when(productoRepo.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.save(venta));
    }

    @Test
    void save_ProductoInactivo_LanzaEntityNotFoundException() {
        producto.setActivo(false);
        when(productoRepo.findById(1L)).thenReturn(Optional.of(producto));
        assertThrows(EntityNotFoundException.class, () -> service.save(venta));
    }

    @Test
    void save_StockInsuficiente_LanzaStockInsuficienteException() {
        producto.setStock(1);
        when(productoRepo.findById(1L)).thenReturn(Optional.of(producto));
        assertThrows(StockInsuficienteException.class, () -> service.save(venta));
    }

    @Test
    void save_MensajeStockInsuficiente_ContieneModelo() {
        producto.setStock(0);
        when(productoRepo.findById(1L)).thenReturn(Optional.of(producto));
        StockInsuficienteException ex = assertThrows(StockInsuficienteException.class, () -> service.save(venta));
        assertTrue(ex.getMessage().contains("Galaxy S24"));
    }

    @Test
    void findAll_RetornaLista() {
        when(repo.findAllByActivoTrue()).thenReturn(Arrays.asList(venta));
        List<Venta> lista = service.findAll();
        assertEquals(1, lista.size());
    }

    @Test
    void findById_Existente_RetornaVenta() {
        when(repo.findById(1L)).thenReturn(Optional.of(venta));
        assertNotNull(service.findById(1L));
    }

    @Test
    void findById_NoExistente_LanzaEntityNotFoundException() {
        when(repo.findById(99L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.findById(99L));
    }

    @Test
    void update_ExistingId_RetornaActualizado() {
        when(repo.existsById(1L)).thenReturn(true);
        when(repo.save(any())).thenReturn(venta);
        assertNotNull(service.update(venta, 1L));
    }

    @Test
    void update_IdNoExistente_LanzaEntityNotFoundException() {
        when(repo.existsById(99L)).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> service.update(venta, 99L));
    }

    @Test
    void delete_Existente_RetornaCustomResponse200() {
        when(repo.findById(1L)).thenReturn(Optional.of(venta));
        when(repo.save(any())).thenReturn(venta);
        CustomResponse r = service.delete(1L);
        assertEquals(200, r.getStatusCode());
    }

    @Test
    void delete_NoExistente_LanzaEntityNotFoundException() {
        when(repo.findById(99L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.delete(99L));
    }
}
