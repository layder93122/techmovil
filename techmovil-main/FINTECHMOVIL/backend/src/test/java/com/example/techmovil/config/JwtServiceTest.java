package com.example.techmovil.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    private JwtService jwtService;

    @BeforeEach
    void setUp() { jwtService = new JwtService(); }

    @Test
    void generateToken_DebeRetornarTokenNoNulo() {
        assertNotNull(jwtService.generateToken("admin"));
    }

    @Test
    void generateToken_TokenTieneTresParts() {
        assertEquals(3, jwtService.generateToken("user").split("\\.").length);
    }

    @Test
    void generateToken_DiferentesUsuarios_TokensDiferentes() {
        assertNotEquals(jwtService.generateToken("u1"), jwtService.generateToken("u2"));
    }

    @Test
    void getKey_NoEsNulo() {
        assertNotNull(jwtService.getKey());
    }

    @Test
    void generateToken_NoEstaVacio() {
        assertFalse(jwtService.generateToken("admin").isEmpty());
    }
}
