package com.henry.medidor.service;

import com.henry.medidor.model.MedicionesPendientes;
import com.henry.medidor.repository.MedicionesPendientesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicionesPendientesService {

    @Autowired
    private MedicionesPendientesRepository medicionesPendientesRepository;

    public void addMedicionPendiente(MedicionesPendientes medicion) {
        medicionesPendientesRepository.save(medicion);
    }

    public void addMedicionesPendientes(List<MedicionesPendientes> medicionesPendientesList) {
            medicionesPendientesRepository.saveAll(medicionesPendientesList);
    }

    public List<MedicionesPendientes> getAll() {
        return medicionesPendientesRepository.findAll();
    }

}
