package com.jmlessous.services;

import com.jmlessous.entities.OffreAssurance;
import com.jmlessous.entities.TypeAssurance;
import com.jmlessous.repositories.OffreAssuranceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OffreAssuranceImpl implements IOffreAssuranceService{

    @Autowired
    OffreAssuranceRepository offreRepo;
    @Override
    public List<OffreAssurance> retrieveAllOffres() {
            return (List<OffreAssurance>) offreRepo.findAll();
    }

    @Override
    public OffreAssurance addOffre(OffreAssurance o) {
            System.out.println("Offre ajouté");

            return offreRepo.save(o);

        }

    @Override
    public void archiveOffre(Long id) {

    }

    @Override
    public void deleteOffre(Long id) {
        OffreAssurance offre= offreRepo.findById(id).get();
        if (offreRepo.findById(id).isPresent()) {
            offreRepo.delete(offre);
            System.out.println("Offre effacé");
        } else {
            System.out.println("Offre introuvable");
        }
    }

    @Override
    public OffreAssurance updateOffre(OffreAssurance o) {
        Long id = o.getIdOffreAssurance();
        if (offreRepo.findById(id).isPresent()) {
            return offreRepo.save(o);
        } else {
            System.out.println("Offre n'existe pas !");
            return null;
        }
    }

    @Override
    public OffreAssurance retrieveOffre(Long id) {
        return offreRepo.findById(id).get();
    }

    @Override
    public List<OffreAssurance> getOffresByType(TypeAssurance type) {
        return offreRepo.retrieveOffreByType(type);
    }

    @Override
    public List<OffreAssurance> getOffresByAssurance(String assurance) {
        return null;
    }

    @Override
    public List<String> getAllAssurance() {
        return offreRepo.retrieveAllAssurance();
    }
}
