package com.jmlessous.services;

import com.jmlessous.entities.*;
import com.jmlessous.repositories.ContratAssuranceRepository;
import com.jmlessous.repositories.OffreAssuranceRepository;
import com.jmlessous.repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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
    public ContratAssurance addContrat(ContratAssurance c,Long idOffre, Long idUser) throws ParseException {
        System.out.println("Contrat ajouté");
        OffreAssurance o = offreRepo.findById(idOffre).get();
        Utilisateur u = utilisateurRepo.findById(idUser).get();
        c.setOffreAssurance(o);
        c.setUtilisateurCA(u);
        c.setDateAjout(LocalDate.now());
        System.out.println("Done 2000");
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
    public ContratAssurance declineContrat(Long id) {
        ContratAssurance contrat = contratRepo.findById(id).get();
        contrat.setStatut(StatutContratAssurance.REJECTED);
        return contratRepo.save(contrat);
    }

    @Override
    public ContratAssurance acceptContrat(Long id) {
        ContratAssurance contrat = contratRepo.findById(id).get();
        OffreAssurance o = offreRepo.findById(contrat.getOffreAssurance().getIdOffreAssurance()).get();
        o.setNbreContrats(o.getNbreContrats()+1);
        o.setGainTotal(o.getGainTotal()+contrat.getPrime()*o.getCommission()/100);
        offreRepo.save(o);
        contrat.setStatut(StatutContratAssurance.REGULATED);
        return contratRepo.save(contrat);
    }

    @Override
    public List<ContratAssurance> getContratsByUtilisateur(Long u) {
        return contratRepo.retrieveContratByUtilisateur(u);
    }

    @Override
    public List<ContratAssurance> getContratsByOffre(Long id) {
        return contratRepo.retrieveContratsByOffre(id);
    }

    @Override
    public List<ContratAssurance> getContratsByTypeAssurance(TypeAssurance ta) {
        return contratRepo.retrieveContratByTypeAssurance(ta);
    }
}
