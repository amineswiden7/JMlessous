package com.jmlessous.repositories;

import com.jmlessous.entities.OffreAssurance;
import com.jmlessous.entities.Portfeuille;
import com.jmlessous.entities.TypeAssurance;
import com.jmlessous.entities.Utilisateur;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PortfeuilleRepository extends CrudRepository<Portfeuille,Long> {
    @Query("SELECT p from  Portfeuille p where p.utilisateur = ?1")
    Portfeuille retrievePortfeuilleByUser(Utilisateur u);
}
