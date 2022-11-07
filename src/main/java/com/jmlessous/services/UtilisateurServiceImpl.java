package com.jmlessous.services;

import com.jmlessous.entities.Utilisateur;
import com.jmlessous.repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UtilisateurServiceImpl implements IUtilisateurService {
    @Autowired
    UtilisateurRepository userRep;
    @Autowired
    private PasswordEncoder bcryptEncoder;
    @Override
    public Utilisateur loadUser(String username) {

        Utilisateur user = userRep.findByLogin(username);
        return user;
    }

    @Override
    public Utilisateur addUser(Utilisateur u) {

        u.setMotDePasse(bcryptEncoder.encode(u.getPassword()));
       userRep.save(u);
        return u;
    }

    @Override
    public Utilisateur loadUser(Long id_user) {
        Utilisateur user = userRep.findById(id_user).orElse(null);
        return user;
    }
}
