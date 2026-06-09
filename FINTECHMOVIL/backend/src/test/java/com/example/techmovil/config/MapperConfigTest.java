package com.example.techmovil.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MapperConfigTest {

    @Test
    void mapperConfig_EsInstanciable() {
        MapperConfig config = new MapperConfig();
        assertNotNull(config, "MapperConfig debe ser instanciable");
    }

    @Test
    void mapperConfig_TieneAnnotationConfiguration() {
        assertDoesNotThrow(() -> {
            Class<?> clazz = MapperConfig.class;
            assertNotNull(clazz.getAnnotation(org.springframework.context.annotation.Configuration.class));
        });
    }
}
