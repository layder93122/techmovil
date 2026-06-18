package com.example.techmovil.servicio;

import com.example.techmovil.modelo.Usuario;
import com.example.techmovil.repositorio.CrudGenericoRepository;
import com.example.techmovil.repositorio.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImp extends CrudGenericoServiceImp<Usuario, Long> implements UsuarioService {

    private final UsuarioRepository repo;

    @Override
    protected CrudGenericoRepository<Usuario, Long> getRepo() {
        return repo;
    }

    @Override
    public List<Usuario> findAll() {
        return repo.findAllByActivoTrue();
    }
}
