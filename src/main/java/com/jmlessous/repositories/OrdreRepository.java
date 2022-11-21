package com.jmlessous.repositories;

import com.jmlessous.entities.OffreAssurance;
import com.jmlessous.entities.Ordre;
import com.jmlessous.entities.Portfeuille;
import com.jmlessous.entities.TypeAssurance;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdreRepository extends CrudRepository<Ordre,Long> {
    @Query("SELECT o from  Ordre o where o.portfeuille = ?1")
    List<Ordre> retrieveOrdreByPorfeuille(Portfeuille p);
}
