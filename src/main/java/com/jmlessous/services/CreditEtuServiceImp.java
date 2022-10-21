package com.jmlessous.services;

import com.jmlessous.entities.*;
import com.jmlessous.repositories.CreditEtuRepository;
import com.jmlessous.repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CreditEtuServiceImp implements ICreditEtu{


    @Autowired
    CreditEtuRepository etu;
    UtilisateurRepository ut;





    @Override
    public List<CreditEtudiant> retrieveAllCredit() {
        return (List<CreditEtudiant>) etu.findAll();
    }

    @Override
    public CreditEtudiant addCredit(CreditEtudiant a, Long id_User) {
        Utilisateur U1=ut.findById(id_User).orElse(null);
        CreditEtudiant ce= etu.getActiveCreditByUser(id_User);
        if(U1.getProfession() == Profession.ETUDIANT){
            if(ce==null) {
                if (a.getNiveauEtude() == NiveauEtude.INGENIERIE) {
                    a.setTauxInteret((float) 0.03);


                } else if (a.getNiveauEtude() == NiveauEtude.MASTER) {
                    a.setTauxInteret((float) 0.025);

                } else
                    a.setTauxInteret((float) 0.02);
                float montant = a.getMontantCredit();
                float tauxmensuel = a.getTauxInteret() / 12;
                float period = a.getDuree() * 12;
                float mensualite = (float) ((montant * tauxmensuel) / (1 - (Math.pow((1 + tauxmensuel), -period))));
                a.setMensualite(mensualite);
                a.setSTATUS(Status.ENCOURSDETRAITEMENT);
                etu.save(a);
                return a;

            }

        }


        return null;
    }

    @Override
    public List<CreditEtudiant> retrieveCreditByUser(Long id_user) {
        return etu.getCreditByUser(id_user);
    }

    @Override
    public CreditEtudiant Archive(Long Id_credit) {
        return null;
    }

    @Override
    public CreditEtudiant retrieveActiveCredit(Long id_User) {
        return null;
    }

    @Override
    public float Calcul_mensualite(CreditEtudiant cr) {
        return 0;
    }
}
