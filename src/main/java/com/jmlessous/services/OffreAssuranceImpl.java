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

    public Float calculateEmpPrime(ParamsOffreAssurance params){
        float prime=0;
        OffreAssurance offreAssurance = offreRepo.findById(params.id).get();
        List<Integer> mortality = Arrays.asList(100000,97104,96869,96727,96624,96541,96471,96410,96356,96306,96258,96211,96163,96111,96052,95985,95908,95821,
                95722,95614,95496,95372,95242,95108,94971,94834,94696,94558,94420,94283,94145,
                94007,93867,93724,93578,93426,93268,93102,92926,92739,92538,92323,92089,91837,91562,91263,90937,90580,90190,89764,89297,88786,88226,87614,86944,86211,85410,
                84536,83582,82542,81409,80178,78842,77393,75826,74134,72312,70354,68257,66017,63632,61103,58432,55623,52686,49629,46469,43222,39911,36560,33200,29861,
                26580,23390,20328,17428,14722,12238,9997,8013,6292,4832,3623,2647,1876,1286,850,539,326,187,101,51,24,10,4,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
        float[] amortissement=new float[params.nbreAnnuite+1];
        amortissement[0]=params.montant;
        for (int i=1;i< params.nbreAnnuite;i++){
            float newval = amortissement[i-1]-(params.annuite - (amortissement[i-1]*params.interet/100));
            amortissement[i]=newval;
        }
        float act = 1/(1+(offreAssurance.getInteret()/100));
        System.out.println("--------------- act :"+ act);
        for (int j=0;j<params.nbreAnnuite;j++){
            float a = (mortality.get(params.age) - mortality.get(params.age+j));
            prime += amortissement[j]*(((float)mortality.get(params.age+j-1) - (float)mortality.get(params.age+j))/(float)mortality.get(params.age))*Float.valueOf(Double.toString(Math.pow(act,j)));
        }
        return prime;
    }

    public Float calculatePrimeRente (ParamsOffreAssurance params){
        float prime=0;
        OffreAssurance offreAssurance = offreRepo.findById(params.id).get();
        List<Integer> mortality = Arrays.asList(100000,97104,96869,96727,96624,96541,96471,96410,96356,96306,96258,96211,96163,96111,96052,95985,95908,95821,
                95722,95614,95496,95372,95242,95108,94971,94834,94696,94558,94420,94283,94145,
                94007,93867,93724,93578,93426,93268,93102,92926,92739,92538,92323,92089,91837,91562,91263,90937,90580,90190,89764,89297,88786,88226,87614,86944,86211,85410,
                84536,83582,82542,81409,80178,78842,77393,75826,74134,72312,70354,68257,66017,63632,61103,58432,55623,52686,49629,46469,43222,39911,36560,33200,29861,
                26580,23390,20328,17428,14722,12238,9997,8013,6292,4832,3623,2647,1876,1286,850,539,326,187,101,51,24,10,4,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
        float act = 1/(1+(offreAssurance.getInteret()/100));
        if(params.isUnique){
            float p = params.montant * Float.parseFloat(Double.toString(Math.pow((1+(offreAssurance.getInteret()/100)), params.ageDebutRemb- params.age)));
            System.out.println("------------ p ---------- "+p);
            float rente = 0;
            for (int i=1;i<= params.nbreRemb;i++){
                rente+= ((float)mortality.get(params.ageDebutRemb+i)/(float)mortality.get(params.ageDebutRemb))*Float.parseFloat(Double.toString(Math.pow(act,i)));
                System.out.println("/////// rente : ////////////"+rente);
            }
            return p/rente;
        }
        else{
            float p = 0;
            for (int i=1;i<= params.nbreAnnuite;i++){
                p+= params.montant*((float)mortality.get(params.age+i)/(float)mortality.get(params.age))* Float.parseFloat(Double.toString(Math.pow((1+(offreAssurance.getInteret()/100)), i)));
            }
            float rente = 0;
            for (int i=1;i<= params.nbreRemb;i++){
                rente+= ((float)mortality.get(params.ageDebutRemb+i)/(float)mortality.get(params.ageDebutRemb))*Float.parseFloat(Double.toString(Math.pow(act,i)));
                System.out.println("/////// rente : ////////////"+rente);
            }
            return p/rente;
        }
    }

    @Override
    public Float calculatePrime(ParamsOffreAssurance params) throws JSONException {
        Float scoreVal, scoreP, scoreSexe, scoreClasse, scoreCirc, score, prime;
        prime = (float) 0;
        OffreAssurance offreAssurance = offreRepo.findById(params.id).get();
//        JSONObject scorePuissJ = new JSONObject(offreAssurance.getScorePuissance());
//        JSONObject scoreBonusJ = new JSONObject(offreAssurance.getScoreBonus());
        if (offreAssurance.getCategorie().equals(CategorieAssurance.AUTOMOBILE)) {
            Map<String, Float> scorePuiss = offreAssurance.getScorePuissance();
            Map<String, Float> scoreBonus = offreAssurance.getScoreBonus();
            Map<String, Float> minMax = getMinMaxScore(offreAssurance);
            scoreVal = (((params.valeurVoit - 6000) / 294000) * (offreAssurance.getScoreValMax() - offreAssurance.getScoreValMin())) + offreAssurance.getScoreValMin();
            scoreCirc = (((params.nbreCirc) / 30) * (offreAssurance.getScoreCircMax() - offreAssurance.getScoreCircMin())) + offreAssurance.getScoreCircMin();
            if (params.sexe.equals("homme")) {
                scoreSexe = offreAssurance.getScoreHomme();
            } else {
                scoreSexe = offreAssurance.getScoreFemme();
            }
            scoreP = scorePuiss.get(params.puissance);
            scoreClasse = scoreBonus.get(params.classe);

            score = scoreClasse + scoreCirc + scoreVal + scoreP + scoreSexe;
            prime = 2 * offreAssurance.getInterFlex() * ((score - minMax.get("min")) / (minMax.get("max") - minMax.get("min"))) + (offreAssurance.getPrimePure() - offreAssurance.getInterFlex());
        }
        else if (offreAssurance.getCategorie().equals(CategorieAssurance.HABITATION)){
            scoreVal = (((params.valeurVoit - 30000) / 2970000) * (offreAssurance.getScoreValMax() - offreAssurance.getScoreValMin())) + offreAssurance.getScoreValMin();
            scoreCirc = (((params.nbreCirc - 5000) / 495000) * (offreAssurance.getScoreCircMax() - offreAssurance.getScoreCircMin())) + offreAssurance.getScoreCircMin();
            if (params.sexe.equals("homme")) {
                scoreSexe = offreAssurance.getScoreHomme();
            } else {
                scoreSexe = offreAssurance.getScoreFemme();
            }
            score = scoreCirc + scoreVal + scoreSexe;
            Float min = offreAssurance.getScoreCircMin()+offreAssurance.getScoreValMin()+Float.min(offreAssurance.getScoreFemme(),offreAssurance.getScoreHomme());
            Float max = offreAssurance.getScoreCircMax()+offreAssurance.getScoreValMax()+Float.max(offreAssurance.getScoreFemme(),offreAssurance.getScoreHomme());
            prime = 2 * offreAssurance.getInterFlex() * ((score - min) / (max - min)) + (offreAssurance.getPrimePure() - offreAssurance.getInterFlex());
        }
        else if (offreAssurance.getCategorie().equals(CategorieAssurance.EMPRUNTEUR)){
            prime=calculateEmpPrime(params);
            System.out.println("/////////////// prime :"+prime);
        }
        else if (offreAssurance.getCategorie().equals(CategorieAssurance.RENTE_VIAGERE)){
            prime=calculatePrimeRente(params);
            System.out.println("/////////////// prime :"+prime);
        }

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
