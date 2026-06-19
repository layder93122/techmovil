package com.example.techmovil.servicio;

import com.example.techmovil.modelo.Cliente;
import com.example.techmovil.repositorio.ClienteRepository;
import com.example.techmovil.repositorio.CrudGenericoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteServiceImp extends CrudGenericoServiceImp<Cliente, Long> implements ClienteService {

    private final ClienteRepository clienteRepository;

    @Override
    protected CrudGenericoRepository<Cliente, Long> getRepo() {
        return clienteRepository;
    }

    @Override
    public List<Cliente> findAll() {
        return clienteRepository.findAllByActivoTrue();
    }
}
