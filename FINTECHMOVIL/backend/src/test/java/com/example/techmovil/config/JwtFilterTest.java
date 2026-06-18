package com.example.techmovil.config;

import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContextHolder;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtFilterTest {

    @Mock private JwtService jwtService;
    @InjectMocks private JwtFilter jwtFilter;
    @Mock private HttpServletRequest request;
    @Mock private HttpServletResponse response;
    @Mock private FilterChain filterChain;

    @BeforeEach
    void setUp() {
        lenient().when(jwtService.getKey())
                .thenReturn(Keys.hmacShaKeyFor(
                        JwtService.SIGNING_CONFIG.getBytes(StandardCharsets.UTF_8)));
        SecurityContextHolder.clearContext();
    }

    @ParameterizedTest(name = "Header [{0}] pasa al siguiente filtro sin JWT")
    @NullAndEmptySource
    @ValueSource(strings = {"Basic dXNlcjpwYXNz", "Bearer token_invalido_xyz"})
    void doFilterInternal_VariosHeaders_PasaAlSiguienteFiltro(String header) throws Exception {
        when(request.getHeader("Authorization")).thenReturn(header);
        jwtFilter.doFilter(request, response, filterChain);
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void doFilterInternal_TokenValido_EstableceAutenticacion() throws Exception {
        JwtService realService = new JwtService();
        String token = realService.generateToken("admin");
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtService.getKey()).thenReturn(realService.getKey());

        jwtFilter.doFilter(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals("admin", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    @Test
    void jwtFilter_EsInstanciable() {
        assertDoesNotThrow(() -> new JwtFilter(jwtService));
    }
}
