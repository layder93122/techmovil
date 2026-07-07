package com.example.techmovil.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Entity
@Table(name = "productos")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Producto implements Activable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "La marca no puede estar vacia")
    private String marca;

    @NotBlank(message = "El modelo no puede estar vacio")
    private String modelo;

    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser mayor a cero")
    private Double precio;

    @NotNull(message = "El stock es obligatorio")
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock;

    @Column(name = "imagen_url") // Mapeo explícito para la base de datos
    private String imagenUrl;

    @Builder.Default
    @Column(name = "stock_minimo")
    private Integer stockMinimo = 3;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "caracteristicas_id", referencedColumnName = "id")
    private Caracteristica caracteristicas;

    @Builder.Default
    @Column(name = "activo", nullable = false)
    private Boolean activo = true;
}