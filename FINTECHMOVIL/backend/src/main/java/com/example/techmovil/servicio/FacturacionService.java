package com.example.techmovil.servicio;

import com.example.techmovil.modelo.Factura;

public interface FacturacionService {
    Factura procesarVentaFacturada(Factura solicitudFactura);
}