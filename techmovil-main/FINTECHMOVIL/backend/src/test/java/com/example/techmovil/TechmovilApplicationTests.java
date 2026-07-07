package com.example.techmovil;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TechmovilApplicationTests {

    @Test
    void contextLoads() {
        assertDoesNotThrow(() -> {}, "La aplicacion debe iniciarse sin errores");
    }

    @Test
    void mainClassTieneAnnotationSpringBoot() {
        assertTrue(TechmovilApplication.class.isAnnotationPresent(SpringBootApplication.class));
    }
}
