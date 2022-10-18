package com.jmlessous.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmlessous.entities.Compte;
import com.jmlessous.entities.CompteCourant;
import com.jmlessous.entities.Utilisateur;
import com.jmlessous.repositories.CompteCourantRepository;
import com.jmlessous.repositories.UtilisateurRepository;

@Service
public class CompteCourantServiceImpl implements ICompteService{
	@Autowired
	CompteCourantRepository comptecourantRepository ;
	@Autowired
	UtilisateurRepository utilisateurRepository;

	@Override
	public Compte addAccountC(CompteCourant c ,Long idUser) {
		Utilisateur utilisateur = utilisateurRepository.findById(idUser).orElse(null);
		Float s = utilisateur.getSalaire();
		c.setMontantDecouvert(s);
		c.setUtilisateurC(utilisateur);
		comptecourantRepository.save(c);
		return c;
	}

	@Override
	public List<CompteCourant> retrieveAllAccountsC() {
		return  (List<CompteCourant>)comptecourantRepository.findAll();
	}

	@Override
	public CompteCourant updateAccountC(CompteCourant u) {
		comptecourantRepository.save(u);
		return u;
	}

	@Override
	public CompteCourant retrieveAccount(Long idCourant) {
		CompteCourant comptecourant = comptecourantRepository.findById(idCourant).orElse(null);
		return (comptecourant);
	}

	@Override
	public void deleteAccount(Long id) {
		comptecourantRepository.deleteById(id);
		
	}


	@Override
	public CompteCourant ArchiveCompteC(Long id) {
		CompteCourant cc = comptecourantRepository.findById(id).orElse(null);
		cc.setState(false);
		comptecourantRepository.save(cc);
		return cc;
	}
	

}
