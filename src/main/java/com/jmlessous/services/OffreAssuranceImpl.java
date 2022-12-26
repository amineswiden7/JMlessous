package com.jmlessous.services;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jmlessous.entities.*;
import com.jmlessous.repositories.OffreAssuranceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

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
    public List<OffreAssurance> getOffresByCategorie(CategorieAssurance categ) {
        return offreRepo.retrieveOffreByCategorie(categ);
    }

    @Override
    public List<OffreAssurance> getAvOffresByCategorie(CategorieAssurance categ) {
        return offreRepo.retrieveAvOffresByCategorie(categ);
    }

    @Override
    public List<OffreAssurance> getPubOffresByCategorie(CategorieAssurance categ) {
        return offreRepo.retrievePubOffresByCategorie(categ);
    }

    @Override
    public List<OffreAssurance> getOffresByAssurance(String assurance) {
        return null;
    }

    @Override
    public List<String> getAllAssurance() {
        return offreRepo.retrieveAllAssurance();
    }

    @Override
    public Float calculatePrime(ParamsOffreAssurance params) throws JSONException {
        Float scoreVal, scoreP, scoreSexe, scoreClasse, scoreCirc, score, prime;
        OffreAssurance offreAssurance = offreRepo.findById(params.id).get();
//        JSONObject scorePuissJ = new JSONObject(offreAssurance.getScorePuissance());
//        JSONObject scoreBonusJ = new JSONObject(offreAssurance.getScoreBonus());
        Map<String,Float> scorePuiss = offreAssurance.getScorePuissance();
        Map<String,Float> scoreBonus = offreAssurance.getScoreBonus();
        Map<String,Float> minMax = getMinMaxScore(offreAssurance);
        scoreVal = (((params.valeurVoit-6000)/294000)*(offreAssurance.getScoreValMax()-offreAssurance.getScoreValMin()))+offreAssurance.getScoreValMin();
        scoreCirc = (((params.nbreCirc)/30)*(offreAssurance.getScoreCircMax()-offreAssurance.getScoreCircMin()))+offreAssurance.getScoreCircMin();
        if (params.sexe.equals("homme")){
            scoreSexe = offreAssurance.getScoreHomme();
        }
        else {
            scoreSexe = offreAssurance.getScoreFemme();
        }
        scoreP = scorePuiss.get(params.puissance);
        scoreClasse = scoreBonus.get(params.classe);

        score = scoreClasse+scoreCirc+scoreVal+scoreP+scoreSexe;
        prime = 2*offreAssurance.getInterFlex()*((score-minMax.get("min"))/(minMax.get("max")-minMax.get("min")))+(offreAssurance.getPrimePure()-offreAssurance.getInterFlex());
        return prime;
    }

    public Map<String,Float> getMinMaxScore(OffreAssurance offre) throws JSONException {
        Map<String,Float> minMax = new TreeMap<String, Float>();
//        JSONObject scorePuissJ = new JSONObject(offre.getScorePuissance());
//        JSONObject scoreBonusJ = new JSONObject(offre.getScoreBonus());
        Map<String,Float> scorePuiss = offre.getScorePuissance();
        Map<String,Float> scoreBonus = offre.getScoreBonus();
        Float min = Collections.min(scorePuiss.values())+Collections.min(scoreBonus.values())+offre.getScoreCircMin()+offre.getScoreValMin()+Float.min(offre.getScoreFemme(),offre.getScoreHomme());
        Float max = Collections.max(scorePuiss.values())+Collections.max(scoreBonus.values())+offre.getScoreCircMax()+offre.getScoreValMax()+Float.max(offre.getScoreFemme(),offre.getScoreHomme());
        minMax.put("min",min);
        minMax.put("max",max);
        return minMax;
    }
}
