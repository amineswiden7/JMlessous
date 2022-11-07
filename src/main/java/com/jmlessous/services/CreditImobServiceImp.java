package com.jmlessous.services;

import com.jmlessous.entities.*;
import com.jmlessous.repositories.CompteCourantRepository;
import com.jmlessous.repositories.CreditImobRepository;
import com.jmlessous.repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class CreditImobServiceImp implements ICreditImobService {

    @Autowired
    CreditImobRepository cr;
    CompteCourantRepository cp;
    UtilisateurRepository u;

    @Override
    public List<CreditImmobilier> retrieveAllCredit() {
        return (List<CreditImmobilier>) cr.findAll();
    }

    @Override
    public CreditImmobilier addCredit(Credit a, Long id_User) {
        Utilisateur uu = u.findById(id_User).orElse(null);
        CompteCourant c = cp.getCompteByUser(id_User);
        a.setCompteCredit(c);
        a.setDateDemande(new Date());

        float montant=a.getMontantCredit();
        float tauxmensuel=12;
        float period= a.getDuree()*12;
        float mensualite=(float) ((montant*tauxmensuel)/(1-(Math.pow((1+tauxmensuel),-period ))));
        a.setMensualite(mensualite);
       // a.setMensualite(100F);
        float ratio = a.getMensualite()/uu.getSalaire();
        if (uu.getCreditAuthorization() == true) {   //
            //NB LE TAUX DE RISQUE 1%<R<2.5%
            if (ratio<=0.4) {
                //CALCUL RISK
                //a.setTaux((float) (0.05+(ratio/20)));
                float montantt=a.getMontantCredit();
                //float tauxmensuell=a.getTaux()/12;
                float tauxmensuell=12;
                float periodd= a.getDuree()*12;
                float mensualitee=(float) ((montantt*tauxmensuell)/(1-(Math.pow((1+tauxmensuell),-periodd ))));
                a.setMensualite(mensualitee);
               // a.setMensualite(200F);
               // uu.setCreditAuthorization(true);
                //Acceptation(credit,fund,"NouveauClient avec garant certifié");
               a.setSTATUS(Status.ACCEPTE);
                a.setDateFin(java.sql.Date.valueOf(Instant.ofEpochMilli(a.getDateDemande().getTime())
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate().plusMonths((long) (periodd))));



            } else {
                a.setSTATUS(Status.REFUS);

                a.setMotif("montant guarantie insuffisant il doit etre égale au minimum 40% d'écheance de crédit");
            }

        }







            return null;
}

    @Override
    public List<Credit> retrieveCreditByUser(Long id_user) {
        return cr.getCreditByUser(id_user);
    }

    @Override
    public Credit Archive(Long Id_credit) {
        return null;
    }

    @Override
    public Credit retrieveActiveCredit(Long id_User) {
        return null;
    }

    @Override
    public float Calcul_mensualite(Credit cr) {
        return 0;
    }
}
