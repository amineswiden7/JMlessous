package com.jmlessous.services;

import java.util.List;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
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
		e.setUtilisateurC(utilisateur);
		String r = GenerateRibE();
		e.setTauxRemuneration((float) 0);
		//e.setIndex_remuneration(0);
		e.setRib(r);
		e.setIban(GenerateIBanE(r));
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
	
	/*
	//calcul du taux d'interet pour les comptes epargne 
	//chaque jour à minuit
	//“At 00:00.” nxet at 2023-03-23 00:00:00
	@Scheduled(cron = "0 0 0 * * *", zone="Africa/Tunis" ) 
	@Transactional
	@Override
	public void GetInterestAmount() {
		//calcul taux journalier
    	final float T_journalier=(float) (0.05/360);
    	List<CompteEpargne> List_Acc = (List<CompteEpargne>) compteepargneRepository.findAll(); 
    	for (CompteEpargne acc: List_Acc){
    	acc.setTauxRemuneration( ( acc.getSolde()+acc.getTauxRemuneration() ) * T_journalier + acc.getTauxRemuneration() );
    	
    	if(acc.getIndex_remuneration()+1==90)
    		{
    		//on a retranch' la retenue à la source de 20%
    		 acc.setSolde(acc.getSolde()+acc.getTauxRemuneration()*0.8f);
             acc.setIndex_remuneration(0);
             acc.setTauxRemuneration((float) 0);
    		}
    	else acc.setIndex_remuneration(acc.getIndex_remuneration()+1);
    	compteepargneRepository.save(acc);	
	}
		
	}*/

	@Override
	public String GenerateRibE() {
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

	@Override
	public String GenerateIBanE(String RibE) {
		String codePays="TN";
		String IbanE = codePays + RibE;
		return IbanE;
	}

}
