package com.example.techmovil.servicio;

import com.example.techmovil.excepciones.CarritoVacioException;
import com.example.techmovil.excepciones.CustomResponse;
import com.example.techmovil.excepciones.StockInsuficienteException;
import com.example.techmovil.modelo.DetalleFactura;
import com.example.techmovil.modelo.Factura;
import com.example.techmovil.modelo.Producto;
import com.example.techmovil.repositorio.FacturaRepository;
import com.example.techmovil.repositorio.ProductoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FacturacionServiceImp implements FacturacionService {

    private final FacturaRepository facturaRepository;
    private final ProductoRepository productoRepository;

    @Override
    @Transactional
    public Factura procesarVentaFacturada(Factura solicitudFactura) {
        if (solicitudFactura.getDetalles() == null || solicitudFactura.getDetalles().isEmpty()) {
            throw new CarritoVacioException("La factura no contiene ningun detalle o articulo.");
        }

        double subtotalAcumulado = 0.0;

        for (DetalleFactura detalle : solicitudFactura.getDetalles()) {
            if (detalle.getProducto() == null || detalle.getProducto().getId() == null) {
                throw new CarritoVacioException("Cada detalle debe incluir un producto con ID valido.");
            }

            Producto producto = productoRepository.findById(detalle.getProducto().getId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "El producto con ID " + detalle.getProducto().getId() + " no existe."));

            if (producto.getStock() < detalle.getCantidad()) {
                throw new StockInsuficienteException(
                        "Stock insuficiente para: " + producto.getModelo()
                        + ". Stock actual: " + producto.getStock()
                        + ", Solicitado: " + detalle.getCantidad());
            }

            producto.setStock(producto.getStock() - detalle.getCantidad());
            productoRepository.save(producto);
            detalle.setPrecioUnitario(producto.getPrecio());
            detalle.setSubtotal(producto.getPrecio() * detalle.getCantidad());
            subtotalAcumulado += detalle.getSubtotal();
        }

        solicitudFactura.setTotal(subtotalAcumulado);
        solicitudFactura.setSubtotal(subtotalAcumulado / 1.18);
        solicitudFactura.setIgv(subtotalAcumulado - solicitudFactura.getSubtotal());

        if (solicitudFactura.getFechaEmision() == null) {
            solicitudFactura.setFechaEmision(LocalDateTime.now());
        }

        return facturaRepository.save(solicitudFactura);
    }

    @Override
    public List<Factura> findAll() {
        return facturaRepository.findAllByActivoTrue();
    }

    @Override
    public Factura findById(Long id) {
        return facturaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Factura no encontrada con ID: " + id));
    }

    @Override
    @Transactional
    public CustomResponse delete(Long id) {
        Factura factura = findById(id);
        factura.setActivo(false);
        facturaRepository.save(factura);
        return CustomResponse.builder()
                .statusCode(200)
                .datetime(java.time.LocalDateTime.now())
                .message("Factura anulada correctamente")
                .build();
    }
}
