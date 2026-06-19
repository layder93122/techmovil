package com.example.techmovil.control;

import com.example.techmovil.excepciones.CustomResponse;
import com.example.techmovil.modelo.Venta;
import com.example.techmovil.servicio.VentaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ventas")
@RequiredArgsConstructor
@Tag(name = "Ventas", description = "Gestión de ventas con eliminado lógico")
public class VentaController {

    private final VentaService service;

    @GetMapping
    @Operation(summary = "Listar ventas activas")
    public ResponseEntity<List<Venta>> listar() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar venta por ID")
    public ResponseEntity<Venta> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    @Operation(summary = "Registrar nueva venta")
    public ResponseEntity<Venta> guardar(@Valid @RequestBody Venta venta) {
        return ResponseEntity.ok(service.save(venta));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar venta")
    public ResponseEntity<Venta> actualizar(@Valid @RequestBody Venta venta, @PathVariable Long id) {
        venta.setIdVenta(id);
        return ResponseEntity.ok(service.update(venta, id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar venta (lógico)")
    public ResponseEntity<CustomResponse> eliminar(@PathVariable Long id) {
        return ResponseEntity.ok(service.delete(id));
    }
}
