package com.jmlessous.controllers;


import com.jmlessous.entities.Amortissement;
import com.jmlessous.entities.CreditEtudiant;
import com.jmlessous.entities.CreditImmobilier;
import com.jmlessous.services.ICreditEtu;
import com.jmlessous.services.ICreditImobService;
import com.jmlessous.services.IUtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
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
    @CrossOrigin(origins = "http://localhost:4200")
    public CreditImmobilier addCredit(@RequestBody CreditImmobilier c, @PathVariable("id") Long id) {
        System.out.println(c);
        return creditImobService.addCreditt(c, id);
    }

    @GetMapping("/retrieve-all-credit")
    @ResponseBody
    public List<CreditImmobilier> getCredit() {
        List<CreditImmobilier> listcre = creditImobService.retrieveAllCredit();
        return listcre;
    }

    @GetMapping("/retrieve-all-credit/{id}")
    @ResponseBody
    public CreditImmobilier getCreditby( @PathVariable("id") Long id) {
        CreditImmobilier listcre = creditImobService.retrieveCreditById(id);
        return listcre;
    }



    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/simulateur")
    public Amortissement Simulation(@RequestBody CreditImmobilier c) {

        return creditImobService.Simulateurr(c);
    }


    @PostMapping("/tabAmor")
    @ResponseBody
    public Amortissement[] Sim(HttpServletResponse response,@RequestBody CreditImmobilier cr) throws IOException {

        response.setContentType("application/pdf");
       DateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd_HH:mm");
        String currentDateTime = dateFormater.format(new Date());
        String headerKey = "Content-Disposition";
       String headerValue = "Attachement;filename=inves_"+ currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);
        Amortissement[] listInvestesment = creditImobService.TabAmortissementt(cr);;
        TabAmortPDFExporter exporter = new TabAmortPDFExporter(Arrays.asList(listInvestesment));
        exporter.export(response);
        return creditImobService.TabAmortissementt(cr);

    }

    @GetMapping("/retrieve-all-creditbyuser/{id}")
    @ResponseBody
    public List<CreditImmobilier> getCreditbyuser( @PathVariable("id") Long id) {
        List<CreditImmobilier> listcre = creditImobService.retrieveCreditByUser(id);
        return listcre;

    }















}
