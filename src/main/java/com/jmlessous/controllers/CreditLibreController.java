package com.jmlessous.controllers;

import com.jmlessous.entities.CreditLibre;
import com.jmlessous.entities.Garantie;
import com.jmlessous.entities.NiveauEtude;
import com.jmlessous.services.Amortissement;
import com.jmlessous.services.ICreditLibreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Hashtable;
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
    @PostMapping("/AddCreditLibre/{idUser}/{idGarantie}")
    @ResponseBody
    public CreditLibre AddAccount (@RequestBody CreditLibre c ,@PathVariable("idUser") Long idUser,@PathVariable("idGarantie") Long idGarantie)
    {
        return creditLibre.addCreditLibre(c,idUser,idGarantie);
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


    @GetMapping("/simulateur/{montant}/{duree}/{taux}")
    public Amortissement Simulation(@PathVariable("montant") float montant, @PathVariable("duree") float duree, @PathVariable("taux") float taux ){
       CreditLibre cr=new CreditLibre();
       cr.setTauxInteret(taux);
       cr.setMontantCredit(montant);
       cr.setDuree(duree);
        return creditLibre.Simulateur(cr);
    }

    @PostMapping("/tabAmortissement")
    @ResponseBody
    public Amortissement[] Simulation(@RequestBody CreditLibre cr)
    {
        return creditLibre.TabAmortissement(cr);

    }
}
