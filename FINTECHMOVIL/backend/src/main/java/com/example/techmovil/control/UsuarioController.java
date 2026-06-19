package com.example.techmovil.control;

import com.example.techmovil.excepciones.CustomResponse;
import com.example.techmovil.modelo.Usuario;
import com.example.techmovil.servicio.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@Tag(name = "Usuarios", description = "Administración de personal con eliminado lógico")
public class UsuarioController {

    private final UsuarioService service;

    @GetMapping
    @Operation(summary = "Listar usuarios activos")
    public ResponseEntity<List<Usuario>> listar() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuario por ID")
    public ResponseEntity<Usuario> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    @Operation(summary = "Crear usuario")
    public ResponseEntity<Usuario> guardar(@Valid @RequestBody Usuario usuario) {
        return ResponseEntity.ok(service.save(usuario));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar usuario")
    public ResponseEntity<Usuario> actualizar(@Valid @RequestBody Usuario usuario, @PathVariable Long id) {
        usuario.setIdUsuario(id);
        return ResponseEntity.ok(service.update(usuario, id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar usuario (lógico)")
    public ResponseEntity<CustomResponse> eliminar(@PathVariable Long id) {
        return ResponseEntity.ok(service.delete(id));
    }
}
