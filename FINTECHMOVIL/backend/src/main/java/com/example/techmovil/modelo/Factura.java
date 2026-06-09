package com.example.techmovil.modelo;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "facturas")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String numeroFactura; // Formato "FAC-XXXXXX"

    private String cliente;
    private LocalDateTime fechaEmision;
    private Double subtotal;
    private Double igv; // 18%
    private Double total;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pago_id", referencedColumnName = "id")
    private Pago detallePago;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "factura_id") // Une la llave foránea en detalles_factura
    private List<DetalleFactura> detalles;
}