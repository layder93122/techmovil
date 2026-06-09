package com.example.techmovil.control;

import com.example.techmovil.dtos.LoginRequest;
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
public class AuthControlador {

    @Value("${app.admin.username}")
    private String adminUsername;

    @Value("${app.admin.password}")
    private String adminPassword;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest loginRequest) {
        if (adminUsername != null && adminUsername.equals(loginRequest.getUsername())
                && adminPassword != null && adminPassword.equals(loginRequest.getPassword())) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("token", "simulated-jwt-token-techmovil-2026");
            response.put("role", "ADMIN");
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(401).body("Credenciales incorrectas");
    }
}
