package com.jmlessous.repositories;

import com.jmlessous.entities.Ordre;
import com.jmlessous.entities.Portfeuille;
import com.jmlessous.entities.ProduitFinancier;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProduitFinancierRepository extends CrudRepository<ProduitFinancier,Long> {
    @Query("SELECT p from  ProduitFinancier p where p.portfeuille = ?1")
    List<Ordre> retrieveProduitEnCours(Portfeuille p);
}
