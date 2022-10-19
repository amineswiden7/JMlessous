package com.jmlessous.services;

import com.jmlessous.entities.*;
import com.jmlessous.repositories.CompteCourantRepository;
import com.jmlessous.repositories.CreditImobRepository;
import com.jmlessous.repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;

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
        if (uu.getCreditAuthorization() == true) {   //(client nouveau)
            //NB LE TAUX DE RISQUE 1%<R<2.5%
            if (1.5 * (a.getGarantie().getValeur()+(uu.getSalaire()*12)) >= a.getMontantCredit()) {
                //CALCUL RISK
                a.setRisque((float) (0.01 + a.getMontantCredit() / ((a.getGarantie().getValeur()+uu.getSalaire()*12) * 100)));
               // uu.setCreditAuthorization(true);
                //Acceptation(credit,fund,"NouveauClient avec garant certifié");
                if(a.getRisque()< 1){




                }


            } else {
                a.setSTATUS(Status.REFUS);
                a.setMotif("montant guarantie insuffisant il doit etre egale à 0.33*montant du crédit");
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
