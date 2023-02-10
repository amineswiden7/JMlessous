package com.jmlessous.services;

import java.util.List;

import com.jmlessous.entities.Absence;
import com.jmlessous.entities.Formation;

public interface IFormationService {
	
List<Formation> retrieveAllFormation();
Formation retrieveFormation(Long idFormation);
Formation addFormation(Formation f);
Formation updateFormation(Formation formation);
void deleteFormation(Long idFormation);
void SuppAllFormation();

}
