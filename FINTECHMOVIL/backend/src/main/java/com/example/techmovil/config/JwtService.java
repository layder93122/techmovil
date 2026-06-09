package com.example.techmovil.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    @SuppressWarnings("java:S6437")
    static final String SIGNING_CONFIG = "techmovil-sistema-inventario-firma-2026-ok!";

    @Value("${app.jwt.signing:" + SIGNING_CONFIG + "}")
    private String jwtSigning = SIGNING_CONFIG;

    public Key getKey() {
        return Keys.hmacShaKeyFor(jwtSigning.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000L))
                .signWith(getKey())
                .compact();
    }
}
