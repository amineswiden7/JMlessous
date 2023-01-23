package com.jmlessous.services;

import com.jmlessous.entities.*;
import com.jmlessous.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
public class ProduitFinancierServiceImpl implements IProduitFinancierService{
    @Autowired
    ProduitFinancierRepository repository;
    @Autowired
    PortfeuilleRepository repoPort;
    @Autowired
    OrdreRepository ordreRepository;
    @Autowired
    CompteRepository accrepo ;
    @Autowired
    IUtilisateurService utilisateurService;
    @Override
    public List<ProduitFinancier> retrieveAllProduits() {
        return (List<ProduitFinancier>) repository.findAll();
    }

 //   @Override
    public ProduitFinancier addProduit1(ProduitFinancier p,Long id) {

        Portfeuille portfeuille = repoPort.findById(id).get();
        float prix = p.getMontantAchat()*p.getQuantite();

        if(prix<=portfeuille.getSolde()){

        portfeuille.setSolde(portfeuille.getSolde()-prix);

        p.setStatusProduit(StatusProduit.ENCOURS);
        p.setTypeProduit(TypeProduit.ACTION);
        p.setPortfeuille(portfeuille);

        return repository.save(p);
        }

        return null;
    }

    @Override
    public ProduitFinancier addProduit(ProduitFinancier p,float prix,Long qte,Long id) {

        Portfeuille portfeuille = repoPort.findById(id).get();
        float prixachat = p.getMontantAchat()*p.getQuantite();
        Ordre o = new Ordre();
        if(prix<=portfeuille.getSolde()){
            boolean trouve = false;
            ProduitFinancier produit = new ProduitFinancier();
            for (ProduitFinancier produit1 : portfeuille.getProduitFinanciers() ) {
                if(produit1.getIsin().equals(p.getIsin())) {
                    trouve = true;
                    produit=produit1;
                    break;
                }
            }
            portfeuille.setSolde(portfeuille.getSolde()-prixachat);
            //en cas de produit inexistant
            if(!trouve) {
                System.out.println("produit inexistant!");
                p.setStatusProduit(StatusProduit.ENCOURS);
                p.setTypeProduit(TypeProduit.ACTION);
                p.setPortfeuille(portfeuille);


                //add ordre
                o.setTypeOrdre(TypeOrdre.ACHAT);
               /* SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                LocalDateTime now = LocalDateTime.now();*/
                //parseFormat.parse(dateFormat.format(date));
                o.setDate(new Date());
                o.setPrix(prix);
                o.setQuantite(qte);
                o.setPortfeuille(portfeuille);
                o.setProduitFinancier(repository.save(p));
                ordreRepository.save(o);

                return p;
            }
            //en cas de mise à jour de produit
            else{
                float total = produit.getMontantAchat() * produit.getQuantite() + p.getMontantAchat() * p.getQuantite();
                long qteTotal = p.getQuantite() + produit.getQuantite();
                produit.setQuantite(qteTotal);
                float prixTot = total / qteTotal;
                produit.setMontantAchat(prixTot);
                produit.setStatusProduit(StatusProduit.ENCOURS);
                System.out.println("produit existant!");

                o.setTypeOrdre(TypeOrdre.ACHAT);

                o.setDate(new Date());
                o.setPrix(prix);
                o.setQuantite(qte);
                o.setPortfeuille(portfeuille);
                o.setProduitFinancier(repository.save(produit));
                ordreRepository.save(o);


                return produit;
            }

        }

        return null;
    }

    @Override
    public void deleteProduit(Long id) {
        ProduitFinancier produit= repository.findById(id).get();
        if (repository.findById(id).isPresent()) {
            repository.delete(produit);
            System.out.println("Produit Financier effacé");
        } else {
            System.out.println("Produit financier introuvable");
        }
    }


    @Override
    public ProduitFinancier retrieveProduit(Long id) {
        ProduitFinancier produit= repository.findById(id).get();
        if (repository.findById(id).isPresent()) {
            return produit;
        } else {
            System.out.println("Produit Financier n'existe pas");
        }
        return null;
    }

    @Override
    public void vendreProduit(ProduitFinancier p,float prix,Long qte,Long id) {

        Ordre o = new Ordre();

        Portfeuille portfeuille = repoPort.findById(id).get();

        ProduitFinancier produit = new ProduitFinancier();
        for (ProduitFinancier produit1 : portfeuille.getProduitFinanciers() ) {
            if(produit1.getIsin().equals(p.getIsin())) {
                produit=produit1;
                break;
            }
        }


        if(qte==produit.getQuantite())
            produit.setStatusProduit(StatusProduit.VENDU);
        produit.setQuantite(produit.getQuantite()-qte);


        portfeuille.setSolde(portfeuille.getSolde()+(prix*qte)*0.995f);

        o.setProduitFinancier(repository.save(produit));
        o.setPrix(prix);
        o.setQuantite(qte);
        o.setDate(new Date());
        o.setTypeOrdre(TypeOrdre.VENTE);
        o.setPortfeuille(portfeuille);
        ordreRepository.save(o);
        Compte acc_dest = accrepo.findByRib("0192000410BC6FTJQJ5VS11");
        System.out.println("solde avant : "+acc_dest.getSolde());
        acc_dest.setSolde(acc_dest.getSolde()+(prix*qte*0.005f));
        System.out.println("solde apres : "+acc_dest.getSolde());
        accrepo.save(acc_dest);

    }
}
