package com.jmlessous.repositories;

import com.jmlessous.entities.Credit;
import com.jmlessous.entities.CreditEtudiant;
import com.jmlessous.entities.CreditImmobilier;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CreditEtuRepository extends CrudRepository<CreditEtudiant,Long> {
    @Query("SELECT c FROM CreditEtudiant c WHERE c.compteCredit.utilisateurC.idUser= :id and c.STATUS= 'ACCEPTE' and c.FinC=false ")
    CreditEtudiant getActiveCreditByUser(@Param("id") Long idUser);

    @Query("SELECT c FROM CreditEtudiant c WHERE c.compteCredit.utilisateurC.idUser= :id")
    List<CreditEtudiant> getCreditByUser(@Param("id") Long idUser);


}
