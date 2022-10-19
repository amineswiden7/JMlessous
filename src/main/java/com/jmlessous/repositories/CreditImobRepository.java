package com.jmlessous.repositories;

import com.jmlessous.entities.Absence;
import com.jmlessous.entities.CompteEpargne;
import com.jmlessous.entities.Credit;
import com.jmlessous.entities.CreditImmobilier;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CreditImobRepository extends CrudRepository<CreditImmobilier,Long> {

    @Query("SELECT c FROM Credit c WHERE c.compteCredit.utilisateurC.idUser= :id")
    List<Credit> getCreditByUser(@Param("id") Long idUser);





}
