package com.jmlessous.controllers;


import com.jmlessous.entities.Absence;
import com.jmlessous.entities.CreditEtudiant;
import com.jmlessous.services.ICreditEtu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/CreditEtu")

public class CreditEtuController {


    @Autowired
    ICreditEtu et;

    @GetMapping("/retrieve-all-credit")
    @ResponseBody
    public List<CreditEtudiant> getCredit() {
        List<CreditEtudiant> listcre = et.retrieveAllCredit();
        return listcre;
    }


    //http://localhost:8083/JMLessous/Credit/add-credit/{client-id}
    @PostMapping("/add-credit/{id}")
    @ResponseBody
    public CreditEtudiant addCredit (@RequestBody CreditEtudiant c ,@PathVariable("id") Long id)
    {
        CreditEtudiant credit = et.addCredit(c,id);
        return credit;
    }




}
