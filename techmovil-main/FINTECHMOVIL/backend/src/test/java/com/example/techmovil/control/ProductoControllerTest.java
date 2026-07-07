package com.example.techmovil.control;

import com.example.techmovil.modelo.Producto;
import com.example.techmovil.repositorio.ProductoRepository;
import com.example.techmovil.servicio.ProductoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
class ProductoControllerTest {

    @Mock
    private ProductoService service;

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoController controller;

    private Producto producto;

    @BeforeEach
    void setUp() {
        producto = new Producto();
        producto.setId(1L);
        producto.setMarca("Samsung");
        producto.setModelo("Galaxy S24");
        producto.setPrecio(2500.0);
        producto.setStock(10);
    }

    @Test
    void listar_RetornaListaProductos() {
        when(service.findAll()).thenReturn(Arrays.asList(producto));

        ResponseEntity<List<Producto>> response = controller.listar();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void listar_ListaVacia_RetornaOk() {
        when(service.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<List<Producto>> response = controller.listar();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void guardar_ProductoValido_RetornaProductoGuardado() {
        when(service.save(any(Producto.class))).thenReturn(producto);

        ResponseEntity<Producto> response = controller.guardar(producto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Samsung", response.getBody().getMarca());
    }

    @Test
    void guardar_LlamaAlServicio() {
        when(service.save(any(Producto.class))).thenReturn(producto);

        controller.guardar(producto);

        verify(service, times(1)).save(producto);
    }

    @Test
    void obtenerAlertasStock_RetornaProductosEnAlerta() {
        Producto productoAlerta = new Producto();
        productoAlerta.setStock(1);
        productoAlerta.setStockMinimo(3);
        when(productoRepository.obtenerProductosEnAlerta()).thenReturn(Arrays.asList(productoAlerta));

        ResponseEntity<List<Producto>> response = controller.obtenerAlertasStock();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void obtenerAlertasStock_SinAlertas_RetornaListaVacia() {
        when(productoRepository.obtenerProductosEnAlerta()).thenReturn(Collections.emptyList());

        ResponseEntity<List<Producto>> response = controller.obtenerAlertasStock();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void buscar_RetornaProductoPorId() {
        when(service.findById(1L)).thenReturn(producto);

        ResponseEntity<Producto> response = controller.buscar(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    void buscar_LlamaAlServicioConId() {
        when(service.findById(1L)).thenReturn(producto);

        controller.buscar(1L);

        verify(service, times(1)).findById(1L);
    }

    @Test
    void actualizar_ProductoValido_RetornaProductoActualizado() {
        producto.setMarca("Apple");
        when(service.update(any(Producto.class), eq(1L))).thenReturn(producto);

        ResponseEntity<Producto> response = controller.actualizar(producto, 1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Apple", response.getBody().getMarca());
    }

    @Test
    void actualizar_AsignaIdAlProducto() {
        when(service.update(any(Producto.class), eq(1L))).thenReturn(producto);

        controller.actualizar(producto, 1L);

        verify(service, times(1)).update(argThat(p -> p.getId().equals(1L)), eq(1L));
    }

    @Test
    void eliminar_RetornaCustomResponse() {
        com.example.techmovil.excepciones.CustomResponse cr =
            com.example.techmovil.excepciones.CustomResponse.builder()
                .statusCode(200).message("Eliminado").build();
        when(service.delete(1L)).thenReturn(cr);

        ResponseEntity<com.example.techmovil.excepciones.CustomResponse> response = controller.eliminar(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Eliminado", response.getBody().getMessage());
    }

    @Test
    void eliminar_LlamaAlServicioConId() {
        com.example.techmovil.excepciones.CustomResponse cr =
            com.example.techmovil.excepciones.CustomResponse.builder()
                .statusCode(200).message("OK").build();
        when(service.delete(1L)).thenReturn(cr);

        controller.eliminar(1L);

        verify(service, times(1)).delete(1L);
    }

    @Test
    void controller_EsInstanciable() {
        assertNotNull(controller);
    }
}
