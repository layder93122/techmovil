package com.example.techmovil.excepciones;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CustomResponseTest {

    @Test
    void builder_CreaCustomResponseCorrectamente() {
        LocalDateTime ahora = LocalDateTime.now();

        CustomResponse response = CustomResponse.builder()
                .statusCode(200)
                .datetime(ahora)
                .message("Éxito")
                .details("Operación completada")
                .build();

        assertEquals(200, response.getStatusCode());
        assertEquals(ahora, response.getDatetime());
        assertEquals("Éxito", response.getMessage());
        assertEquals("Operación completada", response.getDetails());
    }

    @Test
    void noArgsConstructor_CreaObjeto() {
        CustomResponse response = new CustomResponse();
        assertNotNull(response);
    }

    @Test
    void allArgsConstructor_FuncionaCorrectamente() {
        LocalDateTime ahora = LocalDateTime.now();
        CustomResponse response = new CustomResponse(404, ahora, "No encontrado", "ID 99");

        assertEquals(404, response.getStatusCode());
        assertEquals("No encontrado", response.getMessage());
    }

    @Test
    void setters_FuncionanCorrectamente() {
        CustomResponse response = new CustomResponse();
        response.setStatusCode(500);
        response.setMessage("Error");

        assertEquals(500, response.getStatusCode());
        assertEquals("Error", response.getMessage());
    }

    @Test
    void equals_MismosValores_SonIguales() {
        LocalDateTime ahora = LocalDateTime.of(2026, 5, 31, 12, 0);
        CustomResponse r1 = new CustomResponse(200, ahora, "OK", "detail");
        CustomResponse r2 = new CustomResponse(200, ahora, "OK", "detail");

        assertEquals(r1, r2);
    }

    @Test
    void toString_NoEsNulo() {
        CustomResponse response = CustomResponse.builder().statusCode(200).message("OK").build();
        assertNotNull(response.toString());
    }
}
