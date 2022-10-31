package com.jmlessous.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jmlessous.entities.Compte;
import com.jmlessous.entities.CompteCourant;

@Repository
public interface CompteRepository extends CrudRepository<Compte,String>{
	Compte findByRib(String emetteur);

}
