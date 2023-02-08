package com.jmlessous.repositories;

import com.jmlessous.entities.Absence;
import com.jmlessous.entities.OffreAssurance;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AbsenceRepository  extends CrudRepository<Absence,Long> {

    @Query("SELECT c FROM Absence c WHERE c.utilisateursAbsence.cin= :cin")
    List<Absence> getAbsenceByUser(@Param("cin") Long CinUser);

    @Query("SELECT c FROM Absence c WHERE c.utilisateursAbsence.idUser= :idUser")
    List<Absence> getAbsenceByIdUser(@Param("idUser") Long idUser);




}
