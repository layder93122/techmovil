package com.example.techmovil.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "clientes")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cliente implements Activable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacio")
    private String nombre;

    @NotBlank(message = "El apellido no puede estar vacio")
    private String apellido;

    @NotBlank(message = "El documento no puede estar vacio")
    @Column(name = "numero_documento", unique = true)
    private String numeroDocumento;

    @Builder.Default
    @Column(name = "tipo_documento")
    private String tipoDocumento = "DNI";

    private String telefono;

    @Email(message = "El email no es valido")
    private String email;

    private String direccion;

    @Builder.Default
    @Column(name = "activo", nullable = false)
    private Boolean activo = true;
}
