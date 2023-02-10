package com.jmlessous.controllers;

import com.jmlessous.entities.*;
import com.jmlessous.services.IOffreAssuranceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Map;

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

    @PutMapping("updateoffre")
    @CrossOrigin(origins = "http://localhost:4200")
    public OffreAssurance updateOffre(@RequestBody OffreAssurance offre){
        return service.updateOffre(offre);
    }

    @DeleteMapping("deleteoffre/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public void deleteOffre(@PathVariable("id") Long id){
        service.deleteOffre(id);
    }

    @GetMapping("getAlloffresByType/{type}")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<OffreAssurance> getOffresByType(@PathVariable("type") TypeAssurance type){
        return service.getOffresByType(type);
    }

    @GetMapping("getAlloffresByCategorie/{categ}")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<OffreAssurance> getOffresByCateg(@PathVariable("categ") CategorieAssurance categ){
        return service.getOffresByCategorie(categ);
    }

    @GetMapping("getAllAvOffresByCategorie/{categ}")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<OffreAssurance> getAvOffresByCateg(@PathVariable("categ") CategorieAssurance categ){
        return service.getAvOffresByCategorie(categ);
    }

    @GetMapping("getAllPubOffresByCategorie/{categ}")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<OffreAssurance> getPubOffresByCateg(@PathVariable("categ") CategorieAssurance categ){
        return service.getPubOffresByCategorie(categ);
    }

    @GetMapping("getAlloffresByStatut/{statut}")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<OffreAssurance> getOffresByStatut(@PathVariable("statut") StatutAssurance statut){
        return service.getOffresByStatut(statut);
    }

    @GetMapping("getAlloffresByAssurance/{assurance}")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<OffreAssurance> getOffresByAssurance(@PathVariable("assurance") String assurance){
        return service.getOffresByAssurance(assurance);
    }

    @GetMapping("getAllAssurances")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<String> getAllAssurance(){
        return service.getAllAssurance();
    }

    @PostMapping("calculateAutPrime")
    @CrossOrigin(origins = "http://localhost:4200")
    public Float calculateAutPrime(@RequestBody ParamsOffreAssurance params) throws JSONException {
        return service.calculatePrime(params);
    }
}
