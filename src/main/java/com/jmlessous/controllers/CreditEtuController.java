package com.jmlessous.controllers;


import com.jmlessous.entities.Absence;
import com.jmlessous.entities.CreditEtudiant;
import com.jmlessous.entities.NiveauEtude;
import com.jmlessous.services.ICreditEtu;
import com.jmlessous.services.IUtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Hashtable;
import java.util.List;

@RestController
@RequestMapping("/CreditEtu")

public class CreditEtuController {


    @Autowired
    ICreditEtu creditEtu;
    @Autowired
    IUtilisateurService utilisateurService;

    @GetMapping("/retrieve-all-creditImob")
    @ResponseBody
    public List<CreditEtudiant> getCredit() {
        List<CreditEtudiant> listcre = creditEtu.retrieveAllCredit();
        return listcre;
    }


    //http://localhost:8083/JMLessous/Credit/add-credit/{client-id}
    @PostMapping("/add-credit/{id}")
    @ResponseBody
    public CreditEtudiant addCredit (@RequestBody CreditEtudiant c ,@PathVariable("id") Long id)
    {

        return creditEtu.addCredit(c,id);
    }

    @GetMapping("simulator/{amount}/{period}/{typePeriod}")
    public Hashtable<String, Double> Simulation(@PathVariable float amount, @PathVariable float period, @PathVariable NiveauEtude typePeriod ){
        return creditEtu.simulation(amount,period,typePeriod);
    }
    @GetMapping("score/{amount}/{period}/{typePeriod}")
    public Double scoring(@PathVariable float amount, @PathVariable float period, @PathVariable NiveauEtude typePeriod ){
        return creditEtu.Scoring(amount,period,typePeriod);
    }





}
