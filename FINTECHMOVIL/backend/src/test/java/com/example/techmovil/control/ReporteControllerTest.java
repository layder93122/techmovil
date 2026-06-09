package com.example.techmovil.control;

import com.example.techmovil.repositorio.FacturaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReporteControllerTest {

    @Mock
    private FacturaRepository facturaRepository;

    @InjectMocks
    private ReporteController reporteController;

    private List<Map<String, Object>> datos;

    @BeforeEach
    void setUp() {
        Map<String, Object> fila = new HashMap<>();
        fila.put("periodo", "2026-05");
        fila.put("ingresos", 15000.0);
        fila.put("total_ventas", 10);
        datos = Arrays.asList(fila);
    }

    @Test
    void obtenerDiario_RetornaDatos() {
        when(facturaRepository.obtenerVentasDiarias()).thenReturn(datos);

        ResponseEntity<List<Map<String, Object>>> response = reporteController.obtenerDiario();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void obtenerDiario_LlamaAlRepositorio() {
        when(facturaRepository.obtenerVentasDiarias()).thenReturn(datos);

        reporteController.obtenerDiario();

        verify(facturaRepository, times(1)).obtenerVentasDiarias();
    }

    @Test
    void obtenerSemanal_RetornaDatos() {
        when(facturaRepository.obtenerVentasSemanales()).thenReturn(datos);

        ResponseEntity<List<Map<String, Object>>> response = reporteController.obtenerSemanal();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void obtenerSemanal_LlamaAlRepositorio() {
        when(facturaRepository.obtenerVentasSemanales()).thenReturn(datos);

        reporteController.obtenerSemanal();

        verify(facturaRepository, times(1)).obtenerVentasSemanales();
    }

    @Test
    void obtenerMensual_RetornaDatos() {
        when(facturaRepository.obtenerVentasMensuales()).thenReturn(datos);

        ResponseEntity<List<Map<String, Object>>> response = reporteController.obtenerMensual();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void obtenerMensual_LlamaAlRepositorio() {
        when(facturaRepository.obtenerVentasMensuales()).thenReturn(datos);

        reporteController.obtenerMensual();

        verify(facturaRepository, times(1)).obtenerVentasMensuales();
    }

    @Test
    void obtenerAnual_RetornaDatos() {
        when(facturaRepository.obtenerVentasAnuales()).thenReturn(datos);

        ResponseEntity<List<Map<String, Object>>> response = reporteController.obtenerAnual();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void obtenerAnual_LlamaAlRepositorio() {
        when(facturaRepository.obtenerVentasAnuales()).thenReturn(datos);

        reporteController.obtenerAnual();

        verify(facturaRepository, times(1)).obtenerVentasAnuales();
    }

    @Test
    void controller_EsInstanciable() {
        assertNotNull(reporteController);
    }
}
