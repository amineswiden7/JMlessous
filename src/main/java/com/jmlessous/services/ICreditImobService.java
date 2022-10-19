package com.jmlessous.services;

import com.jmlessous.entities.Absence;
import com.jmlessous.entities.Credit;
import com.jmlessous.entities.CreditImmobilier;

import java.util.List;

public interface ICreditImobService {
    List<CreditImmobilier> retrieveAllCredit();

    CreditImmobilier addCredit(Credit a , Long id_User);

    List<Credit> retrieveAbsenceByUser(Long id_user);

    Credit Archive(Long Id_credit);
    Credit retrieveActiveCredit(Long id_User);
    float Calcul_mensualite(Credit cr);








}
