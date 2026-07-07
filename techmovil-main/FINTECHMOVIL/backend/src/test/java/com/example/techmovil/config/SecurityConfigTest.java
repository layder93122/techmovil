package com.example.techmovil.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SecurityConfigTest {

    @Mock private JwtFilter jwtFilter;
    @InjectMocks private SecurityConfig securityConfig;

    @Test
    void securityConfig_EsInstanciable() {
        assertNotNull(securityConfig);
    }

    @Test
    void securityConfig_TieneAnnotationEnableWebSecurity() {
        assertNotNull(SecurityConfig.class.getAnnotation(EnableWebSecurity.class));
    }

    @Test
    void securityConfig_TieneMetodoFilterChain() {
        assertDoesNotThrow(() ->
            SecurityConfig.class.getMethod("filterChain", HttpSecurity.class)
        );
    }
}
