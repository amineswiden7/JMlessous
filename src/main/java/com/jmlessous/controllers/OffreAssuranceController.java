package com.jmlessous.controllers;

import com.jmlessous.entities.OffreAssurance;
import com.jmlessous.entities.TypeAssurance;
import com.jmlessous.services.IOffreAssuranceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
public class OffreAssuranceController {
    @Autowired
    IOffreAssuranceService service;


    @GetMapping("getAllOffres")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<OffreAssurance> getoffres(){
        return service.retrieveAllOffres();
    }

    @GetMapping("getOffre/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public OffreAssurance getOffreById(@PathVariable("id") Long id){
        return service.retrieveOffre(id);
    }

    @PostMapping("addOffre")
    @CrossOrigin(origins = "http://localhost:4200")
    public OffreAssurance addOffre(@RequestBody OffreAssurance offre/*, @RequestParam("file") MultipartFile file*/){
        return service.addOffre(offre,null);
    }

    @PutMapping("updateoffre}")
    @CrossOrigin(origins = "http://localhost:4200")
    public OffreAssurance updateOffre(@RequestBody OffreAssurance offre){
        return service.updateOffre(offre);
    }

    @DeleteMapping("deleteoffre/{id}")
    public void deleteOffre(@PathVariable("id") Long id){
        service.deleteOffre(id);
    }

    @GetMapping("getAlloffresByType/{type}")
    public List<OffreAssurance> getOffresByType(@PathVariable("type") TypeAssurance type){
        return service.getOffresByType(type);
    }

    @GetMapping("getAlloffresByAssurance/{assurance}")
    public List<OffreAssurance> getOffresByAssurance(@PathVariable("assurance") String assurance){
        return service.getOffresByAssurance(assurance);
    }

    @GetMapping("getAllAssurances")
    public List<String> getAllAssurance(){
        return service.getAllAssurance();
    }
}
