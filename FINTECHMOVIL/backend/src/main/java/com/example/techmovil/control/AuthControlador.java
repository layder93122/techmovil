package com.example.techmovil.control;

import com.example.techmovil.config.JwtService;
import com.example.techmovil.dtos.LoginRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticacion", description = "Endpoints de login y generacion de token JWT")
public class AuthControlador {

    private final JwtService jwtService;

    @Value("${app.admin.username}")
    private String adminUsername;

    @Value("${app.admin.password}")
    private String adminPassword;

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesion", description = "Valida credenciales y retorna un token JWT firmado")
    @ApiResponse(responseCode = "200", description = "Login exitoso, token retornado")
    @ApiResponse(responseCode = "401", description = "Credenciales incorrectas")
    public ResponseEntity<Object> login(@Valid @RequestBody LoginRequest loginRequest) {
        if (adminUsername.equals(loginRequest.getUsername())
                && adminPassword.equals(loginRequest.getPassword())) {
            String token = jwtService.generateToken(loginRequest.getUsername());
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("token", token);
            response.put("role", "ADMIN");
            response.put("nombre", "Administrador");
            response.put("username", loginRequest.getUsername());
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(401).body("Credenciales incorrectas");
    }
}
