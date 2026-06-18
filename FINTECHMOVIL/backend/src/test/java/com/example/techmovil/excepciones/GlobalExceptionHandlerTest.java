package com.example.techmovil.excepciones;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Arrays;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler handler;

    @Test
    void handleNotFound_RetornaStatus404() {
        ResponseEntity<CustomResponse> r = handler.handleNotFound(new EntityNotFoundException("msg"));
        assertEquals(HttpStatus.NOT_FOUND, r.getStatusCode());
    }

    @Test
    void handleNotFound_RetornaMensajeError() {
        ResponseEntity<CustomResponse> r = handler.handleNotFound(new EntityNotFoundException("x"));
        assertEquals("Error: Recurso no encontrado", r.getBody().getMessage());
    }

    @Test
    void handleNotFound_RetornaDetallesDelError() {
        ResponseEntity<CustomResponse> r = handler.handleNotFound(new EntityNotFoundException("ID 5 no existe"));
        assertEquals("ID 5 no existe", r.getBody().getDetails());
    }

    @Test
    void handleNotFound_RetornaStatusCode404EnCuerpo() {
        ResponseEntity<CustomResponse> r = handler.handleNotFound(new EntityNotFoundException("msg"));
        assertEquals(404, r.getBody().getStatusCode());
    }

    @Test
    void handleNotFound_RetornaFechaNoNula() {
        ResponseEntity<CustomResponse> r = handler.handleNotFound(new EntityNotFoundException("msg"));
        assertNotNull(r.getBody().getDatetime());
    }

    @Test
    void handleGlobal_RetornaStatus500() {
        ResponseEntity<CustomResponse> r = handler.handleGlobal(new RuntimeException("err"));
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, r.getStatusCode());
    }

    @Test
    void handleGlobal_RetornaMensajeErrorInterno() {
        ResponseEntity<CustomResponse> r = handler.handleGlobal(new RuntimeException("msg"));
        assertEquals("Error interno en el servidor", r.getBody().getMessage());
    }

    @Test
    void handleGlobal_RetornaDetallesDelError() {
        ResponseEntity<CustomResponse> r = handler.handleGlobal(new RuntimeException("Fallo crítico"));
        assertEquals("Fallo crítico", r.getBody().getDetails());
    }

    @Test
    void handleStockInsuficiente_Retorna400() {
        ResponseEntity<CustomResponse> r = handler.handleStockInsuficiente(
                new StockInsuficienteException("Sin stock"));
        assertEquals(HttpStatus.BAD_REQUEST, r.getStatusCode());
        assertEquals("Error: Stock insuficiente", r.getBody().getMessage());
    }

    @Test
    void handleCarritoVacio_Retorna400() {
        ResponseEntity<CustomResponse> r = handler.handleCarritoVacio(
                new CarritoVacioException("Carrito vacío"));
        assertEquals(HttpStatus.BAD_REQUEST, r.getStatusCode());
        assertNotNull(r.getBody().getDatetime());
    }

    @Test
    void handleIllegalArgument_Retorna400() {
        ResponseEntity<CustomResponse> r = handler.handleIllegalArgument(
                new IllegalArgumentException("Dato inválido"));
        assertEquals(HttpStatus.BAD_REQUEST, r.getStatusCode());
        assertEquals("Dato inválido", r.getBody().getDetails());
    }

    @Test
    void handleValidation_RetornaStatus400() {
        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError = new FieldError("producto", "marca", "no puede estar vacia");
        when(ex.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getFieldErrors()).thenReturn(Arrays.asList(fieldError));

        ResponseEntity<CustomResponse> r = handler.handleValidation(ex);

        assertEquals(HttpStatus.BAD_REQUEST, r.getStatusCode());
        assertEquals("Error de validacion", r.getBody().getMessage());
        assertTrue(r.getBody().getDetails().contains("marca"));
    }

    @Test
    void handleValidation_SinErrores_RetornaDetalleVacio() {
        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);
        when(ex.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getFieldErrors()).thenReturn(java.util.Collections.emptyList());

        ResponseEntity<CustomResponse> r = handler.handleValidation(ex);

        assertEquals(HttpStatus.BAD_REQUEST, r.getStatusCode());
        assertEquals("", r.getBody().getDetails());
    }

    @Test
    void handler_EsInstanciable() {
        assertNotNull(handler);
    }
}
