package com.jmlessous.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.jmlessous.entities.Compte;
import com.jmlessous.entities.CompteCourant;
import com.jmlessous.entities.Transaction;
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
	public CompteCourant addAccountC(CompteCourant c ,Long idUser) {
		Utilisateur utilisateur = utilisateurRepository.findById(idUser).orElse(null);
		Float s = utilisateur.getSalaire();
		String r = GenerateRibC();
		//convention => Montant découvert d'un compte = 1 salaire 
		c.setMontantDecouvert(s);
		c.setUtilisateurC(utilisateur);
		c.setRib(r);
		c.setIban(GenerateIBanC(r));
		c.setDateOuverture(new Date());
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
	
	///////---------------------Generation Du Rib-------------------------------------------- /////////
	/*
	Chaque lettre est remplacée par son équivalent numérique :

		A,J = 1 ; B,K,S = 2 ; C,L,T = 3 ; D,M,U = 4 ; E,N,V = 5
		F,O,W = 6 ; G,P,X = 7 ; H,Q,Y = 8 ; I,R,Z = 9

		La clé peut alors être calculée avec la formule suivante :

		Clé RIB = 97 - ( (
		   89 x Code banque +
		   15 x Code guichet +
		   3 x Numéro de compte ) modulo 97 )
    */
	@Override
	public String GenerateRibC() {
	    //(code_B.MAX_VALUE==5)
		//&& (Num_Compte.length()==11)
		int i=0;
		String codeBanque="01920";
		String codeGuichet="00410";
		
		    int leftLimit = 48; // numeral '0'
		    int rightLimit = 122; // letter 'z'
		    int targetStringLength = 11;
		    Random random = new Random();
		    String generatedString = random.ints(leftLimit, rightLimit + 1)
		      .filter(k -> (k <= 57 || k >= 65) && (k <= 90 || k >= 97))
		      .limit(targetStringLength)
		      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
		      .toString();
		   System.out.println(generatedString);
		   
		//Lezmou ykoun sequenceee
		String Numero_Compte=generatedString; 
		String Code_G_v=codeBanque+codeGuichet+Numero_Compte ;
		Code_G_v=Code_G_v.toUpperCase() ;
		String[]  convert=new String[21] ;
		String retour ="" ; 
		  
		for (i=0 ; i<21 ; i++ )
		{
			if (Character.toString (Code_G_v.charAt(i)).matches("[A,J]") )
			{
			  convert[i]="1" ; 
			  
			}
			else if (Character.toString (Code_G_v.charAt(i)).matches("[B,K,S]"))
			{
				convert[i]="2" ;
			}
			else if (Character.toString (Code_G_v.charAt(i)).matches("[C,L,T ]"))
			{
				convert[i]="3" ;
			 
			}
			else if (Character.toString (Code_G_v.charAt(i)).matches("[D,M,U]"))
			{
				convert[i]="4" ;
			}
			else if (Character.toString (Code_G_v.charAt(i)).matches("[E,N,V]"))
			{
				convert[i]="5" ;
			
			}	
			else if (Character.toString (Code_G_v.charAt(i)).matches("[F,O,W]"))
			{
			
				convert[i]="6" ;
			}
			else if (Character.toString (Code_G_v.charAt(i)).matches("[G,P,X]"))
			{
			
				convert[i]="7" ;
			}
			else if (Character.toString (Code_G_v.charAt(i)).matches("[H,Q,Y]"))
			{
				convert[i]="8" ;
			
			}
			else if (Character.toString (Code_G_v.charAt(i)).matches("[I,R,Z]"))
			{
				convert[i]="9" ;
			
			}
			else if (Character.toString (Code_G_v.charAt(i)).matches("[0-9]"))
			{
				convert[i]=""+Code_G_v.charAt(i)+"" ;
			
			}
		}
		
		{
			for(int j=0 ;j<21;j++)
				retour=retour+convert[j]; 
	
		}
		int b=Integer.parseInt(retour.substring(0,5)) ;
		System.out.println(b);
		int g=Integer.parseInt(retour.substring(5,10)) ;
		System.out.println(g);
		Long c=Long.parseLong(retour.substring(10,21));
		System.out.println(c);
		Long x= 97 - ((89 * b + 15 * g  + 3 * c) % 97) ;
		if (0<=x &&  9>=x )
		{
			return Code_G_v+"0"+x ;
		}
		return Code_G_v+x ;
	}
	
	///////---------------------Generation Du IBan-------------------------------------------- /////////
	@Override
	public String GenerateIBanC(String RibC) {
		String codePays="TN";
		String IbanC = codePays + RibC;
		return IbanC;
	}

	@Override
	public List<CompteCourant> CompteCourantByRib(String rib) {
		
		return (List<CompteCourant>)comptecourantRepository.getcptByRib(rib);
	}
	

	

}
