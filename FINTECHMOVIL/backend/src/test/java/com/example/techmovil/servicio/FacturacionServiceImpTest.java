package com.example.techmovil.servicio;

import com.example.techmovil.excepciones.CarritoVacioException;
import com.example.techmovil.excepciones.StockInsuficienteException;
import com.example.techmovil.modelo.DetalleFactura;
import com.example.techmovil.modelo.Factura;
import com.example.techmovil.modelo.Pago;
import com.example.techmovil.modelo.Producto;
import com.example.techmovil.repositorio.FacturaRepository;
import com.example.techmovil.repositorio.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FacturacionServiceImpTest {

    @Mock private FacturaRepository facturaRepository;
    @Mock private ProductoRepository productoRepository;
    @InjectMocks private FacturacionServiceImp facturacionService;

    private Producto producto;
    private DetalleFactura detalle;
    private Factura factura;

    @BeforeEach
    void setUp() {
        producto = Producto.builder().id(1L).marca("Apple").modelo("iPhone 15")
                .precio(4500.0).stock(10).build();
        detalle = DetalleFactura.builder()
                .producto(Producto.builder().id(1L).build()).cantidad(2).build();
        Pago pago = new Pago(); pago.setMetodoPago("TARJETA");
        factura = Factura.builder().numeroFactura("FAC-000001").cliente("Maria Quispe")
                .detalles(Arrays.asList(detalle)).detallePago(pago).build();
    }

    @Test
    void procesarVentaFacturada_Exitoso_RetornaFactura() {
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));
        when(productoRepository.save(any())).thenReturn(producto);
        when(facturaRepository.save(any())).thenReturn(factura);
        assertNotNull(facturacionService.procesarVentaFacturada(factura));
    }

    @Test
    void procesarVentaFacturada_CalculaTotal9000() {
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));
        when(productoRepository.save(any())).thenReturn(producto);
        when(facturaRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);
        assertEquals(9000.0, facturacionService.procesarVentaFacturada(factura).getTotal(), 0.01);
    }

    @Test
    void procesarVentaFacturada_CalculaIgvCorrectamente() {
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));
        when(productoRepository.save(any())).thenReturn(producto);
        when(facturaRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);
        Factura r = facturacionService.procesarVentaFacturada(factura);
        assertEquals(r.getTotal() - r.getSubtotal(), r.getIgv(), 0.01);
    }

    @Test
    void procesarVentaFacturada_DescuentaStock() {
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));
        when(productoRepository.save(any())).thenReturn(producto);
        when(facturaRepository.save(any())).thenReturn(factura);
        facturacionService.procesarVentaFacturada(factura);
        verify(productoRepository).save(argThat(p -> p.getStock() == 8));
    }

    @Test
    void procesarVentaFacturada_SinDetalles_LanzaCarritoVacioException() {
        factura.setDetalles(Collections.emptyList());
        assertThrows(CarritoVacioException.class, () -> facturacionService.procesarVentaFacturada(factura));
    }

    @Test
    void procesarVentaFacturada_DetallesNulos_LanzaCarritoVacioException() {
        factura.setDetalles(null);
        assertThrows(CarritoVacioException.class, () -> facturacionService.procesarVentaFacturada(factura));
    }

    @Test
    void procesarVentaFacturada_DetalleProductoNulo_LanzaCarritoVacioException() {
        detalle.setProducto(null);
        assertThrows(CarritoVacioException.class, () -> facturacionService.procesarVentaFacturada(factura));
    }

    @Test
    void procesarVentaFacturada_ProductoNoExistente_LanzaEntityNotFoundException() {
        when(productoRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(jakarta.persistence.EntityNotFoundException.class,
                () -> facturacionService.procesarVentaFacturada(factura));
    }

    @Test
    void procesarVentaFacturada_ProductoInactivo_LanzaEntityNotFoundException() {
        producto.setActivo(false);
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));
        assertThrows(jakarta.persistence.EntityNotFoundException.class,
                () -> facturacionService.procesarVentaFacturada(factura));
    }

    @Test
    void procesarVentaFacturada_StockInsuficiente_LanzaException() {
        producto.setStock(1);
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));
        assertThrows(StockInsuficienteException.class, () -> facturacionService.procesarVentaFacturada(factura));
    }

    @Test
    void procesarVentaFacturada_ProductoIdNulo_LanzaCarritoVacioException() {
        detalle.setProducto(Producto.builder().build());
        assertThrows(CarritoVacioException.class, () -> facturacionService.procesarVentaFacturada(factura));
    }

    @Test
    void procesarVentaFacturada_FechaEmisionNull_SetFechaActual() {
        factura.setFechaEmision(null);
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));
        when(productoRepository.save(any())).thenReturn(producto);
        when(facturaRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);
        assertNotNull(facturacionService.procesarVentaFacturada(factura).getFechaEmision());
    }

    @Test
    void findAll_RetornaListaDeFacturasActivas() {
        when(facturaRepository.findAllByActivoTrueWithDetalles()).thenReturn(Arrays.asList(factura));
        assertEquals(1, facturacionService.findAll().size());
    }

    @Test
    void findAll_SinFacturas_RetornaListaVacia() {
        when(facturaRepository.findAllByActivoTrueWithDetalles()).thenReturn(Collections.emptyList());
        assertTrue(facturacionService.findAll().isEmpty());
    }

    @Test
    void procesarVentaFacturada_FechaExistente_NoSePisa() {
        LocalDateTime fechaFija = LocalDateTime.of(2026, 1, 15, 10, 30);
        factura.setFechaEmision(fechaFija);
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));
        when(productoRepository.save(any())).thenReturn(producto);
        when(facturaRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);
        assertEquals(fechaFija, facturacionService.procesarVentaFacturada(factura).getFechaEmision());
    }

    @Test
    void findById_FacturaExistente_RetornaFactura() {
        when(facturaRepository.findByIdWithDetalles(1L)).thenReturn(Optional.of(factura));

        Factura resultado = facturacionService.findById(1L);

        assertNotNull(resultado);
        assertEquals("FAC-000001", resultado.getNumeroFactura());
    }

    @Test
    void findById_FacturaNoExistente_LanzaEntityNotFoundException() {
        when(facturaRepository.findByIdWithDetalles(99L)).thenReturn(Optional.empty());

        assertThrows(jakarta.persistence.EntityNotFoundException.class,
                () -> facturacionService.findById(99L));
    }

    @Test
    void delete_FacturaExistente_AnulaYRetornaResponse() {
        when(facturaRepository.findByIdWithDetalles(1L)).thenReturn(Optional.of(factura));
        when(facturaRepository.save(any())).thenReturn(factura);

        com.example.techmovil.excepciones.CustomResponse response = facturacionService.delete(1L);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode());
        assertTrue(response.getMessage().contains("anulada"));
        verify(facturaRepository).save(argThat(f -> Boolean.FALSE.equals(f.getActivo())));
    }

    @Test
    void delete_FacturaNoExistente_LanzaEntityNotFoundException() {
        when(facturaRepository.findByIdWithDetalles(99L)).thenReturn(Optional.empty());

        assertThrows(jakarta.persistence.EntityNotFoundException.class,
                () -> facturacionService.delete(99L));
    }
}
