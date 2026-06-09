package com.example.techmovil.repositorio;

import com.example.techmovil.modelo.Venta;
import org.springframework.stereotype.Repository;

@Repository
public interface VentaRepository extends CrudGenericoRepository<Venta, Long> {
}