package com.example.techmovil.control;

import com.example.techmovil.modelo.Usuario;
import com.example.techmovil.servicio.UsuarioService;
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
class UsuarioControllerTest {

    @Mock
    private UsuarioService service;

    @InjectMocks
    private UsuarioController controller;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setIdUsuario(1L);
        usuario.setNombre("Carlos Lopez");
        usuario.setUsername("carlos");
        usuario.setPassword("pass123");
        usuario.setRol("VENDEDOR");
    }

    @Test
    void listar_RetornaListaUsuarios() {
        when(service.findAll()).thenReturn(Arrays.asList(usuario));

        ResponseEntity<List<Usuario>> response = controller.listar();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void listar_ListaVacia_RetornaOk() {
        when(service.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<List<Usuario>> response = controller.listar();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void guardar_UsuarioValido_RetornaUsuarioGuardado() {
        when(service.save(any(Usuario.class))).thenReturn(usuario);

        ResponseEntity<Usuario> response = controller.guardar(usuario);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("carlos", response.getBody().getUsername());
    }

    @Test
    void guardar_LlamaAlServicio() {
        when(service.save(any(Usuario.class))).thenReturn(usuario);

        controller.guardar(usuario);

        verify(service, times(1)).save(usuario);
    }

    @Test
    void buscar_RetornaUsuarioPorId() {
        when(service.findById(1L)).thenReturn(usuario);

        ResponseEntity<Usuario> response = controller.buscar(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, response.getBody().getIdUsuario());
    }

    @Test
    void buscar_LlamaAlServicioConId() {
        when(service.findById(1L)).thenReturn(usuario);

        controller.buscar(1L);

        verify(service, times(1)).findById(1L);
    }

    @Test
    void actualizar_UsuarioValido_RetornaUsuarioActualizado() {
        usuario.setNombre("Carlos Actualizado");
        when(service.update(any(Usuario.class), eq(1L))).thenReturn(usuario);

        ResponseEntity<Usuario> response = controller.actualizar(usuario, 1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Carlos Actualizado", response.getBody().getNombre());
    }

    @Test
    void actualizar_AsignaIdAlUsuario() {
        when(service.update(any(Usuario.class), eq(1L))).thenReturn(usuario);

        controller.actualizar(usuario, 1L);

        verify(service, times(1)).update(argThat(u -> u.getIdUsuario().equals(1L)), eq(1L));
    }

    @Test
    void eliminar_RetornaCustomResponse() {
        com.example.techmovil.excepciones.CustomResponse cr =
            com.example.techmovil.excepciones.CustomResponse.builder()
                .statusCode(200).message("Usuario eliminado").build();
        when(service.delete(1L)).thenReturn(cr);

        ResponseEntity<com.example.techmovil.excepciones.CustomResponse> response = controller.eliminar(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Usuario eliminado", response.getBody().getMessage());
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
