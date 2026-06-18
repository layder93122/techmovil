package com.example.techmovil.excepciones;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomResponse> handleValidation(MethodArgumentNotValidException ex) {
        String details = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.joining(" | "));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                CustomResponse.builder()
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .datetime(LocalDateTime.now())
                        .message("Error de validacion")
                        .details(details)
                        .build());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<CustomResponse> handleNotFound(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                CustomResponse.builder().statusCode(HttpStatus.NOT_FOUND.value())
                        .datetime(LocalDateTime.now()).message("Error: Recurso no encontrado")
                        .details(ex.getMessage()).build());
    }

    @ExceptionHandler(StockInsuficienteException.class)
    public ResponseEntity<CustomResponse> handleStockInsuficiente(StockInsuficienteException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                CustomResponse.builder().statusCode(HttpStatus.BAD_REQUEST.value())
                        .datetime(LocalDateTime.now()).message("Error: Stock insuficiente")
                        .details(ex.getMessage()).build());
    }

    @ExceptionHandler(CarritoVacioException.class)
    public ResponseEntity<CustomResponse> handleCarritoVacio(CarritoVacioException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                CustomResponse.builder().statusCode(HttpStatus.BAD_REQUEST.value())
                        .datetime(LocalDateTime.now()).message("Error: La factura no tiene productos")
                        .details(ex.getMessage()).build());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<CustomResponse> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                CustomResponse.builder().statusCode(HttpStatus.BAD_REQUEST.value())
                        .datetime(LocalDateTime.now()).message("Error: Datos invalidos")
                        .details(ex.getMessage()).build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomResponse> handleGlobal(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                CustomResponse.builder().statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .datetime(LocalDateTime.now()).message("Error interno en el servidor")
                        .details(ex.getMessage()).build());
    }
}
