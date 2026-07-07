package com.example.techmovil.repositorio;

import com.example.techmovil.modelo.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long>, CrudGenericoRepository<Producto, Long> {

    List<Producto> findAllByActivoTrue();

    @Query("SELECT p FROM Producto p WHERE p.stock <= p.stockMinimo AND p.activo = true")
    List<Producto> obtenerProductosEnAlerta();
}