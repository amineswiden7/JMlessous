package com.jmlessous.controllers;

import com.jmlessous.entities.Utilisateur;
import com.jmlessous.services.IUtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UtilisateurController {

    @Autowired
    IUtilisateurService utilisateurService;

    @PostMapping("/addUser")
    @ResponseBody
    public Utilisateur AjoutUser (@RequestBody Utilisateur c)
    {
        return utilisateurService.addUser(c);
    }
}
