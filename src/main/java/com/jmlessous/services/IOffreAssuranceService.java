package com.jmlessous.services;

import com.jmlessous.entities.*;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface IOffreAssuranceService {
    List<OffreAssurance> retrieveAllOffres();

    OffreAssurance addOffre(OffreAssurance o, MultipartFile file);

    void archiveOffre(Long id);

    void deleteOffre(Long id);

    OffreAssurance updateOffre(OffreAssurance o);

    OffreAssurance retrieveOffre(Long id);

    List<OffreAssurance> getOffresByType(TypeAssurance type);

    List<OffreAssurance> getOffresByStatut(StatutAssurance statut);

    List<OffreAssurance> getOffresByCategorie(CategorieAssurance categ);

    List<OffreAssurance> getAvOffresByCategorie(CategorieAssurance categ);

    List<OffreAssurance> getPubOffresByCategorie(CategorieAssurance categ);

    List<OffreAssurance> getOffresByAssurance(String assurance);

    List<String> getAllAssurance();

    Float calculatePrime(ParamsOffreAssurance params) throws JSONException;

}
