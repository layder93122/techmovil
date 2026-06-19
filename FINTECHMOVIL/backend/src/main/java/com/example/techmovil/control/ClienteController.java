package com.example.techmovil.control;

import com.example.techmovil.excepciones.CustomResponse;
import com.example.techmovil.modelo.Cliente;
import com.example.techmovil.servicio.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
@Tag(name = "Clientes", description = "Gestion de clientes con eliminado logico")
public class ClienteController {

    private final ClienteService clienteService;

    @GetMapping
    @Operation(summary = "Listar clientes activos")
    public ResponseEntity<List<Cliente>> listar() {
        return ResponseEntity.ok(clienteService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar cliente por ID")
    public ResponseEntity<Cliente> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Registrar cliente")
    public ResponseEntity<Cliente> guardar(@Valid @RequestBody Cliente cliente) {
        return ResponseEntity.ok(clienteService.save(cliente));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar cliente")
    public ResponseEntity<Cliente> actualizar(@Valid @RequestBody Cliente cliente, @PathVariable Long id) {
        cliente.setId(id);
        return ResponseEntity.ok(clienteService.update(cliente, id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar cliente (logico)")
    public ResponseEntity<CustomResponse> eliminar(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.delete(id));
    }
}
