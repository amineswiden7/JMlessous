package com.jmlessous.services;

import com.jmlessous.entities.Absence;
import com.jmlessous.entities.Utilisateur;
import com.jmlessous.repositories.AbsenceRepository;
import com.jmlessous.repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AbsenceServiceImpl implements IAbsenceService{
    @Autowired
    AbsenceRepository absenceRepo;
    UtilisateurRepository utilisateurRepo;


    @Override
    public List<Absence> retrieveAllAbsences() {
        return (List<Absence>) absenceRepo.findAll();
    }

    @Override
    public Absence addOffre(Absence a, Long id) {
        Utilisateur u = utilisateurRepo.findById(id).orElse(null);
        a.setUtilisateursAbsence(u);

        return absenceRepo.save(a);
    }

    @Override
    public List<Absence> retrieveAbsenceByUser(Long id_user) {
        return absenceRepo.getAbsenceByUser(id_user);
    }

    @Override
    public Absence updateAbsence(Absence a, Long id_user) {
        Utilisateur u = utilisateurRepo.findById(id_user).orElse(null);
        a.setUtilisateursAbsence(u);

        return absenceRepo.save(a);

    }
}
