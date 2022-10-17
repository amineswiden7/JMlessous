package com.jmlessous.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jmlessous.entities.CompteCourant;
import com.jmlessous.entities.CompteEpargne;

@Repository

public interface CompteEpargneRepository extends CrudRepository<CompteEpargne,Long>{

}
