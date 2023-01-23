package com.jmlessous.services;

import com.jmlessous.entities.Absence;
import com.jmlessous.entities.OffreAssurance;

import java.util.List;

public interface IAbsenceService {

    List<Absence> retrieveAllAbsences();

    Absence addOffre(Absence a );

    List<Absence> retrieveAbsenceByUser(Long id_user);

    Absence updateAbsence(Absence a , Long id_user);


}
