package com.example.techmovil.modelo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    @Test
    void builder_CreaUsuarioCorrectamente() {
        Usuario u = Usuario.builder()
                .idUsuario(1L)
                .nombre("Ana Torres")
                .username("ana_t")
                .password("pass123")
                .rol("ADMIN")
                .build();

        assertEquals(1L, u.getIdUsuario());
        assertEquals("Ana Torres", u.getNombre());
        assertEquals("ana_t", u.getUsername());
        assertEquals("ADMIN", u.getRol());
    }

    @Test
    void noArgsConstructor_FuncionaCorrectamente() {
        Usuario u = new Usuario();
        assertNotNull(u);
        assertNull(u.getNombre());
    }

    @Test
    void settersGetters_FuncionanCorrectamente() {
        Usuario u = new Usuario();
        u.setIdUsuario(5L);
        u.setNombre("Pedro");
        u.setUsername("pedro_x");
        u.setPassword("securePass");
        u.setRol("VENDEDOR");

        assertEquals(5L, u.getIdUsuario());
        assertEquals("Pedro", u.getNombre());
        assertEquals("pedro_x", u.getUsername());
        assertEquals("securePass", u.getPassword());
        assertEquals("VENDEDOR", u.getRol());
    }

    @Test
    void equals_MismosValores_SonIguales() {
        Usuario u1 = Usuario.builder().idUsuario(1L).username("user1").build();
        Usuario u2 = Usuario.builder().idUsuario(1L).username("user1").build();
        assertEquals(u1, u2);
    }

    @Test
    void toString_NoEsNulo() {
        Usuario u = Usuario.builder().idUsuario(1L).nombre("Test").build();
        assertNotNull(u.toString());
    }
}
