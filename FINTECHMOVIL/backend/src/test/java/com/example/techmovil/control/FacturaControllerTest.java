package com.example.techmovil.control;

import com.example.techmovil.excepciones.CarritoVacioException;
import com.example.techmovil.excepciones.StockInsuficienteException;
import com.example.techmovil.modelo.Factura;
import com.example.techmovil.servicio.FacturacionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FacturaControllerTest {

    @Mock
    private FacturacionService facturacionService;

    @InjectMocks
    private FacturaController facturaController;

    private Factura factura;

    @BeforeEach
    void setUp() {
        factura = new Factura();
        factura.setId(1L);
        factura.setNumeroFactura("FAC-000001");
        factura.setCliente("Juan Perez");
        factura.setTotal(1180.0);
        factura.setSubtotal(1000.0);
        factura.setIgv(180.0);
    }

    @Test
    void emitirFactura_Exitoso_Retorna200() {
        when(facturacionService.procesarVentaFacturada(any(Factura.class))).thenReturn(factura);

        ResponseEntity<Object> response = facturaController.emitirFactura(factura);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void emitirFactura_Exitoso_RetornaFacturaProcesada() {
        when(facturacionService.procesarVentaFacturada(any(Factura.class))).thenReturn(factura);

        ResponseEntity<Object> response = facturaController.emitirFactura(factura);

        Factura resultado = (Factura) response.getBody();
        assertEquals("FAC-000001", resultado.getNumeroFactura());
    }

    @Test
    void emitirFactura_StockInsuficiente_Retorna400() {
        // ← StockInsuficienteException (no RuntimeException genérica)
        when(facturacionService.procesarVentaFacturada(any(Factura.class)))
                .thenThrow(new StockInsuficienteException("Stock insuficiente"));

        ResponseEntity<Object> response = facturaController.emitirFactura(factura);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void emitirFactura_StockInsuficiente_RetornaMensajeError() {
        // ← StockInsuficienteException con mensaje concreto
        when(facturacionService.procesarVentaFacturada(any(Factura.class)))
                .thenThrow(new StockInsuficienteException("Stock insuficiente para el celular"));

        ResponseEntity<Object> response = facturaController.emitirFactura(factura);

        assertEquals("Stock insuficiente para el celular", response.getBody());
    }

    @Test
    void emitirFactura_CarritoVacio_Retorna400() {
        // ← CarritoVacioException también queda cubierta
        when(facturacionService.procesarVentaFacturada(any(Factura.class)))
                .thenThrow(new CarritoVacioException("La factura no contiene artículos"));

        ResponseEntity<Object> response = facturaController.emitirFactura(factura);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("La factura no contiene artículos", response.getBody());
    }

    @Test
    void emitirFactura_LlamaAlServicio() {
        when(facturacionService.procesarVentaFacturada(any(Factura.class))).thenReturn(factura);

        facturaController.emitirFactura(factura);

        verify(facturacionService, times(1)).procesarVentaFacturada(factura);
    }

    @Test
    void listar_RetornaListaFacturas() {
        when(facturacionService.findAll()).thenReturn(java.util.Arrays.asList(factura));

        ResponseEntity<Object> response = facturaController.listar();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void listar_LlamaAlServicio() {
        when(facturacionService.findAll()).thenReturn(java.util.Collections.emptyList());

        facturaController.listar();

        verify(facturacionService, times(1)).findAll();
    }

    @Test
    void buscar_RetornaFacturaPorId() {
        when(facturacionService.findById(1L)).thenReturn(factura);

        ResponseEntity<Object> response = facturaController.buscar(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Factura resultado = (Factura) response.getBody();
        assertEquals("FAC-000001", resultado.getNumeroFactura());
    }

    @Test
    void buscar_LlamaAlServicioConId() {
        when(facturacionService.findById(1L)).thenReturn(factura);

        facturaController.buscar(1L);

        verify(facturacionService, times(1)).findById(1L);
    }

    @Test
    void anular_RetornaCustomResponse() {
        com.example.techmovil.excepciones.CustomResponse cr =
            com.example.techmovil.excepciones.CustomResponse.builder()
                .statusCode(200).message("Factura anulada correctamente").build();
        when(facturacionService.delete(1L)).thenReturn(cr);

        ResponseEntity<com.example.techmovil.excepciones.CustomResponse> response = facturaController.anular(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Factura anulada correctamente", response.getBody().getMessage());
    }

    @Test
    void anular_LlamaAlServicioConId() {
        com.example.techmovil.excepciones.CustomResponse cr =
            com.example.techmovil.excepciones.CustomResponse.builder()
                .statusCode(200).message("OK").build();
        when(facturacionService.delete(1L)).thenReturn(cr);

        facturaController.anular(1L);

        verify(facturacionService, times(1)).delete(1L);
    }

    @Test
    void emitirFactura_EntityNotFound_Retorna400() {
        when(facturacionService.procesarVentaFacturada(any(Factura.class)))
                .thenThrow(new jakarta.persistence.EntityNotFoundException("Producto no encontrado"));

        ResponseEntity<Object> response = facturaController.emitirFactura(factura);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Producto no encontrado", response.getBody());
    }

    @Test
    void facturaController_EsInstanciable() {
        assertNotNull(facturaController);
    }
}
