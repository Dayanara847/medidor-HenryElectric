package com.henry.medidor.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.henry.medidor.model.Medidor;
import com.henry.medidor.service.MedidorService;
import com.henry.medidor.utils.PostResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/medidor")
public class MedidorController {

    @Autowired
    private MedidorService medidorService;

    @PostMapping
    public PostResponse postMedidor(@RequestBody Medidor medidor) {
        return medidorService.postMedidor(medidor);
    }

    @GetMapping("/{id}")
    public Medidor getMedidor(@PathVariable Integer id) {
        return medidorService.getMedidor(id);
    }

    @GetMapping
    public List<Medidor> getAllMedidores() {
        return medidorService.getAllMedidores(false);
    }

    @PostMapping("/medidasList")
    public void postMedidores() {
        try {
            medidorService.postMedidores();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteMedidor(@PathVariable Integer id) {
        medidorService.deleteMedidor(id);
    }

    @PutMapping
    public void editMedidor(@RequestBody Medidor medidor) {
        medidorService.editMedidor(medidor);
    }

    @PutMapping("/asignado/{idMedidor}/{status}")
    public void asignarMedidor(@PathVariable Integer idMedidor, @PathVariable Boolean status) {
        medidorService.asignarMedidor(idMedidor, status);
    }

    //    @GetMapping("/{idMedidor}")
    //    private Double getMedicionFromMedidor(@PathVariable Integer idMedidor){
    //        return medidorService.getMedicionFromMedidor(idMedidor);
    //    }
}

