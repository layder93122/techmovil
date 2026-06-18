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

    @Test
    void builderToString_NoEsNulo() {
        assertNotNull(CustomResponse.builder().statusCode(200).message("OK").toString());
    }

    @Test
    void equals_ConNull_RetornaFalse() {
        CustomResponse r = new CustomResponse(200, LocalDateTime.now(), "OK", "d");
        assertNotEquals(r, null);
    }

    @Test
    void equals_ConDistintoTipo_RetornaFalse() {
        CustomResponse r = new CustomResponse(200, LocalDateTime.now(), "OK", "d");
        assertNotEquals(r, "string");
    }

    @Test
    void equals_CamposNulos_SonIguales() {
        CustomResponse r1 = new CustomResponse();
        CustomResponse r2 = new CustomResponse();
        assertEquals(r1, r2);
    }

    @Test
    void equals_DatetimeNuloVsNoNulo_RetornaFalse() {
        LocalDateTime ahora = LocalDateTime.now();
        CustomResponse r1 = new CustomResponse(200, null, "OK", "d");
        CustomResponse r2 = new CustomResponse(200, ahora, "OK", "d");
        assertNotEquals(r1, r2);
    }

    @Test
    void equals_DatetimeNoNuloVsNulo_RetornaFalse() {
        LocalDateTime ahora = LocalDateTime.now();
        CustomResponse r1 = new CustomResponse(200, ahora, "OK", "d");
        CustomResponse r2 = new CustomResponse(200, null, "OK", "d");
        assertNotEquals(r1, r2);
    }

    @Test
    void equals_MessageNuloVsNoNulo_RetornaFalse() {
        CustomResponse r1 = new CustomResponse(200, null, null, "d");
        CustomResponse r2 = new CustomResponse(200, null, "OK", "d");
        assertNotEquals(r1, r2);
    }

    @Test
    void equals_DetailsNuloVsNoNulo_RetornaFalse() {
        CustomResponse r1 = new CustomResponse(200, null, "OK", null);
        CustomResponse r2 = new CustomResponse(200, null, "OK", "d");
        assertNotEquals(r1, r2);
    }

    @Test
    void equals_DiferenteStatusCode_RetornaFalse() {
        LocalDateTime ahora = LocalDateTime.now();
        CustomResponse r1 = new CustomResponse(200, ahora, "OK", "d");
        CustomResponse r2 = new CustomResponse(404, ahora, "OK", "d");
        assertNotEquals(r1, r2);
    }

    @Test
    void hashCode_CamposNulos_NoLanzaExcepcion() {
        CustomResponse r = new CustomResponse();
        assertDoesNotThrow(r::hashCode);
    }

    @Test
    void hashCode_MismosValores_Iguales() {
        CustomResponse r1 = new CustomResponse(200, null, "OK", null);
        CustomResponse r2 = new CustomResponse(200, null, "OK", null);
        assertEquals(r1.hashCode(), r2.hashCode());
    }

    @Test
    void canEqual_MismoTipo_RetornaTrue() {
        CustomResponse r1 = new CustomResponse();
        CustomResponse r2 = new CustomResponse();
        assertTrue(r1.canEqual(r2));
    }

    @Test
    void canEqual_OtroTipo_RetornaFalse() {
        CustomResponse r = new CustomResponse();
        assertFalse(r.canEqual("otro"));
    }

    @Test
    void setDatetime_FuncionaCorrectamente() {
        LocalDateTime ahora = LocalDateTime.now();
        CustomResponse r = new CustomResponse();
        r.setDatetime(ahora);
        assertEquals(ahora, r.getDatetime());
    }

    @Test
    void setDetails_FuncionaCorrectamente() {
        CustomResponse r = new CustomResponse();
        r.setDetails("detalles");
        assertEquals("detalles", r.getDetails());
    }

    @Test
    void equals_DetailsNoNuloVsNulo_RetornaFalse() {
        CustomResponse r1 = new CustomResponse(200, null, "OK", "detalle");
        CustomResponse r2 = new CustomResponse(200, null, "OK", null);
        assertNotEquals(r1, r2);
    }

    @Test
    void hashCode_ConDetailsNoNulo_NoLanzaExcepcion() {
        CustomResponse r = new CustomResponse(200, null, "OK", "detalle");
        assertDoesNotThrow(r::hashCode);
    }
}
