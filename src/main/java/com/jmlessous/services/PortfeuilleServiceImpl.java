package com.jmlessous.services;

import com.fasterxml.jackson.core.JsonParser;
import com.jmlessous.entities.Portfeuille;
import com.jmlessous.entities.ProduitFinancier;
import com.jmlessous.entities.StatusProduit;
import com.jmlessous.entities.Utilisateur;
import com.jmlessous.repositories.PortfeuilleRepository;
import com.jmlessous.repositories.ProduitFinancierRepository;
import com.jmlessous.repositories.UtilisateurRepository;
import io.swagger.models.HttpMethod;
import jdk.nashorn.internal.runtime.JSONFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import springfox.documentation.spring.web.json.Json;
import springfox.documentation.spring.web.json.JsonSerializer;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;


@Service
public class PortfeuilleServiceImpl implements IPortfeuilleService {

    @Autowired
    PortfeuilleRepository repository;
    @Autowired
    ProduitFinancierRepository repositoryProduit;

    @Autowired
    UtilisateurRepository utilisateurRepository;
    @Override
    public Portfeuille addPortfeuille(float solde,Long id) {
        Portfeuille p =new Portfeuille();
        p.setSolde(solde);
        p.setDateOuverture(new Date());
        Utilisateur user = utilisateurRepository.findById(id).get();
        p.setUtilisateur(user);
        return repository.save(p);
    }

    @Override
    public void deletePortfeuille(Long id) {
        Portfeuille portfeuille= repository.findById(id).get();
        if (repository.findById(id).isPresent()) {
            repository.delete(portfeuille);
            System.out.println("Portfeuille effacé");
        } else {
            System.out.println("Portfeuille introuvable");
        }
    }

    @Override
    public Portfeuille retrievePortfeuille(Long id) {
        Portfeuille portfeuille= repository.findById(id).get();

        if (repository.findById(id).isPresent()) {

            return portfeuille;
        } else {
            System.out.println("Portfeuille introuvable");
        }
        return null;
    }

    @Override
    public Portfeuille retrievePortfeuillebyUser(Long id) {
        Utilisateur user= utilisateurRepository.findById(id).get();
        if (utilisateurRepository.findById(id).isPresent()) {
            return repository.retrievePortfeuilleByUser(user);
        } else {
            System.out.println("Portfeuille introuvable");
        }
        return null;
    }

    @Override
    public Set<ProduitFinancier> rerieveProduits(Long id) {


        Set<ProduitFinancier> produits = new HashSet<ProduitFinancier>();

        if (repository.findById(id).isPresent()) {
            Portfeuille portfeuille= repository.findById(id).get();

            //laiseer que les produits en cours
            for (ProduitFinancier p: portfeuille.getProduitFinanciers() ) {
                //if(produit.getIsin().equals(p.getIsin()))
                if(p.getStatusProduit().equals(StatusProduit.ENCOURS))
                    produits.add(p);

            }


            return produits;
        } else {
            System.out.println("Portfeuille introuvable");
        }
        return null;
    }

    @Override
    public Set<Float> rerieveCapitals(Long id) {
        Set<Float> capitals = new HashSet<Float>();
        if (repository.findById(id).isPresent()) {
            Portfeuille portfeuille = repository.findById(id).get();

            for (ProduitFinancier p : portfeuille.getProduitFinanciers()) {
                capitals.add(p.getMontantAchat() * p.getQuantite());
            }


        }
        return capitals;
    }

    @Override
    public float calculPerformance(Long id) {
        float gain =0;
        if( repository.findById(id).isPresent()){
            Portfeuille portfeuille= repository.findById(id).get();

            for (ProduitFinancier p: portfeuille.getProduitFinanciers()) {
                 gain = p.getMontantAchat()*p.getQuantite();
            }
        }
        return gain;
    }

    @Override
    public Object varPortfeuille(Long id) {
        Portfeuille p = repository.findById(id).get();
        /*ArrayList<ArrayList<Float>> portfeuilleCloses = new ArrayList<ArrayList<Float>>();
        double[][] covariance = new double[100][100];
        for (ProduitFinancier produit:p.getProduitFinanciers() ) {
            portfeuilleCloses.add(getCloses(produit.getSymbol()));
        }
        Set<Float> capitals=rerieveCapitals(id);*/
        RestTemplate restTemplate = new RestTemplate();
       // String url = "http://127.0.0.1:8000/PortfeuilleVar?stock_return="+ portfeuilleCloses.toString()+"&exposure="+capitals+"&CI="+99+"&days="+1;
        String url = "http://127.0.0.1:8000/PortfeuilleVar1?id="+id+"&CI="+99+"&days="+1;
        /*UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url).queryParam("stock_return",portfeuilleCloses)
                        .queryParam("exposure",capitals).queryParam("CI",99).queryParam("days",1);
        System.out.println(builder.buildAndExpand().encode());*/
        //restTemplate.exchange(builder.buildAndExpand().toUri() , HttpMethod.GET, new RequestEntity<Object>(), new Object());
       // restTemplate.exchange(builder.buildAndExpand().toUri() , HttpMethod.GET,req);
        System.out.println(url);
        Object h = restTemplate.getForObject(url, Object.class);

        return h;
    }

    @Override
    public ArrayList<ArrayList<Float>> portfeuilleCloses(Long id) {
        Portfeuille p = repository.findById(id).get();
        ArrayList<ArrayList<Float>> portfeuilleCloses = new ArrayList<ArrayList<Float>>();
        double[][] covariance = new double[100][100];
        for (ProduitFinancier produit : p.getProduitFinanciers()) {
            portfeuilleCloses.add(getCloses(produit.getSymbol()));
        }
        return portfeuilleCloses;
    }

