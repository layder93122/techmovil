package com.example.techmovil.control;

import com.example.techmovil.excepciones.CarritoVacioException;
import com.example.techmovil.excepciones.StockInsuficienteException;
import com.example.techmovil.modelo.Factura;
import com.example.techmovil.servicio.FacturacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/facturas")
@RequiredArgsConstructor
@Tag(name = "Facturacion", description = "Procesamiento de pagos y emision de facturas")
public class FacturaController {

    private final FacturacionService facturacionService;

    @PostMapping("/emitir")
    @Operation(summary = "Emitir Factura")
    public ResponseEntity<Object> emitirFactura(@RequestBody Factura solicitudFactura) {
        try {
            return ResponseEntity.ok(facturacionService.procesarVentaFacturada(solicitudFactura));
        } catch (CarritoVacioException | StockInsuficienteException | EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
