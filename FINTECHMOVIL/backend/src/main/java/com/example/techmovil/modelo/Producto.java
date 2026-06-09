package com.example.techmovil.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Entity
@Table(name = "productos")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "La marca no puede estar vacía")
    private String marca;
    private String modelo;

    @Positive(message = "El precio debe ser mayor a cero")
    private Double precio;
    private Integer stock;

    @Column(name = "imagen_url") // Mapeo explícito para la base de datos
    private String imagenUrl;

    @Column(name = "stock_minimo")
    private Integer stockMinimo = 3; // Por defecto inicia en 3

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "caracteristicas_id", referencedColumnName = "id")
    private Caracteristica caracteristicas;
}