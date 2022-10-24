package com.jmlessous.controllers;

import com.jmlessous.entities.Compte;
import com.jmlessous.entities.CompteCourant;
import com.jmlessous.entities.Garantie;
import com.jmlessous.services.ICompteService;
import com.jmlessous.services.IGarantieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class GarantieController {

    @Autowired
    IGarantieService garantieService;

    //http://localhost:8083/JMLessous/ListGarantie
    @GetMapping("/ListGarantie")
    @ResponseBody
    public List<Garantie> getAllAcounts() {
        List <Garantie> list=garantieService.retrieveAllAbsences();
        return list ;
    }

    //http://localhost:8083/JMLessous/AddGarantie
    @PostMapping("/AddGarantie")
    @ResponseBody
    public Garantie AddAccount (@RequestBody Garantie c )
    {
        return garantieService.addGarantie(c);
    }

    // http://localhost:8083/JMLessous/modify-Garantie/i
    @PutMapping("/modify-Garantie/{id}")
    @ResponseBody
    public Garantie modifyAccount(@RequestBody Garantie g,@PathVariable("id") Long id ) {
        return garantieService.updateGarantie(g,id);
    }

    //http://localhost:8083/JMLessous/DeleteGarantie/1
    @DeleteMapping("/DeleteGarantie/{id}")
    @ResponseBody
    public void deleteAcc (@PathVariable("id") Long id ) {
        garantieService.deleteGarantie(id);
    }


}
