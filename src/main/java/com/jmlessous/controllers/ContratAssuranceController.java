package com.jmlessous.controllers;

import com.jmlessous.entities.ContratAssurance;
import com.jmlessous.entities.TypeAssurance;
import com.jmlessous.services.ContratAssuranceServiceImpl;
import com.jmlessous.services.IContratAssuranceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ContratAssuranceController {
    @Autowired
    IContratAssuranceService service;
    
    @GetMapping("getAllContrats")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<ContratAssurance> getContrats(){
        return service.retrieveAllContrats();
    }

    @GetMapping("getContrat/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ContratAssurance getContratById(@PathVariable("id") Long id){
        return service.retrieveContrat(id);
    }

    @PostMapping("addContrat/{idOffre}/{idUser}}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ContratAssurance addContrat(@RequestBody ContratAssurance Contrat,@PathVariable("idOffre") Long idOffre, @PathVariable("idUser") Long idUser){
        return service.addContrat(Contrat,idOffre,idUser);
    }

    @PutMapping("updateContrat/{idOffre}/{idUser}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ContratAssurance updateContrat(@RequestBody ContratAssurance Contrat, @PathVariable("idOffre") Long idOffre, @PathVariable("idUser") Long idUser){
        return service.updateContrat(Contrat,idOffre,idUser);
    }

    @DeleteMapping("deleteContrat/{id}")
    public void deleteContrat(@PathVariable("id") Long id){
        service.deleteContrat(id);
    }

    @GetMapping("getAllContratsByUtilisateur/{id}")
    public List<ContratAssurance> getContratsByUtilisateur(@PathVariable("id") Long idUser){
        return service.getContratsByUtilisateur(idUser);
    }

    @GetMapping("getAllContratsByTypeAssurance/{type}")
    public List<ContratAssurance> getContratsByTypeAssurance(@PathVariable("type") TypeAssurance type){
        return service.getContratsByTypeAssurance(type);
    }
}
