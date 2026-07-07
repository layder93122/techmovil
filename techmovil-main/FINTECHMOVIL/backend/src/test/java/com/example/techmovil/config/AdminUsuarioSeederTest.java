package com.example.techmovil.config;

import com.example.techmovil.modelo.Usuario;
import com.example.techmovil.repositorio.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminUsuarioSeederTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private AdminUsuarioSeeder seeder;

    private void setCredenciales() {
        ReflectionTestUtils.setField(seeder, "adminUsername", "admin");
        ReflectionTestUtils.setField(seeder, "adminPassword", "admin123");
    }

    @Test
    void run_AdminNoExiste_LoCrea() throws Exception {
        setCredenciales();
        when(usuarioRepository.existsByUsername("admin")).thenReturn(false);

        seeder.run();

        verify(usuarioRepository).save(argThat((Usuario u) ->
                u.getUsername().equals("admin")
                        && u.getRol().equals("ADMIN")
                        && Boolean.TRUE.equals(u.getActivo())));
    }

    @Test
    void run_AdminYaExiste_NoLoDuplica() throws Exception {
        setCredenciales();
        when(usuarioRepository.existsByUsername("admin")).thenReturn(true);

        seeder.run();

        verify(usuarioRepository, never()).save(any());
    }
}
