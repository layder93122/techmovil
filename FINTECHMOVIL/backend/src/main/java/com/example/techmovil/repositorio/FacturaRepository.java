package com.example.techmovil.repositorio;

import com.example.techmovil.modelo.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long> {

    // Ventas del día actual y previos
    @Query(value = "SELECT DATE(fecha_emision) as periodo, SUM(total) as ingresos, COUNT(id) as total_ventas FROM facturas GROUP BY DATE(fecha_emision) ORDER BY periodo DESC", nativeQuery = true)
    List<Map<String, Object>> obtenerVentasDiarias();

    // Ventas agrupadas por semana epidemiológica/anual
    @Query(value = "SELECT CONCAT('Semana ', WEEK(fecha_emision)) as periodo, SUM(total) as ingresos, COUNT(id) as total_ventas FROM facturas GROUP BY WEEK(fecha_emision) ORDER BY WEEK(fecha_emision) DESC", nativeQuery = true)
    List<Map<String, Object>> obtenerVentasSemanales();

    // Ventas mensuales del año en curso
    @Query(value = "SELECT DATE_FORMAT(fecha_emision, '%Y-%m') as periodo, SUM(total) as ingresos, COUNT(id) as total_ventas FROM facturas GROUP BY DATE_FORMAT(fecha_emision, '%Y-%m') ORDER BY periodo DESC", nativeQuery = true)
    List<Map<String, Object>> obtenerVentasMensuales();

    // Ventas globales anuales
    @Query(value = "SELECT YEAR(fecha_emision) as periodo, SUM(total) as ingresos, COUNT(id) as total_ventas FROM facturas GROUP BY YEAR(fecha_emision) ORDER BY periodo DESC", nativeQuery = true)
    List<Map<String, Object>> obtenerVentasAnuales();
}