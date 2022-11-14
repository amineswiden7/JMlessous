package com.jmlessous.services;

import com.jmlessous.entities.OffreAssurance;
import com.jmlessous.entities.TypeAssurance;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IOffreAssuranceService {
    List<OffreAssurance> retrieveAllOffres();

    OffreAssurance addOffre(OffreAssurance o, MultipartFile file);

    void archiveOffre(Long id);

    void deleteOffre(Long id);

    OffreAssurance updateOffre(OffreAssurance o);

    OffreAssurance retrieveOffre(Long id);

    List<OffreAssurance> getOffresByType(TypeAssurance type);

    List<OffreAssurance> getOffresByAssurance(String assurance);

    List<String> getAllAssurance();

}
