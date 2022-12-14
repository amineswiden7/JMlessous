package com.jmlessous.services;

import com.jmlessous.entities.ContratAssurance;
import com.jmlessous.entities.OffreAssurance;
import com.jmlessous.entities.TypeAssurance;
import com.jmlessous.entities.Utilisateur;
import com.jmlessous.repositories.ContratAssuranceRepository;
import com.jmlessous.repositories.OffreAssuranceRepository;
import com.jmlessous.repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContratAssuranceServiceImpl implements IContratAssuranceService{

    @Autowired
    ContratAssuranceRepository contratRepo;
    @Autowired
    OffreAssuranceRepository offreRepo;
    @Autowired
    UtilisateurRepository utilisateurRepo;
    @Override
    public List<ContratAssurance> retrieveAllContrats() {
        return (List<ContratAssurance>) contratRepo.findAll();
    }

    @Override
    public ContratAssurance addContrat(ContratAssurance c,Long idOffre, Long idUser) {
        System.out.println("Contrat ajouté");
        OffreAssurance o = offreRepo.findById(idOffre).get();
        Utilisateur u = utilisateurRepo.findById(idUser).get();
        c.setOffreAssurance(o);
        c.setUtilisateurCA(u);
        return contratRepo.save(c);

    }

    @Override
    public void archiveContrat(Long id) {

    }

    @Override
    public void deleteContrat(Long id) {
        ContratAssurance contrat= contratRepo.findById(id).get();
        if (contratRepo.findById(id).isPresent()) {
            contratRepo.delete(contrat);
            System.out.println("Contrat effacé");
        } else {
            System.out.println("Contrat introuvable");
        }
    }

    @Override
    public ContratAssurance updateContrat(ContratAssurance c,Long idOffre, Long idUser) {
        Long id = c.getIdContrat();
        if (contratRepo.findById(id).isPresent()) {
            OffreAssurance o = offreRepo.findById(idOffre).get();
            Utilisateur u =utilisateurRepo.findById(idUser).get();
            c.setOffreAssurance(o);
            c.setUtilisateurCA(u);
            return contratRepo.save(c);
        } else {
            System.out.println("Contrat n'existe pas !");
            return null;
        }
    }

    @Override
    public ContratAssurance retrieveContrat(Long id) {
        if (contratRepo.findById(id).isPresent()) {
            return contratRepo.findById(id).get();
        } else {
            System.out.println("Contrat n'existe pas !");
            return null;
        }
    }

    @Override
    public List<ContratAssurance> getContratsByUtilisateur(Long u) {
        return contratRepo.retrieveContratByUtilisateur(u);
    }

    @Override
    public List<ContratAssurance> getContratsByTypeAssurance(TypeAssurance ta) {
        return contratRepo.retrieveContratByTypeAssurance(ta);
    }
}
