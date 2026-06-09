package com.example.techmovil.control;

import com.example.techmovil.modelo.Venta;
import com.example.techmovil.servicio.VentaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/ventas")
@RequiredArgsConstructor
@Tag(name = "Ventas", description = "Gestion de ventas")
public class VentaController {

    private final VentaService service;

    @GetMapping
    @Operation(summary = "Lista todas las ventas")
    public ResponseEntity<List<Venta>> listar() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping
    @Operation(summary = "Registrar nueva venta")
    public ResponseEntity<Venta> guardar(@RequestBody Venta venta) {
        return ResponseEntity.ok(service.save(venta));
    }
}
