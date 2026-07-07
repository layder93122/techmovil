package com.example.techmovil.excepciones;

public class CarritoVacioException extends RuntimeException {
    public CarritoVacioException(String mensaje) {
        super(mensaje);
    }
}
