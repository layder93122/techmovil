package com.example.techmovil.servicio;

import com.example.techmovil.modelo.Producto;
import com.example.techmovil.modelo.Venta;
import com.example.techmovil.repositorio.VentaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReporteServiceTest {

    @Mock private VentaRepository ventaRepository;
    @InjectMocks private ReporteService reporteService;

    @Test
    void calcularIngresosTotales_ConVentas_RetornaSuma() {
        Venta v1 = Venta.builder().cantidad(2).producto(Producto.builder().precio(1000.0).build()).build();
        Venta v2 = Venta.builder().cantidad(1).producto(Producto.builder().precio(2000.0).build()).build();
        when(ventaRepository.findAllByActivoTrue()).thenReturn(Arrays.asList(v1, v2));
        assertEquals(4000.0, reporteService.calcularIngresosTotales(), 0.01);
    }

    @Test
    void calcularIngresosTotales_SinVentas_RetornaCero() {
        when(ventaRepository.findAllByActivoTrue()).thenReturn(Collections.emptyList());
        assertEquals(0.0, reporteService.calcularIngresosTotales(), 0.01);
    }

    @Test
    void calcularIngresosTotales_CantidadNula_UsaUno() {
        Venta v = Venta.builder().cantidad(null).producto(Producto.builder().precio(500.0).build()).build();
        when(ventaRepository.findAllByActivoTrue()).thenReturn(Arrays.asList(v));
        assertEquals(500.0, reporteService.calcularIngresosTotales(), 0.01);
    }

    @Test
    void calcularIngresosTotales_ProductoNull_ContribueCero() {
        Venta v = Venta.builder().cantidad(3).producto(null).build();
        when(ventaRepository.findAllByActivoTrue()).thenReturn(Arrays.asList(v));
        assertEquals(0.0, reporteService.calcularIngresosTotales(), 0.01);
    }

    @Test
    void calcularIngresosTotales_PrecioNull_ContribueCero() {
        Venta v = Venta.builder().cantidad(2).producto(Producto.builder().precio(null).build()).build();
        when(ventaRepository.findAllByActivoTrue()).thenReturn(Arrays.asList(v));
        assertEquals(0.0, reporteService.calcularIngresosTotales(), 0.01);
    }

    @Test
    void reporteService_EsInstanciable() {
        assertNotNull(reporteService);
    }
}
