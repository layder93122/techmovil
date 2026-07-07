package com.example.techmovil.excepciones;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomResponse {
    private int statusCode;
    private LocalDateTime datetime;
    private String message;
    private String details;
}