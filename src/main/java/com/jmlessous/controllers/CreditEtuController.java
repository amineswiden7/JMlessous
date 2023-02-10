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
    @GetMapping("/retrieve-all-creditImob/{id}")
    @ResponseBody
    public List<CreditEtudiant> getCreditby(@PathVariable("id") Long id) {
        List<CreditEtudiant> listcre = creditEtu.retrieveCreditByUser(id);
        return listcre;
    }


    //http://localhost:8083/JMLessous/Credit/add-credit/{client-id}
    @PostMapping("/add-credit/{id}/{idC}")
    @ResponseBody
    public CreditEtudiant addCredit (@RequestBody CreditEtudiant c ,@PathVariable("id") Long id,@PathVariable("idC") Long idC)
    {

        return creditEtu.addCredit(c,id,idC);
    }



    @PostMapping("/add-creditverif")
    @ResponseBody
    public CreditEtudiant addCredit (@RequestBody CreditEtudiant c )
    {

        return creditEtu.transemtre(c);
    }

    @GetMapping("simulator/{amount}/{period}/{typePeriod}")
    public Hashtable<String, Double> Simulation(@PathVariable float amount, @PathVariable float period, @PathVariable NiveauEtude typePeriod ){
        return creditEtu.simulation(amount,period,typePeriod);
    }
    @GetMapping("score/{amount}/{period}/{typePeriod}")
    public Double scoring(@PathVariable float amount, @PathVariable float period, @PathVariable NiveauEtude typePeriod ){
        return creditEtu.Scoring(amount,period,typePeriod);
    }
    @GetMapping("/accepter/{id}")
    public void accepter( @PathVariable("id") Long id) {
        creditEtu.acceptercredit(id);

    }
    @GetMapping("/refuser/{id}")
    public void refuser( @PathVariable("id") Long id) {
        creditEtu.Refusercredit(id);
    }




}
