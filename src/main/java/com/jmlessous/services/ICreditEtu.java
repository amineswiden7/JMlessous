package com.jmlessous.services;

import com.jmlessous.entities.Credit;
import com.jmlessous.entities.CreditEtudiant;
import com.jmlessous.entities.CreditImmobilier;
import com.jmlessous.entities.NiveauEtude;

import java.util.Hashtable;
import java.util.List;

public interface ICreditEtu {
    List<CreditEtudiant> retrieveAllCredit();

    CreditEtudiant addCredit(CreditEtudiant a , Long id_User);

    List<CreditEtudiant> retrieveCreditByUser(Long id_user);

    CreditEtudiant Archive(Long Id_credit);
    CreditEtudiant retrieveActiveCredit(Long id_User);
    float Calcul_mensualite(CreditEtudiant cr);
    void ActiverCredit(Long a);
    Hashtable<String,Double> simulation(float montant,float periode, NiveauEtude typeEtudiant);
    Double Scoring(float montant,float periode, NiveauEtude typeEtu);









}
