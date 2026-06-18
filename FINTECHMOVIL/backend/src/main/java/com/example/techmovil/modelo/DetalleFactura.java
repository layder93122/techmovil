package com.example.techmovil.modelo;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "detalles_factura")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DetalleFactura implements Activable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    private Integer cantidad;
    private Double precioUnitario;
    private Double subtotal;

    @Builder.Default
    @Column(name = "activo", nullable = false)
    private Boolean activo = true;
}