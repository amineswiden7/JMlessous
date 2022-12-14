package com.jmlessous.services;

import java.util.List;

import com.jmlessous.entities.Absence;
import com.jmlessous.entities.Formation;

public interface IFormationService {
	
List<Formation> retrieveAllFormation();
Formation retrieveFormation(String idFormation);
Formation addFormation(Formation f);
Formation updateFormation(Formation formation);
void deleteFormation(String idFormation);
void SuppAllFormation();

}
