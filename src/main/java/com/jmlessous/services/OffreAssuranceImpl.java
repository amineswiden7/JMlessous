package com.jmlessous.services;

import com.jmlessous.entities.OffreAssurance;
import com.jmlessous.entities.StatutAssurance;
import com.jmlessous.entities.TypeAssurance;
import com.jmlessous.repositories.OffreAssuranceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
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
    public OffreAssurance addOffre(OffreAssurance o, MultipartFile file) {
            System.out.println("Offre ajouté");
            /*String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            if(fileName.contains(".."))
                System.out.println("not a valid file");
            try {
                o.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
            } catch (IOException e) {
                e.printStackTrace();
            }*/
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
        if (offreRepo.findById(id).isPresent()) {
            return offreRepo.findById(id).get();
        } else {
            System.out.println("Offre n'existe pas !");
            return null;
        }

    }

    @Override
    public List<OffreAssurance> getOffresByType(TypeAssurance type) {
        return offreRepo.retrieveOffreByType(type);
    }

    @Override
    public List<OffreAssurance> getOffresByStatut(StatutAssurance statut) {
        return offreRepo.retrieveOffreByStatut(statut);
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
