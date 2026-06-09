package com.example.techmovil.control;

import com.example.techmovil.modelo.Producto;
import com.example.techmovil.repositorio.ProductoRepository;
import com.example.techmovil.servicio.ProductoService;
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
@RequestMapping("/api/productos")
@RequiredArgsConstructor
@Tag(name = "Productos", description = "Mantenimiento del catalogo de celulares")
public class ProductoController {

    private final ProductoService service;
    private final ProductoRepository productoRepository;

    @GetMapping
    @Operation(summary = "Obtener catalogo")
    public ResponseEntity<List<Producto>> listar() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping
    @Operation(summary = "Agregar producto")
    public ResponseEntity<Producto> guardar(@RequestBody Producto producto) {
        return ResponseEntity.ok(service.save(producto));
    }

    @GetMapping("/alertas")
    @Operation(summary = "Alertas de stock")
    public ResponseEntity<List<Producto>> obtenerAlertasStock() {
        return ResponseEntity.ok(productoRepository.obtenerProductosEnAlerta());
    }
}
