package com.jmlessous.controllers;

import com.jmlessous.entities.Utilisateur;
import com.jmlessous.services.IUtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/ListUser/{id}")
    @ResponseBody
    public Utilisateur affichUser (@PathVariable("id") Long id )
    {
        return utilisateurService.loadUser(id);
    }
}
