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
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;

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
    void buscar_RetornaVentaPorId() {
        when(service.findById(1L)).thenReturn(venta);

        ResponseEntity<Venta> response = controller.buscar(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, response.getBody().getIdVenta());
    }

    @Test
    void buscar_LlamaAlServicioConId() {
        when(service.findById(1L)).thenReturn(venta);

        controller.buscar(1L);

        verify(service, times(1)).findById(1L);
    }

    @Test
    void actualizar_VentaValida_RetornaVentaActualizada() {
        venta.setTotal(2000.0);
        when(service.update(any(Venta.class), eq(1L))).thenReturn(venta);

        ResponseEntity<Venta> response = controller.actualizar(venta, 1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2000.0, response.getBody().getTotal());
    }

    @Test
    void actualizar_AsignaIdALaVenta() {
        when(service.update(any(Venta.class), eq(1L))).thenReturn(venta);

        controller.actualizar(venta, 1L);

        verify(service, times(1)).update(argThat(v -> v.getIdVenta().equals(1L)), eq(1L));
    }

    @Test
    void eliminar_RetornaCustomResponse() {
        com.example.techmovil.excepciones.CustomResponse cr =
            com.example.techmovil.excepciones.CustomResponse.builder()
                .statusCode(200).message("Venta eliminada").build();
        when(service.delete(1L)).thenReturn(cr);

        ResponseEntity<com.example.techmovil.excepciones.CustomResponse> response = controller.eliminar(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Venta eliminada", response.getBody().getMessage());
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
