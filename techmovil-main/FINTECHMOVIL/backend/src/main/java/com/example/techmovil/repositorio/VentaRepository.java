package com.example.techmovil.repositorio;

import com.example.techmovil.modelo.Venta;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VentaRepository extends CrudGenericoRepository<Venta, Long> {
    List<Venta> findAllByActivoTrue();
}