package com.example.techmovil.control;

import com.example.techmovil.modelo.Producto;
import com.example.techmovil.modelo.Usuario;
import com.example.techmovil.modelo.Venta;
import com.example.techmovil.servicio.VentaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VentaControllerTest {

    @Mock
    private VentaService service;

    @InjectMocks
    private VentaController controller;

    private Venta venta;

    @BeforeEach
    void setUp() {
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setPrecio(1500.0);

        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1L);

        venta = new Venta();
        venta.setIdVenta(1L);
        venta.setFecha(LocalDateTime.now());
        venta.setTotal(1500.0);
        venta.setCantidad(1);
        venta.setProducto(producto);
        venta.setUsuario(usuario);
    }

    @Test
    void listar_RetornaListaVentas() {
        when(service.findAll()).thenReturn(Arrays.asList(venta));

        ResponseEntity<List<Venta>> response = controller.listar();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void guardar_VentaValida_RetornaVentaGuardada() {
        when(service.save(any(Venta.class))).thenReturn(venta);

        ResponseEntity<Venta> response = controller.guardar(venta);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1500.0, response.getBody().getTotal());
    }

    @Test
    void guardar_LlamaAlServicio() {
        when(service.save(any(Venta.class))).thenReturn(venta);

        controller.guardar(venta);

        verify(service, times(1)).save(venta);
    }

    @Test
    void controller_EsInstanciable() {
        assertNotNull(controller);
    }
}
