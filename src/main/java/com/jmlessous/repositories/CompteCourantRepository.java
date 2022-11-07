package com.jmlessous.repositories;

import java.util.List;

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


//	@Query("SELECT t FROM User t WHERE t.utilisateurC.email= :email")
//	List<Transaction> getUserByEmail(@Param("email") String email);

}
