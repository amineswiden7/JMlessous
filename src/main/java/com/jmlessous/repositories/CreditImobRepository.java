package com.jmlessous.repositories;

import com.jmlessous.entities.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CreditImobRepository extends CrudRepository<CreditImmobilier,Long> {

    @Query("SELECT c FROM Credit c WHERE c.compteCredit.utilisateurC.idUser= :id")
    List<CreditImmobilier> getCreditByUser(@Param("id") Long idUser);


    @Query("SELECT c FROM CreditImmobilier c WHERE c.compteCredit.utilisateurC.idUser= :id and c.STATUS= 'ACCEPTE' and c.FinC=false ")
    CreditImmobilier getActiveCreditImobByUser(@Param("id") Long idUser);


    @Query("SELECT c FROM Credit c WHERE c.STATUS='ENCOURSDETRAITEMENT'")
    List<CreditImmobilier> getCredit();




}
