package com.example.techmovil.repositorio;

import com.example.techmovil.modelo.Cliente;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends CrudGenericoRepository<Cliente, Long> {

    List<Cliente> findAllByActivoTrue();

    boolean existsByNumeroDocumento(String numeroDocumento);
}
