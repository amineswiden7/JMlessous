package com.jmlessous.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jmlessous.entities.CompteCourant;
import com.jmlessous.entities.CompteEpargne;
import com.jmlessous.entities.NatureEpargne;

@Repository

public interface CompteEpargneRepository extends CrudRepository<CompteEpargne,Long>{
	
	//List<CompteEpargne> findByNatureEpargne(NatureEpargne natureepargne);

}
