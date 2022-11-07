package com.jmlessous.services;

import java.util.List;
import java.util.Set;

import com.jmlessous.entities.Compte;
import com.jmlessous.entities.CompteCourant;
import com.jmlessous.entities.CompteEpargne;
import com.jmlessous.entities.Transaction;

public interface ICompteService {

List<CompteCourant> retrieveAllAccountsC();
public Compte addAccountC (CompteCourant c,Long idUser ) ; 
CompteCourant updateAccountC(CompteCourant u);
CompteCourant retrieveAccount(Long id);
void deleteAccount(Long id);
CompteCourant ArchiveCompteC(Long id);
public String GenerateRibC() ; 
public String GenerateIBanC(String RibC);


	//public List<Transaction> retrieveAllTransactionsEmisesByRib(Long rib) ;
	//public List<Transaction> retrieveAllTransactionsRecuesByRib(Long rib) ;
    //public Set<Transaction> retrieveAllTransactionsEmises1ByRib( String rib ); 
}
