package com.jmlessous.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jmlessous.entities.Formation;
import com.jmlessous.repositories.FormationRepository;
import com.jmlessous.repositories.UtilisateurRepository;

@Service
public class FormationServiceImpl implements IFormationService {
	@Autowired 
	FormationRepository formarepo ; 
	@Autowired 
	UtilisateurRepository userrepo;
	
	@Override
	public List<Formation> retrieveAllFormation() {
		return (List<Formation>) formarepo.findAll();
	}


	@Override
	public Formation addFormation(Formation f) {
	     return formarepo.save(f);
	}

	
	@Override
	public void SuppAllFormation() {
		formarepo.deleteAll();
	}


	@Override
	public Formation retrieveFormation(Long idFormation) {
		return formarepo.findById(idFormation).orElse(null);
	}


	@Override
	public Formation updateFormation(Formation formation) {
		formarepo.save(formation);
		return formation;
	}


	@Override
	public void deleteFormation(Long idFormation) {
		formarepo.deleteById(idFormation);
	}

}
