package com.jmlessous.controllers;

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
    @GetMapping(value = "findUserByToken")
    public Utilisateur findUserByToken() {
        return   utilisateurService.loadUser(SecurityContextHolder.getContext().getAuthentication().getName()) ;
    }
}
