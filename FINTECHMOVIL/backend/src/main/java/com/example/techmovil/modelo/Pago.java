package com.example.techmovil.modelo;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "pagos")
public class Pago implements Activable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String metodoPago; // "TARJETA", "YAPE", "PLIN", "EFECTIVO"
    private String transaccionId; // Código de operación simulado
    private String estadoPago; // "APROBADO", "RECHAZADO"
    private LocalDateTime fechaPago;

    @Column(name = "activo", nullable = false)
    private Boolean activo = true;
}