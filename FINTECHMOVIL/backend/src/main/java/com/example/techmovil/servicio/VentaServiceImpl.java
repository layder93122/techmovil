package com.example.techmovil.servicio;

import com.example.techmovil.excepciones.StockInsuficienteException;
import com.example.techmovil.modelo.Producto;
import com.example.techmovil.modelo.Venta;
import com.example.techmovil.repositorio.CrudGenericoRepository;
import com.example.techmovil.repositorio.ProductoRepository;
import com.example.techmovil.repositorio.VentaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VentaServiceImpl extends CrudGenericoServiceImp<Venta, Long> implements VentaService {

    private final VentaRepository repo;
    private final ProductoRepository productoRepo;

    @Override
    protected CrudGenericoRepository<Venta, Long> getRepo() { return repo; }

    @Override
    public List<Venta> findAll() {
        return repo.findAllByActivoTrue();
    }

    @Override
    @Transactional
    public Venta save(Venta venta) {
        if (venta.getProducto() == null || venta.getProducto().getId() == null) {
            throw new IllegalArgumentException("La venta debe incluir un producto con ID valido");
        }

        Producto producto = productoRepo.findById(venta.getProducto().getId())
                .filter(p -> Boolean.TRUE.equals(p.getActivo()))
                .orElseThrow(() -> new EntityNotFoundException(
                        "Producto no encontrado con el ID especificado"));

        int cantidad = venta.getCantidad() != null ? venta.getCantidad() : 1;

        if (producto.getStock() < cantidad) {
            throw new StockInsuficienteException(
                    "No hay stock disponible para: " + producto.getModelo()
                    + ". Stock actual: " + producto.getStock()
                    + ", Solicitado: " + cantidad);
        }

        producto.setStock(producto.getStock() - cantidad);
        productoRepo.save(producto);
        venta.setTotal(producto.getPrecio() * cantidad);
        return repo.save(venta);
    }
}
