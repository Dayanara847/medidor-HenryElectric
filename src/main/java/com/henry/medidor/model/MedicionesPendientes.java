package com.henry.medidor.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MedicionesPendientes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idMP;

    private Integer idMedidor;

    private String nroSerieMedidor;

    private String fechaHora;

    private Double consumo;

}
