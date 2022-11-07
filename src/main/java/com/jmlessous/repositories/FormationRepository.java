package com.jmlessous.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import com.jmlessous.entities.Formation;

public interface FormationRepository extends CrudRepository<Formation,String>{
	
}
