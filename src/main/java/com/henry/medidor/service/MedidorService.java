package com.henry.medidor.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.henry.medidor.model.MedicionesPendientes;
import com.henry.medidor.model.Medidor;
import com.henry.medidor.repository.MedidorRepository;
import com.henry.medidor.utils.EntityURLBuilder;
import com.henry.medidor.utils.PostResponse;
import com.henry.medidor.utils.UpdateFields;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class MedidorService {

    private MedidorRepository medidorRepository;
    private MedicionesPendientesService medicionesPendientesService;
    private static final String MEDIDOR_PATH = "medidor";

    @Autowired
    public MedidorService(MedidorRepository medidorRepository, MedicionesPendientesService medicionesPendientesService) {
        this.medidorRepository = medidorRepository;
        this.medicionesPendientesService = medicionesPendientesService;
    }

    public PostResponse postMedidor(Medidor medidor) {
        final Medidor medidorSaved = medidorRepository.save(medidor);

        MedicionesPendientes medicionPendiente = new MedicionesPendientes();
        medicionPendiente.setIdMedidor(medidorSaved.getId());
        medicionPendiente.setNroSerieMedidor(medidorSaved.getNroSerie());
        medicionPendiente.setFechaHora(LocalDateTime.now().toString());
        medicionPendiente.setConsumo(0.0);
        medicionesPendientesService.addMedicionPendiente(medicionPendiente);

        return PostResponse.builder()
                .status(HttpStatus.CREATED)
                .url(EntityURLBuilder.buildURL(MEDIDOR_PATH, medidorSaved.getId().toString()))
                .build();
    }

    public Medidor getMedidor(Integer id) {
        return medidorRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "El medidor no fue encontrado"));
    }

    public void deleteMedidor(Integer id) {
        try {
            medidorRepository.deleteById(id);
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        }
    }

    public List<Medidor> getAllMedidores(Boolean asignado) {
        List<Medidor> medidoresList;
        if(asignado) {
            medidoresList = medidorRepository.findAllMedidoresAsignados();
        } else {
            medidoresList = medidorRepository.findAll();
        }
        if (medidoresList.size() == 0) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
        return medidoresList;
    }

    public void editMedidor(Medidor medidor) {
        medidorRepository.findById(medidor.getId()).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
        medidorRepository.save(medidor);
    }

    @CircuitBreaker(name = "medidores", fallbackMethod = "fallback")
    @Scheduled(cron = "0 0,5,10,15,20,25,30,35,40,45,50,55 * * * *")
    public void postMedidores() throws JsonProcessingException {
        List<Medidor> medidoresList = this.getAllMedidores(true);

        List<MedicionesPendientes> medicionesPendientesList = medicionesPendientesService.getAll();
        for (Medidor medidor :
                medidoresList) {
            MedicionesPendientes medicionPendiente = new MedicionesPendientes();
            Double random = medidor.getConsumoTotal() + Math.random() * 2;
            medidor.setConsumoTotal(random);
            medidorRepository.save(medidor);
            medicionPendiente.setIdMedidor(medidor.getId());
            medicionPendiente.setNroSerieMedidor(medidor.getNroSerie());
            medicionPendiente.setFechaHora(LocalDateTime.now().toString());
            medicionPendiente.setConsumo(medidor.getConsumoTotal());
            medicionesPendientesList.add(medicionPendiente);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(medicionesPendientesList);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/medicion/allmediciones"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        try {
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            medicionesPendientesService.addMedicionesPendientes(medicionesPendientesList);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void fallback(final Throwable t) {
        List<MedicionesPendientes> medicionesPendientesList = medicionesPendientesService.getAll();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/medicion/allmediciones"))
                .method("POST", HttpRequest.BodyPublishers.ofString(String.valueOf(medicionesPendientesList)))
                .build();

        try {
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void asignarMedidor(Integer idMedidor, Boolean status) {
        Medidor medidorAAsignar = medidorRepository.findById(idMedidor).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));

        Medidor medidorAsignado = new Medidor();
        medidorAsignado.setAsignado(status);

        medidorRepository.save((Medidor) UpdateFields.updateObject(medidorAsignado, medidorAAsignar));
    }
}
