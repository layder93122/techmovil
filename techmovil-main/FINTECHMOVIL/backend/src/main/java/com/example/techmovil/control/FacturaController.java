package com.example.techmovil.control;

import com.example.techmovil.excepciones.CarritoVacioException;
import com.example.techmovil.excepciones.CustomResponse;
import com.example.techmovil.excepciones.StockInsuficienteException;
import com.example.techmovil.modelo.Factura;
import com.example.techmovil.servicio.FacturacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/facturas")
@RequiredArgsConstructor
@Tag(name = "Facturación", description = "Procesamiento de pagos y emisión de facturas")
public class FacturaController {

    private final FacturacionService facturacionService;

    @GetMapping
    @Operation(summary = "Listar facturas activas")
    public ResponseEntity<Object> listar() {
        return ResponseEntity.ok(facturacionService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar factura por ID")
    public ResponseEntity<Object> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(facturacionService.findById(id));
    }

    @PostMapping("/emitir")
    @Operation(summary = "Emitir factura con validación de stock")
    public ResponseEntity<Object> emitirFactura(@Valid @RequestBody Factura solicitudFactura) {
        try {
            return ResponseEntity.ok(facturacionService.procesarVentaFacturada(solicitudFactura));
        } catch (CarritoVacioException | StockInsuficienteException | EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Anular factura (lógico)")
    public ResponseEntity<CustomResponse> anular(@PathVariable Long id) {
        return ResponseEntity.ok(facturacionService.delete(id));
    }
}
