package com.jmlessous.services;

import com.jmlessous.entities.Credit;
import com.jmlessous.entities.CreditEtudiant;
import com.jmlessous.entities.CreditImmobilier;
import com.jmlessous.entities.Amortissement;


import java.util.List;

public interface ICreditImobService {
    List<CreditImmobilier> retrieveAllCredit();

    CreditImmobilier addCredit(CreditImmobilier a );

    List<CreditImmobilier> retrieveCreditByUser(Long id_user);

    Credit Archive(Long Id_credit);
    Credit retrieveActiveCredit(Long id_User);
    CreditImmobilier retrieveCreditById(Long id);
    float Calcul_mensualite(CreditImmobilier cr);

    CreditImmobilier addCreditt (CreditImmobilier a , Long id_User);

    float calculTaux(CreditImmobilier a );
    float calculTauxSim(CreditImmobilier a);
    Amortissement[] TabAmortissementt(CreditImmobilier cr);
     Amortissement Simulateurr(CreditImmobilier cr);
     void TraitementCredit();
     void acceptercredit(Long id);
    void Refusercredit(Long id);
    CreditImmobilier transemtre (CreditImmobilier a );










}
