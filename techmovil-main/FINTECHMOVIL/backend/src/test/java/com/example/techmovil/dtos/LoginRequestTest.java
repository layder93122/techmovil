package com.example.techmovil.dtos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginRequestTest {

    @Test
    void gettersSetters_FuncionanCorrectamente() {
        LoginRequest request = new LoginRequest();
        request.setUsername("admin");
        request.setPassword("secret123");

        assertEquals("admin", request.getUsername());
        assertEquals("secret123", request.getPassword());
    }

    @Test
    void toString_NoEsNulo() {
        LoginRequest request = new LoginRequest();
        request.setUsername("user");
        request.setPassword("pass");

        assertNotNull(request.toString());
    }

    @Test
    void equals_MismosValores_SonIguales() {
        LoginRequest r1 = new LoginRequest();
        r1.setUsername("admin");
        r1.setPassword("pass");

        LoginRequest r2 = new LoginRequest();
        r2.setUsername("admin");
        r2.setPassword("pass");

        assertEquals(r1, r2);
    }

    @Test
    void hashCode_MismosValores_MismoHash() {
        LoginRequest r1 = new LoginRequest();
        r1.setUsername("admin");
        r1.setPassword("pass");

        LoginRequest r2 = new LoginRequest();
        r2.setUsername("admin");
        r2.setPassword("pass");

        assertEquals(r1.hashCode(), r2.hashCode());
    }

    @Test
    void loginRequest_CamposNulos_EsValido() {
        LoginRequest request = new LoginRequest();
        assertNull(request.getUsername());
        assertNull(request.getPassword());
    }
}
