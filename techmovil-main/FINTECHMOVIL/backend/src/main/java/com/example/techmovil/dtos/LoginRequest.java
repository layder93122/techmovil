package com.example.techmovil.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank(message = "El username no puede estar vacio")
    private String username;

    @NotBlank(message = "La contrasena no puede estar vacia")
    private String password;
}
