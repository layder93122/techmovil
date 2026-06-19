package com.example.techmovil.servicio;

import com.example.techmovil.excepciones.CustomResponse;
import com.example.techmovil.modelo.Factura;
import java.util.List;

public interface FacturacionService {
    Factura procesarVentaFacturada(Factura solicitudFactura);
    List<Factura> findAll();
    Factura findById(Long id);
    CustomResponse delete(Long id);
}