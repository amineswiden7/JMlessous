package com.jmlessous.services;


import com.jmlessous.entities.*;
import com.jmlessous.repositories.AbsenceRepository;

import com.jmlessous.repositories.CompteCourantRepository;
import com.jmlessous.repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilisateurServiceImpl implements IUtilisateurService {
    @Autowired
    UtilisateurRepository userRep;
    @Autowired

    AbsenceRepository AbsRep;
    @Autowired

    CompteCourantRepository cptRep;
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


	@Override
	public List<CompteCourant> loadCpt(Long id_user) {
		List<CompteCourant> cpt = cptRep.getCompteByUser(id_user);
        return cpt;
	}

    @Override
    public List<Utilisateur> retrieveAllUtilisateur() {
        return (List<Utilisateur>) userRep.findAll();
    }


    @Override
    public float affecterSalaire(Long id_user , String rib) {
       List<Absence> ab = AbsRep.getAbsenceByIdUser(id_user);
       Utilisateur u=userRep.findById(id_user).orElse(null);
       CompteCourant cp= cptRep.getcptByRib(rib);
       float solde =cp.getSolde();
       float salaireParJour=u.getSalaire()/30;
        float salaire=u.getSalaire();
        if((u.getRole()== Role.ADMIN)||(u.getRole()== Role.AGENT_MARKETING)||(u.getRole()== Role.AGENT_RH)||(u.getRole()== Role.CONSEILLER_CLIENTELE)||(u.getRole()== Role.EMPLOYE_ASSURANCE)||(u.getRole()== Role.FINANCIER)||(u.getRole()== Role.GESTIONNAIRE_CLIENTELE)||(u.getRole()== Role.MEMBRE_DIRECTOIRE)){
        for (Absence absence : ab) {
            if(absence.getTypeAbsence()== TypeAbsence.ABSENCENONJUSTIFIEE){
                salaire-=salaireParJour;
            }

        }
            cp.setSolde(solde+salaire);
            cptRep.save(cp);
        }

        return salaire;
    }


}
