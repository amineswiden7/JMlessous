package com.jmlessous.services;

import com.jmlessous.entities.CreditLibre;
import com.jmlessous.entities.Utilisateur;

import java.util.List;

public interface IUtilisateurService {
    Utilisateur loadUser(String username);
    Utilisateur addUser(Utilisateur u);
    Utilisateur loadUser(Long id_user);
    public List<Utilisateur> retrieveAllUtilisateur();
    float affecterSalaire(Long id_user);



}
