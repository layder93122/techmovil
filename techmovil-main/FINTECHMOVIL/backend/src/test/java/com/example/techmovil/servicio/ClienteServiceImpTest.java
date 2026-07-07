package com.example.techmovil.servicio;

import com.example.techmovil.modelo.Cliente;
import com.example.techmovil.repositorio.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceImpTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteServiceImp clienteService;

    private Cliente clienteActivo() {
        return Cliente.builder()
                .id(1L)
                .nombre("Juan")
                .apellido("Perez")
                .numeroDocumento("12345678")
                .tipoDocumento("DNI")
                .telefono("999888777")
                .email("juan@example.com")
                .activo(true)
                .build();
    }

    @Test
    void findAll_RetornaSoloClientesActivos() {
        when(clienteRepository.findAllByActivoTrue()).thenReturn(Arrays.asList(clienteActivo()));
        List<Cliente> resultado = clienteService.findAll();
        assertFalse(resultado.isEmpty());
        assertTrue(resultado.get(0).getActivo());
        verify(clienteRepository).findAllByActivoTrue();
    }

    @Test
    void findAll_SinClientes_RetornaListaVacia() {
        when(clienteRepository.findAllByActivoTrue()).thenReturn(Collections.emptyList());
        assertTrue(clienteService.findAll().isEmpty());
    }

    @Test
    void save_GuardaClienteCorrectamente() {
        Cliente c = clienteActivo();
        when(clienteRepository.save(any(Cliente.class))).thenReturn(c);
        Cliente guardado = clienteService.save(c);
        assertNotNull(guardado);
        assertEquals("Juan", guardado.getNombre());
        assertEquals("12345678", guardado.getNumeroDocumento());
    }

    @Test
    void getRepo_RetornaClienteRepository() {
        assertNotNull(clienteService.getRepo());
    }
}
