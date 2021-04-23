package com.henry.medidor.repository;

import com.henry.medidor.model.Medidor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedidorRepository extends JpaRepository<Medidor, Integer> {

    @Query(value = "SELECT * FROM medidor WHERE asignado = 1", nativeQuery = true)
    public List<Medidor> findAllMedidoresAsignados();

}

