package com.example.techmovil.repositorio;

import com.example.techmovil.modelo.Usuario;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UsuarioRepository extends CrudGenericoRepository<Usuario, Long> {
    List<Usuario> findAllByActivoTrue();
    boolean existsByUsername(String username);
}