package com.jmlessous.services;

import java.util.List;

import com.jmlessous.entities.Compte;
import com.jmlessous.entities.CompteEpargne;

public interface ICompteEService {
	List<CompteEpargne> retrieveAllAccountsE();
	public Compte addAccountE (CompteEpargne c,Long idUser ) ; 
	CompteEpargne updateAccountE(CompteEpargne u);
	CompteEpargne retrieveAccount(Long id);
	void deleteAccount(Long id);
	CompteEpargne ArchiveCompteE(Long id);
}
