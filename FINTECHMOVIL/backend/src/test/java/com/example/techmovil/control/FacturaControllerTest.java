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
    void facturaController_EsInstanciable() {
        assertNotNull(facturaController);
    }
}
