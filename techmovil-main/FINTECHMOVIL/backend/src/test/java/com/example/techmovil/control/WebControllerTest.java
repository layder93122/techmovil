package com.example.techmovil.control;

import com.example.techmovil.modelo.Producto;
import com.example.techmovil.modelo.Usuario;
import com.example.techmovil.modelo.Venta;
import com.example.techmovil.servicio.ProductoService;
import com.example.techmovil.servicio.UsuarioService;
import com.example.techmovil.servicio.VentaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WebControllerTest {

    @Mock private ProductoService productoService;
    @Mock private UsuarioService usuarioService;
    @Mock private VentaService ventaService;
    @Mock private Model model;
    @InjectMocks private WebController webController;

    @BeforeEach
    void setUp() {
        // Inicializacion automatica con @ExtendWith(MockitoExtension.class)
    }

    @Test
    void login_RetornaVistaLogin() {
        assertEquals("login", webController.login());
    }

    @Test
    void home_RetornaVistaHome() {
        assertEquals("home", webController.home());
    }

    @Test
    void index_RedireccionaAHome() {
        assertEquals("redirect:/home", webController.index());
    }

    @Test
    void verInventario_AgregaProductosAlModelo() {
        when(productoService.findAll()).thenReturn(Arrays.asList(new Producto()));
        assertEquals("inventario", webController.verInventario(model));
        verify(model).addAttribute(eq("productos"), anyList());
    }

    @Test
    void verPersonal_AgregaUsuariosAlModelo() {
        when(usuarioService.findAll()).thenReturn(Arrays.asList(new Usuario()));
        assertEquals("personal", webController.verPersonal(model));
        verify(model).addAttribute(eq("usuarios"), anyList());
    }

    @Test
    void nuevaVenta_AgregaAtributosAlModelo() {
        when(productoService.findAll()).thenReturn(Collections.emptyList());
        when(usuarioService.findAll()).thenReturn(Collections.emptyList());
        assertEquals("registrar_venta", webController.nuevaVenta(model));
        verify(model).addAttribute(eq("venta"), any(Venta.class));
    }

    @Test
    void guardarVenta_RetornaVistaFactura() {
        Venta guardada = new Venta(); guardada.setIdVenta(1L);
        when(ventaService.save(any(Venta.class))).thenReturn(guardada);
        assertEquals("factura", webController.guardarVenta(new Venta(), model));
        verify(model).addAttribute("venta", guardada);
    }

    @Test
    void controller_EsInstanciable() {
        assertNotNull(webController);
    }
}