    @Override
    public ArrayList<Float> getCloses(String symbol) {
            RestTemplate restTemplate = new RestTemplate();
            String url = "https://www.ilboursa.com/api/charting/GetTicksEOD?symbol="+symbol+"&length=365&period=0&guid=4QGJkkSWRC8zaK_2x5U0QlsfzhyPz0Q3s_NRb_y78kc";
            Map<?,?> history = restTemplate.getForObject(url, Map.class);
            Object h = restTemplate.getForObject(url, Object.class);
            //Map<String,Float>  actionCloseHistory = new HashMap<String,Float>();
            String actionName = history.get("Name").toString();
            System.out.println(actionName);
            System.out.println(history.get("QuoteTab").getClass());
            ArrayList<Object> Quote = (ArrayList<Object>) history.get("QuoteTab");
            ArrayList<Float> closes = new ArrayList<>();
            for (int i=0;i< Quote.size();i++){
                //closes[i]=
                Map<Object,Object> actionCloseHistory = (HashMap) Quote.get(i);
                //double c = (double)((Integer) actionCloseHistory.get("c"));
                //System.out.println("c : "+c);
                closes.add( Float.valueOf(actionCloseHistory.get("c").toString()));
                //(double)((Integer) );

            }
            //double e = esperenceAction(getLNCours(closes));
            //double et = ecartypeAction(getLNCours(closes),e);
            return closes;
    }


    double varAction(ArrayList<Double> closes, double montant){
        double confiance = 0.95;
        int horizon = 1;

        return centille(closes,confiance)*montant*Math.sqrt(horizon);
    }

    ArrayList<Double> getLNCours(ArrayList<Float> closes){
        ArrayList<Double> ln =new ArrayList<>();
        for (int i = 0; i < closes.size()-1; i++) {
            float a=0;
            try {
                 a = closes.get(i+1)/closes.get(i);
            }catch (NullPointerException e){
                System.out.println(e);
            }
            finally {

                ln.add(Math.log(a));
                //System.out.println(a+" : " +Math.log(a));
            }

        }
        return ln;
    }

    double esperenceAction(ArrayList<Double> closes){
        double somme=0;
        for (Double o:closes   ) {
            somme+=o;
        }
        System.out.println(somme/closes.size() );
        DecimalFormat df = new DecimalFormat("#.#######");
        df.setRoundingMode(RoundingMode.HALF_UP);
        String fr =  df.format(somme/closes.size());
        String newfr ="";
        for (int i = 0; i <fr.length() ; i++) {
            if(fr.charAt(i)==','){
                newfr=newfr+'.';
            }
            else {
                newfr=newfr+fr.charAt(i);
            }

        }
        System.out.println(Double.parseDouble(newfr));
        return Double.parseDouble(newfr);
    }

    double ecartypeAction(ArrayList<Double> closes,double e){
        double et=0;
        for (Double o:closes   ) {
            et += (o-e)*(o-e);
        }

        System.out.println(Math.sqrt(et/ closes.size()));
        DecimalFormat df = new DecimalFormat("#.#######");
        df.setRoundingMode(RoundingMode.HALF_UP);
        String fr =  df.format(Math.sqrt(et/ closes.size()));
        String newfr ="";
        for (int i = 0; i <fr.length() ; i++) {
            if(fr.charAt(i)==','){
                newfr=newfr+'.';
            }
            else {
                newfr=newfr+fr.charAt(i);
            }

        }
        System.out.println(Double.parseDouble(newfr));

        return Double.parseDouble(newfr);
    }

    double poidAction(float capital,Portfeuille p){
        float somme=0;
        for (ProduitFinancier produit:p.getProduitFinanciers() ) {
            somme+=(produit.getMontantAchat()*produit.getQuantite());
        }
        return capital/somme;
    }

    double centille(ArrayList<Double> closes,double confiance){
        Collections.sort(closes);
        double somme=0;
        for (double c: closes ) {
            somme+=c;
        }
        double centille =0;
        double total=0;
        int i=0;
        while (i< closes.size()&&total<(confiance*somme)){
            total+=closes.get(i);
            i++;
        }

        return closes.get(i);
    }
/*
    double[][] covariance(Long id){
        Portfeuille p = repository.findById(id).get();
        double[][] covariance = new double[100][100];
        ArrayList<String> tickers = new ArrayList<String>();
        for (ProduitFinancier produit:p.getProduitFinanciers() ) {
            tickers.add(produit.getTitre());
        }
        for (int i = 0; i < tickers.size(); i++) {
            for (int j = 0; j < tickers.size(); j++) {
                if(i==j){
                    double e = esperenceAction(getLNCours(getCloses(tickers.get(i))));
                    covariance[i][j]=ecartypeAction(getLNCours(getCloses(tickers.get(i))),e);
                }else{
                    covariance[i][j];
                }

            }

        }
        return null;
    }*/

    /*
    @Override
    public void addProduitToPortfeuille(Long id,ProduitFinancier p) {
        Portfeuille portfeuille= repository.findById(id).get();
        Long produit = p.getIdProduit();
        if (repository.findById(id).isPresent()&&repositoryProduit.findById(produit).isPresent()) {
            p.setPortfeuille(portfeuille);
            portfeuille.getProduitFinanciers().add(p);
            repositoryProduit.save(p);
            System.out.println("Produit ajouté");
        } else {
            System.out.println("Erreur Ajout");
        }
    }*/
}
