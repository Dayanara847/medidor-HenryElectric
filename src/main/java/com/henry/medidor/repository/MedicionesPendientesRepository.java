package com.henry.medidor.repository;

import com.henry.medidor.model.MedicionesPendientes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicionesPendientesRepository extends JpaRepository<MedicionesPendientes, Integer> {
}
