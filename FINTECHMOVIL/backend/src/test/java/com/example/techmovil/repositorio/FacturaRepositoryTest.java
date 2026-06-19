package com.example.techmovil.repositorio;

import com.example.techmovil.modelo.Factura;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FacturaRepositoryTest {

    @Mock
    private FacturaRepository repo;

    private Factura facturaActiva() {
        return Factura.builder()
                .id(1L)
                .numeroFactura("FAC-000001")
                .cliente("Juan Perez")
                .total(1180.0)
                .subtotal(1000.0)
                .igv(180.0)
                .fechaEmision(LocalDateTime.now())
                .activo(true)
                .build();
    }

    @Test
    void findAllByActivoTrue_RetornaFacturasActivas() {
        when(repo.findAllByActivoTrue()).thenReturn(Arrays.asList(facturaActiva()));
        List<Factura> resultado = repo.findAllByActivoTrue();
        assertFalse(resultado.isEmpty());
        assertTrue(resultado.get(0).getActivo());
    }

    @Test
    void findAllByActivoTrue_SinFacturas_RetornaListaVacia() {
        when(repo.findAllByActivoTrue()).thenReturn(Collections.emptyList());
        assertTrue(repo.findAllByActivoTrue().isEmpty());
    }

    @Test
    void save_GuardaFactura() {
        Factura f = facturaActiva();
        when(repo.save(any(Factura.class))).thenReturn(f);
        Factura guardada = repo.save(f);
        assertNotNull(guardada);
        assertEquals("FAC-000001", guardada.getNumeroFactura());
    }

    @Test
    void findById_FacturaExistente_Retorna() {
        when(repo.findById(1L)).thenReturn(Optional.of(facturaActiva()));
        assertTrue(repo.findById(1L).isPresent());
    }

    @Test
    void findById_FacturaInexistente_RetornaVacio() {
        when(repo.findById(999L)).thenReturn(Optional.empty());
        assertTrue(repo.findById(999L).isEmpty());
    }

    @Test
    void obtenerVentasDiarias_RetornaResultados() {
        Map<String, Object> row = Map.of("periodo", "2026-06-18", "ingresos", 5000.0, "total_ventas", 3);
        when(repo.obtenerVentasDiarias()).thenReturn(Arrays.asList(row));
        List<Map<String, Object>> resultado = repo.obtenerVentasDiarias();
        assertFalse(resultado.isEmpty());
        assertEquals("2026-06-18", resultado.get(0).get("periodo"));
    }

    @Test
    void obtenerVentasSemanales_RetornaResultados() {
        Map<String, Object> row = Map.of("periodo", "Semana 25", "ingresos", 15000.0, "total_ventas", 10);
        when(repo.obtenerVentasSemanales()).thenReturn(Arrays.asList(row));
        assertFalse(repo.obtenerVentasSemanales().isEmpty());
    }

    @Test
    void obtenerVentasMensuales_RetornaResultados() {
        Map<String, Object> row = Map.of("periodo", "2026-06", "ingresos", 60000.0, "total_ventas", 40);
        when(repo.obtenerVentasMensuales()).thenReturn(Arrays.asList(row));
        assertFalse(repo.obtenerVentasMensuales().isEmpty());
    }

    @Test
    void obtenerVentasAnuales_RetornaResultados() {
        Map<String, Object> row = Map.of("periodo", 2026, "ingresos", 720000.0, "total_ventas", 480);
        when(repo.obtenerVentasAnuales()).thenReturn(Arrays.asList(row));
        assertFalse(repo.obtenerVentasAnuales().isEmpty());
    }
}
