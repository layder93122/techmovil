package com.example.techmovil.modelo;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "caracteristicas")
public class Caracteristica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String procesador;
    private String ram;
    private String almacenamiento;
    private String bateria;
    private String camaras;
    private String pantalla;
}