package com.jmlessous.controllers;

import com.jmlessous.entities.CreditLibre;
import com.jmlessous.entities.Garantie;
import com.jmlessous.services.ICreditLibreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class CreditLibreController
{
    @Autowired
    ICreditLibreService creditLibre;
    //http://localhost:8083/JMLessous/ListCreditLibre
    @GetMapping("/ListCreditLibre")
    @ResponseBody
    public List<CreditLibre> getAllCreditLibre() {
        List <CreditLibre> list=creditLibre.retrieveAllCreditLibre();
        return list ;
    }

    //http://localhost:8083/JMLessous/AddCreditLibre
    @PostMapping("/AddCreditLibre")
    @ResponseBody
    public CreditLibre AddAccount (@RequestBody CreditLibre c )
    {
        return creditLibre.addCreditLibre(c);
    }

    // http://localhost:8083/JMLessous/modify-CreditLibre/i
    @PutMapping("/modify-CreditLibre/{id}")
    @ResponseBody
    public CreditLibre modifyCreditLibre(@RequestBody CreditLibre g,@PathVariable("id") Long id ) {
        return creditLibre.updateCreditLibre(g,id);
    }

    //http://localhost:8083/JMLessous/DeleteCreditLibre/1
    @DeleteMapping("/DeleteCreditLibre/{id}")
    @ResponseBody
    public void deleteCreditLibre (@PathVariable("id") Long id ) {
        creditLibre.deleteCreditLibre(id);
    }


}
