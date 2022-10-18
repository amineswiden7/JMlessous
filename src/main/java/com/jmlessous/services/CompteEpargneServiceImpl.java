package com.jmlessous.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmlessous.entities.Compte;
import com.jmlessous.entities.CompteCourant;
import com.jmlessous.entities.CompteEpargne;
import com.jmlessous.entities.Utilisateur;
import com.jmlessous.repositories.CompteCourantRepository;
import com.jmlessous.repositories.CompteEpargneRepository;
import com.jmlessous.repositories.UtilisateurRepository;

@Service
public class CompteEpargneServiceImpl implements ICompteEService{

	@Autowired
	CompteEpargneRepository compteepargneRepository ;
	@Autowired
	UtilisateurRepository utilisateurRepository;
	
	@Override
	public List<CompteEpargne> retrieveAllAccountsE() {
		return  (List<CompteEpargne>)compteepargneRepository.findAll();
	}

	@Override
	public Compte addAccountE(CompteEpargne e, Long idUser) {
		Utilisateur utilisateur = utilisateurRepository.findById(idUser).orElse(null);
		Float taux = null;
		e.setTauxRemuneration(taux);
		e.setUtilisateurC(utilisateur);
		compteepargneRepository.save(e);
		return e;
	}

	@Override
	public CompteEpargne updateAccountE(CompteEpargne e) {
		compteepargneRepository.save(e);
		return e;
	}

	@Override
	public CompteEpargne retrieveAccount(Long id) {
		CompteEpargne compteepargne = compteepargneRepository.findById(id).orElse(null);
		return (compteepargne);
	}

	@Override
	public void deleteAccount(Long id) {
		compteepargneRepository.deleteById(id);
	}

	@Override
	public CompteEpargne ArchiveCompteE(Long id) {
		CompteEpargne ce = compteepargneRepository.findById(id).orElse(null);
		ce.setState(false);
		compteepargneRepository.save(ce);
		return ce;
	}



}
