package com.jmlessous.controllers;


import com.jmlessous.repositories.CreditImobRepository;
import com.jmlessous.services.Amortissement;
import com.jmlessous.entities.CreditImmobilier;
import com.jmlessous.services.ICreditImobService;
import com.jmlessous.services.IUtilisateurService;
import com.lowagie.text.DocumentException;
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
    @Autowired
    CreditImobRepository crr;
    Amortissement[] aaa ;

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

        Amortissement a = creditImobService.Simulateurr(c);
        this.aaa=creditImobService.TabAmortissementt(c);
        return creditImobService.Simulateurr(c);

    }


    @PostMapping("/tabAmor")
    @ResponseBody
    public Amortissement[] Sim(@RequestBody CreditImmobilier cr) {



        return creditImobService.TabAmortissementt(cr);

    }
    @GetMapping("/export")
    public void exportToPDF(HttpServletResponse response,@RequestBody Amortissement[] cr) throws DocumentException,IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd_HH:mm");
        String currentDateTime = dateFormater.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "Attachement;filename=inves_"+ currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);
        //Amortissement[] listInvestesment = creditImobService.TabAmortissementt(cr);;

        TabAmortPDFExporter exporter = new TabAmortPDFExporter(this.aaa);
        exporter.export(response);
    }

    @GetMapping("/retrieve-all-creditbyuser/{id}")
    @ResponseBody
    public List<CreditImmobilier> getCreditbyuser( @PathVariable("id") Long id) {
        List<CreditImmobilier> listcre = creditImobService.retrieveCreditByUser(id);
        return listcre;

    }
    @GetMapping ("/export/excel")

    public void exportToExcel(HttpServletResponse response) throws DocumentException,IOException {


        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";

        String headervalue = "attachment; filename=Tableau_Credit_N_.xlsx";
        response.setHeader(headerKey, headervalue);
     //  Amortissement[] credit = creditImobService.TabAmortissementt(this.aaa);
        List<Amortissement> list = Arrays.asList(this.aaa);
        com.jmlessous.services.ExcelExporter exp = new com.jmlessous.services.ExcelExporter(list);
        //UserExcelExporter exp = new UserExcelExporter(list);
        exp.export(response);
    }
    @GetMapping("/accepter/{id}")
    public void accepter( @PathVariable("id") Long id) {
        creditImobService.acceptercredit(id);

    }
    @GetMapping("/refuser/{id}")
    public void refuser( @PathVariable("id") Long id) {
        creditImobService.Refusercredit(id);

    }

    @PostMapping("/add-creditveriff")
    @ResponseBody
    public CreditImmobilier addCredit (@RequestBody CreditImmobilier c )
    {

        return creditImobService.transemtre(c);
    }














}
