package com.jmlessous.services;

import com.jmlessous.entities.Absence;
import com.jmlessous.entities.CreditImmobilier;

import java.util.List;

public interface ICreditImobService {
    List<CreditImmobilier> retrieveAllCredit();

    CreditImmobilier addCredit(Absence a , Long id_Compte,Long id_garantie);

    List<Absence> retrieveAbsenceByUser(Long id_user);

    Absence updateAbsence(Absence a , Long id_user);








}
