package com.example.techmovil.control;

import com.example.techmovil.dtos.LoginRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AuthControladorTest {

    @InjectMocks private AuthControlador authControlador;
    private LoginRequest loginRequest;

    @BeforeEach
    void setUp() {
        loginRequest = new LoginRequest();
        ReflectionTestUtils.setField(authControlador, "adminUsername", "admin");
        ReflectionTestUtils.setField(authControlador, "adminPassword", "admin123");
    }

    @Test
    void login_CredencialesCorrectas_Retorna200() {
        loginRequest.setUsername("admin");
        loginRequest.setPassword("admin123");
        assertEquals(HttpStatus.OK, authControlador.login(loginRequest).getStatusCode());
    }

    @ParameterizedTest(name = "Campo [{0}] retorna [{1}]")
    @CsvSource({
        "status, success",
        "token, simulated-jwt-token-techmovil-2026",
        "role, ADMIN"
    })
    void login_CredencialesCorrectas_RetornaCamposEsperados(String campo, String valor) {
        loginRequest.setUsername("admin");
        loginRequest.setPassword("admin123");
        @SuppressWarnings("unchecked")
        Map<String, Object> body = (Map<String, Object>) authControlador.login(loginRequest).getBody();
        assertEquals(valor, String.valueOf(body.get(campo)));
    }

    @ParameterizedTest(name = "Credenciales [{0}/{1}] retornan 401")
    @MethodSource("credencialesInvalidas")
    void login_CredencialesInvalidas_Retorna401(String username, String password) {
        loginRequest.setUsername(username);
        loginRequest.setPassword(password);
        assertEquals(HttpStatus.UNAUTHORIZED, authControlador.login(loginRequest).getStatusCode());
    }

    static Stream<org.junit.jupiter.params.provider.Arguments> credencialesInvalidas() {
        return Stream.of(
            org.junit.jupiter.params.provider.Arguments.of("admin", "wrong"),
            org.junit.jupiter.params.provider.Arguments.of("hacker", "admin123"),
            org.junit.jupiter.params.provider.Arguments.of(null, null)
        );
    }

    @Test
    void login_CredencialesIncorrectas_RetornaMensajeError() {
        loginRequest.setUsername("x");
        loginRequest.setPassword("y");
        assertEquals("Credenciales incorrectas", authControlador.login(loginRequest).getBody());
    }

    @Test
    void authControlador_EsInstanciable() {
        assertNotNull(authControlador);
    }
}
