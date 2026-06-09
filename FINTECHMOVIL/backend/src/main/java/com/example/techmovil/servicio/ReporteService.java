package com.example.techmovil.servicio;

import com.example.techmovil.repositorio.VentaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReporteService {

    private final VentaRepository ventaRepository;

    public Double calcularIngresosTotales() {
        return ventaRepository.findAll().stream()
                .mapToDouble(v -> {
                    int cantidad = v.getCantidad() != null ? v.getCantidad() : 1;
                    double precio = (v.getProducto() != null && v.getProducto().getPrecio() != null)
                            ? v.getProducto().getPrecio() : 0.0;
                    return cantidad * precio;
                })
                .sum();
    }
}
