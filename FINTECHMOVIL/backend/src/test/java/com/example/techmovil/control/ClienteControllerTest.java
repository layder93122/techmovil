package com.example.techmovil.control;

import com.example.techmovil.excepciones.CustomResponse;
import com.example.techmovil.modelo.Cliente;
import com.example.techmovil.servicio.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteControllerTest {

    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private ClienteController clienteController;

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        cliente = Cliente.builder()
                .id(1L)
                .nombre("Juan")
                .apellido("Perez")
                .numeroDocumento("12345678")
                .tipoDocumento("DNI")
                .activo(true)
                .build();
    }

    @Test
    void listar_RetornaListaClientes() {
        when(clienteService.findAll()).thenReturn(Arrays.asList(cliente));
        ResponseEntity<List<Cliente>> response = clienteController.listar();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void buscar_RetornaCliente() {
        when(clienteService.findById(1L)).thenReturn(cliente);
        ResponseEntity<Cliente> response = clienteController.buscar(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Juan", response.getBody().getNombre());
    }

    @Test
    void guardar_RetornaClienteCreado() {
        when(clienteService.save(any(Cliente.class))).thenReturn(cliente);
        ResponseEntity<Cliente> response = clienteController.guardar(cliente);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void actualizar_RetornaClienteActualizado() {
        when(clienteService.update(any(Cliente.class), eq(1L))).thenReturn(cliente);
        ResponseEntity<Cliente> response = clienteController.actualizar(cliente, 1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void eliminar_RetornaConfirmacion() {
        CustomResponse cr = CustomResponse.builder().statusCode(200).message("Exito").build();
        when(clienteService.delete(1L)).thenReturn(cr);
        ResponseEntity<CustomResponse> response = clienteController.eliminar(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Exito", response.getBody().getMessage());
    }
}
