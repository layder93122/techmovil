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
    void controller_EsInstanciable() {
        assertNotNull(controller);
    }
}
