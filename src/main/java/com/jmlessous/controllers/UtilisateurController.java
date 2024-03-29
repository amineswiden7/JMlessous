package com.jmlessous.controllers;

import com.jmlessous.entities.CompteCourant;
import com.jmlessous.entities.Utilisateur;
import com.jmlessous.services.IUtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class UtilisateurController {

    @Autowired
    IUtilisateurService utilisateurService;

    @PostMapping("/addUser")
    @ResponseBody
    public Utilisateur AjoutUser (@RequestBody Utilisateur c)
    {
        return utilisateurService.addUser(c);
    }
    @GetMapping("/affecterSalaire/{id}/{rib}")
    @ResponseBody
    public float affecterSalaire (@PathVariable("id") Long id,@PathVariable("rib") String rib)
    {
        return utilisateurService.affecterSalaire(id,rib);
    }
    @GetMapping("/ListUser")
    @ResponseBody
    public List<Utilisateur> affichUser ( )
    {
        return utilisateurService.retrieveAllUtilisateur();
    }
    @GetMapping("/ListUser/{id}")
    @ResponseBody
    public Utilisateur affichUser (@PathVariable("id") Long id )
    {
        return utilisateurService.loadUser(id);
    }

    @GetMapping("/ListCpt/{id}")
    @ResponseBody
    public CompteCourant affichCpt (@PathVariable("id") Long id )
    {
        return (CompteCourant) utilisateurService.loadCpt(id);
        }

    @GetMapping(value = "findUserByToken")
    public Utilisateur findUserByToken() {
        return   utilisateurService.loadUser(SecurityContextHolder.getContext().getAuthentication().getName()) ;

    }
}
