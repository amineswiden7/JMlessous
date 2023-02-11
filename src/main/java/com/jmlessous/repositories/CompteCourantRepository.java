package com.jmlessous.repositories;

import java.util.List;

import com.jmlessous.entities.Absence;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jmlessous.entities.Compte;
import com.jmlessous.entities.CompteCourant;
import com.jmlessous.entities.Transaction;
import com.jmlessous.entities.Utilisateur;
@Repository
public interface CompteCourantRepository extends CrudRepository<CompteCourant,Long>{

    @Query("SELECT c FROM CompteCourant  c WHERE c.utilisateurC.idUser= :id")
    List<CompteCourant> getCompteByUser(@Param("id") Long idUser);
    

	@Query("SELECT c FROM CompteCourant c WHERE c.rib= :rib")
	CompteCourant getcptByRib(@Param("rib") String rib);

//	@Query("SELECT t FROM User t WHERE t.utilisateurC.email= :email")
//	List<Transaction> getUserByEmail(@Param("email") String email);

}
