package com.jmlessous.controllers;


import com.jmlessous.entities.Amortissement;
import com.jmlessous.entities.CreditEtudiant;
import com.jmlessous.entities.CreditImmobilier;
import com.jmlessous.services.ICreditEtu;
import com.jmlessous.services.ICreditImobService;
import com.jmlessous.services.IUtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/CreditImob")
public class CreditImobController {


    @Autowired
    ICreditImobService creditImobService;
    @Autowired
    IUtilisateurService utilisateurService;

    @PostMapping("/add-creditImob/{id}")
    @ResponseBody
    public CreditImmobilier addCredit(@RequestBody CreditImmobilier c, @PathVariable("id") Long id) {

        return creditImobService.addCreditt(c, id);
    }

    @GetMapping("/retrieve-all-credit")
    @ResponseBody
    public List<CreditImmobilier> getCredit() {
        List<CreditImmobilier> listcre = creditImobService.retrieveAllCredit();
        return listcre;
    }

    @GetMapping("/simulateur")
    public Amortissement Simulation(@RequestBody CreditImmobilier c) {

        return creditImobService.Simulateurr(c);
    }


    @PostMapping("/tabAmor")
    @ResponseBody
    public Amortissement[] Sim(@RequestBody CreditImmobilier cr) {
        return creditImobService.TabAmortissementt(cr);

    }

















}
