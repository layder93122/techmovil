package com.example.techmovil.servicio;

import com.example.techmovil.modelo.Producto;
import com.example.techmovil.repositorio.CrudGenericoRepository;
import com.example.techmovil.repositorio.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductoServiceImp extends CrudGenericoServiceImp<Producto, Long> implements ProductoService {

    private final ProductoRepository repo;

    @Override
    protected CrudGenericoRepository<Producto, Long> getRepo() {
        return repo;
    }
}
