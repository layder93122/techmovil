package com.example.techmovil.control;

import com.example.techmovil.repositorio.FacturaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reportes")
@RequiredArgsConstructor
@Tag(name = "Reportes", description = "Métricas avanzadas de rendimiento comercial")
public class ReporteController {

    private final FacturaRepository facturaRepository;

    @GetMapping("/diario")
    @Operation(summary = "Muestreo diario")
    public ResponseEntity<List<Map<String, Object>>> obtenerDiario() {
        return ResponseEntity.ok(facturaRepository.obtenerVentasDiarias());
    }

    @GetMapping("/semanal")
    @Operation(summary = "Muestreo semanal")
    public ResponseEntity<List<Map<String, Object>>> obtenerSemanal() {
        return ResponseEntity.ok(facturaRepository.obtenerVentasSemanales());
    }

    @GetMapping("/mensual")
    @Operation(summary = "Muestreo mensual")
    public ResponseEntity<List<Map<String, Object>>> obtenerMensual() {
        return ResponseEntity.ok(facturaRepository.obtenerVentasMensuales());
    }

    @GetMapping("/anual")
    @Operation(summary = "Muestreo anual")
    public ResponseEntity<List<Map<String, Object>>> obtenerAnual() {
        return ResponseEntity.ok(facturaRepository.obtenerVentasAnuales());
    }
}
