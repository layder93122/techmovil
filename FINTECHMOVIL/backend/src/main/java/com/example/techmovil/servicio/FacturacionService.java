package com.example.techmovil.servicio;

import com.example.techmovil.modelo.Factura;
import java.util.List;

public interface FacturacionService {
    Factura procesarVentaFacturada(Factura solicitudFactura);
    List<Factura> findAll();
}