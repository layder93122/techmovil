package com.example.techmovil.repositorio;

import com.example.techmovil.modelo.Usuario;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends CrudGenericoRepository<Usuario, Long> {
    // Aquí puedes agregar métodos personalizados después, como buscar por username
}