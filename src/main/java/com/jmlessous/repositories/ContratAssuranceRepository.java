package com.jmlessous.repositories;

import com.jmlessous.entities.ContratAssurance;
import com.jmlessous.entities.OffreAssurance;
import com.jmlessous.entities.TypeAssurance;
import com.jmlessous.entities.Utilisateur;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContratAssuranceRepository extends CrudRepository<ContratAssurance,Long> {

    @Query("SELECT c from  ContratAssurance c join Utilisateur ut on c.utilisateurCA.idUser=ut.idUser where ut.idUser = ?1")
    List<ContratAssurance> retrieveContratByUtilisateur(Long userId);

    @Query("SELECT c from  ContratAssurance c join OffreAssurance o on c.offreAssurance.idOffreAssurance=o.idOffreAssurance where o.type = ?1")
    List<ContratAssurance> retrieveContratByTypeAssurance(TypeAssurance t);

}
