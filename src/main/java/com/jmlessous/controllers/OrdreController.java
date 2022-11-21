package com.jmlessous.controllers;

import com.jmlessous.entities.Ordre;
import com.jmlessous.services.IOrdreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrdreController {
    @Autowired
    IOrdreService service;

    @GetMapping("getAllOrdres/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<Ordre> getOrders(@PathVariable("id") Long id){
        return service.retrieveOrdres(id);
    }

    @GetMapping("getOrdre/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public Ordre getOrdreById(@PathVariable("id") Long id){
        return service.retrieveOrdre(id);
    }

    @PostMapping("addOrdre")
    @CrossOrigin(origins = "http://localhost:4200")
    public Ordre addOrdre(@RequestBody Ordre ordre){
        return service.addOrdre(ordre);
    }
}
