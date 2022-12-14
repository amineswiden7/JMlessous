package com.jmlessous.repositories;


import com.jmlessous.entities.CreditEtudiant;
import com.jmlessous.entities.CreditLibre;
import com.jmlessous.entities.Status;
import com.jmlessous.entities.Utilisateur;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CreditLibreRepository extends CrudRepository<CreditLibre,Long> {
    @Query("SELECT c FROM CreditLibre c WHERE c.compteCredit.utilisateurC.idUser= :id and c.STATUS= 'ACCEPTE' and c.FinC=false ")
    CreditLibre getActiveCreditByUser(@Param("id") Long idUser);

    @Query("SELECT c FROM CreditLibre c WHERE c.compteCredit.utilisateurC.idUser= :id")
    List<CreditLibre> getCreditByUser(@Param("id") Long idUser);


    //Chercher si un client a un credit actif .
    @Query(value="SELECT * FROM CreditLibre c WHERE c.compteCredit.utilisateurC.idUser=:idclient and c.STATUS = true and c.FinC =true limit 1 ",nativeQuery = true)
    CreditLibre getlastCreditsByClient(@Param("idclient") Long idClient);

    //selectionner le dernier credit complet pour tester son historique
    @Query(value = "SELECT * FROM CreditLibre c WHERE c.compteCredit.utilisateurC.idUser=:idclient and c.STATUS=true and c.FinC=true ORDER BY dateDemande DESC limit 1" , nativeQuery =true)
    CreditLibre getIDofLatestCompletedCreditsByClient(@Param("idclient") Long idClient);

    List<CreditLibre> getCreditLibreBySTATUS(@Param("status") Status status);


}
