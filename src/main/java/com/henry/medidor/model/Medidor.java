package com.henry.medidor.model;

import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@Entity
public class Medidor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String marca;

    @NotNull
    private String modelo;

    @NotNull
    private String nroSerie;

    private Double consumoTotal = 0.0;

    private Boolean asignado = false;

}