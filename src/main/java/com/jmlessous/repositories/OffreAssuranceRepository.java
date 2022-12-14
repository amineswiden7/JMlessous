package com.jmlessous.repositories;

import com.jmlessous.entities.OffreAssurance;
import com.jmlessous.entities.StatutAssurance;
import com.jmlessous.entities.TypeAssurance;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.security.SecureRandom;
import java.util.List;

@Repository
public interface OffreAssuranceRepository extends CrudRepository<OffreAssurance,Long> {

    @Query("SELECT o from  OffreAssurance o where o.type = ?1")
    List<OffreAssurance> retrieveOffreByType(TypeAssurance type);

    @Query("SELECT o from  OffreAssurance o where o.statut = ?1")
    List<OffreAssurance> retrieveOffreByStatut(StatutAssurance statut);

    @Query("SELECT o from  OffreAssurance o where o.assurance = ?1")
    List<OffreAssurance> retrieveOffreByAssurance(String assurance);

    @Query("SELECT distinct o.assurance from  OffreAssurance o ")
    List<String> retrieveAllAssurance();

}
