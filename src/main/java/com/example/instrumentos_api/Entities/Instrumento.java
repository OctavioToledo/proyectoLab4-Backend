package com.example.instrumentos_api.Entities;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.bind.annotation.CrossOrigin;


@Entity
@Table(name ="instrumentos")
@CrossOrigin(origins = "*")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Instrumento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String instrumento;
    private String marca;
    private String modelo;
    private String imagen;
    private double precio;
    private String costoEnvio;
    private int cantidadVendida;
    private String descripcion;
    private String categoria;
    private boolean eliminado;

    private int quantity;


    // Getters and setters
}

