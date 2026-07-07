package com.example.techmovil.dtos;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductoDTO {
    private Long idProducto;
    private String nombre;
    private String marca;
    private Double precio;
    private Integer stock;
}