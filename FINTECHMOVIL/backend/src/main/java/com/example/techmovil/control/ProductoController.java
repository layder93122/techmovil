package com.example.techmovil.control;

import com.example.techmovil.excepciones.CustomResponse;
import com.example.techmovil.modelo.Producto;
import com.example.techmovil.repositorio.ProductoRepository;
import com.example.techmovil.servicio.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
@Tag(name = "Productos", description = "Mantenimiento del catálogo de celulares")
public class ProductoController {

    private final ProductoService service;
    private final ProductoRepository productoRepository;

    @GetMapping
    @Operation(summary = "Listar productos activos")
    public ResponseEntity<List<Producto>> listar() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar producto por ID")
    public ResponseEntity<Producto> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    @Operation(summary = "Registrar producto")
    public ResponseEntity<Producto> guardar(@Valid @RequestBody Producto producto) {
        return ResponseEntity.ok(service.save(producto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar producto")
    public ResponseEntity<Producto> actualizar(@Valid @RequestBody Producto producto, @PathVariable Long id) {
        producto.setId(id);
        return ResponseEntity.ok(service.update(producto, id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar producto (lógico)")
    public ResponseEntity<CustomResponse> eliminar(@PathVariable Long id) {
        return ResponseEntity.ok(service.delete(id));
    }

    @GetMapping("/alertas")
    @Operation(summary = "Productos con stock bajo")
    public ResponseEntity<List<Producto>> alertasStock() {
        return ResponseEntity.ok(productoRepository.obtenerProductosEnAlerta());
    }
}
