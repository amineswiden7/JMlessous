package com.jmlessous.services;

import com.jmlessous.entities.Portfeuille;
import com.jmlessous.entities.ProduitFinancier;
import com.jmlessous.entities.StatusProduit;
import com.jmlessous.entities.Utilisateur;
import com.jmlessous.repositories.PortfeuilleRepository;
import com.jmlessous.repositories.ProduitFinancierRepository;
import com.jmlessous.repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
    public float calculPerformance(Long id) {
        if( repository.findById(id).isPresent()){
            Portfeuille portfeuille= repository.findById(id).get();

            for (ProduitFinancier p: portfeuille.getProduitFinanciers()) {
                float gain = p.getMontantAchat()*p.getQuantite();
            }
        }
        return 0;
    }
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
