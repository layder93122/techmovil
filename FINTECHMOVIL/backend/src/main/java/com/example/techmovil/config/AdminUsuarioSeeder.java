package com.example.techmovil.config;

import com.example.techmovil.modelo.Usuario;
import com.example.techmovil.repositorio.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminUsuarioSeeder implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;

    @Value("${app.admin.username}")
    private String adminUsername;

    @Value("${app.admin.password}")
    private String adminPassword;

    @Override
    public void run(String... args) {
        if (!usuarioRepository.existsByUsername(adminUsername)) {
            usuarioRepository.save(Usuario.builder()
                    .nombre("Administrador")
                    .username(adminUsername)
                    .password(adminPassword)
                    .rol("ADMIN")
                    .activo(true)
                    .build());
        }
    }
}
