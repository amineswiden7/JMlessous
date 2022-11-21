package com.jmlessous.controllers;

import com.jmlessous.entities.Ordre;
import com.jmlessous.entities.ProduitFinancier;
import com.jmlessous.entities.ProduitFinancier;
import com.jmlessous.repositories.ProduitFinancierRepository;
import com.jmlessous.services.IProduitFinancierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProduitFinancierController {
    @Autowired
    IProduitFinancierService service;

    @GetMapping("getAllProduits")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<ProduitFinancier> getProduits(){
        return service.retrieveAllProduits();
    }

    @GetMapping("getProduit/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ProduitFinancier getProduitById(@PathVariable("id") Long id){
        return service.retrieveProduit(id);
    }

   /* @PostMapping("addProduit/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ProduitFinancier addProduit(@RequestBody ProduitFinancier produit,@PathVariable("id") Long id){
        System.out.println("hereeee");
        return service.addProduit(produit,id);
    }*/

    @PostMapping("addProduit/{id}/{prix}/{qte}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ProduitFinancier addProduit(@RequestBody ProduitFinancier produit, @PathVariable("id") Long id,@PathVariable("prix") float prix,@PathVariable("qte") Long qte){
        System.out.println("hereeee");
        return service.addProduit(produit,prix,qte,id);
    }

    @PutMapping("vendre/{id}/{prix}/{qte}")
    @CrossOrigin(origins = "http://localhost:4200")
    public void updateProduit(@RequestBody ProduitFinancier produit, @PathVariable("id") Long id,@PathVariable("prix") float prix,@PathVariable("qte") Long qte){
         service.vendreProduit(produit,prix,qte,id);
    }

}
