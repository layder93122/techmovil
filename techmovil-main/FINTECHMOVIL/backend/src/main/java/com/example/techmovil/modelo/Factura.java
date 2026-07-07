package com.example.techmovil.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "facturas")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Factura implements Activable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El numero de factura es obligatorio")
    @Column(unique = true, nullable = false)
    private String numeroFactura;

    @NotBlank(message = "El nombre del cliente es obligatorio")
    private String cliente;
    private LocalDateTime fechaEmision;
    private Double subtotal;
    private Double igv; // 18%
    private Double total;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pago_id", referencedColumnName = "id")
    private Pago detallePago;

    @NotEmpty(message = "La factura debe tener al menos un producto")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "factura_id")
    private List<DetalleFactura> detalles;

    @Builder.Default
    @Column(name = "activo", nullable = false)
    private Boolean activo = true;
}