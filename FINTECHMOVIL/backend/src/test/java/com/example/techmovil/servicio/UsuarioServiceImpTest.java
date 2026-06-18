package com.example.techmovil.servicio;

import com.example.techmovil.excepciones.CustomResponse;
import com.example.techmovil.modelo.Usuario;
import com.example.techmovil.repositorio.UsuarioRepository;
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
class UsuarioServiceImpTest {

    @Mock
    private UsuarioRepository repo;

    @InjectMocks
    private UsuarioServiceImp service;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = Usuario.builder()
                .idUsuario(1L)
                .nombre("Ana Torres")
                .username("ana_t")
                .password("pass123")
                .rol("VENDEDOR")
                .build();
    }

    @Test
    void save_UsuarioValido_Retorna() {
        when(repo.save(any(Usuario.class))).thenReturn(usuario);

        Usuario resultado = service.save(usuario);

        assertNotNull(resultado);
        assertEquals("Ana Torres", resultado.getNombre());
    }

    @Test
    void findAll_RetornaLista() {
        when(repo.findAllByActivoTrue()).thenReturn(Arrays.asList(usuario));

        List<Usuario> lista = service.findAll();

        assertFalse(lista.isEmpty());
    }

    @Test
    void findById_Existente_Retorna() {
        when(repo.findById(1L)).thenReturn(Optional.of(usuario));

        Usuario resultado = service.findById(1L);

        assertEquals("ana_t", resultado.getUsername());
    }

    @Test
    void findById_NoExistente_LanzaException() {
        when(repo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.findById(99L));
    }

    @Test
    void update_ExistingId_RetornaActualizado() {
        when(repo.existsById(1L)).thenReturn(true);
        when(repo.save(any(Usuario.class))).thenReturn(usuario);

        Usuario resultado = service.update(usuario, 1L);

        assertNotNull(resultado);
    }

    @Test
    void update_IdNoExistente_LanzaException() {
        when(repo.existsById(99L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> service.update(usuario, 99L));
    }

    @Test
    void delete_Existente_RetornaExito() {
        when(repo.findById(1L)).thenReturn(Optional.of(usuario));
        when(repo.save(any(Usuario.class))).thenReturn(usuario);

        CustomResponse response = service.delete(1L);

        assertEquals(200, response.getStatusCode());
    }

    @Test
    void delete_NoExistente_LanzaException() {
        when(repo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.delete(99L));
    }
}
