package com.jmlessous.services;

import java.util.Date;
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
		//Fixée par la banque centrale
		float TMM = ((float) 7.030);
		float TRE = (TMM-1)/100;
		e.setUtilisateurC(utilisateur);
		String r = GenerateRibE();
		e.setTauxRemuneration(TRE);
		//e.setIndex_remuneration(0);
		e.setRib(r);
		e.setIban(GenerateIBanE(r));
		//formule d'interet composé
		//capitalFinal = capitalInitial * (1+TRE) puiss (nbr d'année)
		float total = (float) (e.getVercement()*(Math.pow((1+TRE),e.getDuree())));
		//vercement sans interet
		e.setTotalVercement(e.getTotalVercement()+e.getVercement());
		//le gain grace aux interets 
		float totalinteret = total - e.getVercement();
		e.setInteretAcquis(totalinteret);
		//solde du compte 
		e.setSolde(total);
		e.setDateOuverture(new Date());
		compteepargneRepository.save(e);
		return e;
	}
	
	@Override
	public float GenerateMontant(CompteEpargne e) {
		//Fixée par la banque centrale
		float TMM = ((float) 7.030);
		float TRE = (TMM-1)/100;
		//formule d'interet composé
		//capitalFinal = capitalInitial * (1+TRE) puiss (nbr d'année)
		float total1 = (float) (e.getVercement()*(Math.pow((1+TRE),e.getDuree())));	
		//float n = e.getDuree()*12;
		//for (int i=1; i<n ;i++) {
		//float total2 = (float) (e.getVercementReg()*(Math.pow((1+TRE),e.getDuree()-(n/12))));
		//float t = total1 + total2;
		//System.out.println("total :" + total);
		//}
		return total1;
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
	/*
	//Fonction qui calcule le tabamortissement
		public CompteEpargne[] TabAmortissement(CompteEpargne ce)
		{   
			
				 
			double interest=ce.getTauxRemuneration()/12;
	        System.out.println("intereeeeeeeeeeeettt");
			System.out.println(ce.getTauxRemuneration());
	        int leng=(int) (ce.getDuree()*12);
	        System.out.println("perioddeeeeeeee");
	        System.out.println(ce.getDuree()*12);
	        System.out.println(leng);
	        
	        CompteEpargne[] ListEpargne =new CompteEpargne[leng];
			
	        CompteEpargne cmptepa=new CompteEpargne(null, null, null, null, null, false, null, null, null, leng, leng, leng) ;
			//System.out.println(cr.getAmount());
			
			
	        cmptepa.setVercement(ce.getVercement());
	        cmptepa.setVercementReg(ce.getVercementReg());
	        cmptepa.setInteretAcquis((float) (cmptepa.getVercement()*interest));
	        cmptepa.setVercement(cmptepa.getVercement()-cmptepa.getInteretAcquis());
	        ListEpargne[0]=cmptepa;
			
			//System.out.println(ListAmortissement[0]);
			for (int i=1;i<ce.getDuree()*12;i++) {
				CompteEpargne EpargnePrecedant=ListEpargne[i-1];
				CompteEpargne EpargneNEW=new CompteEpargne(null, null, null, null, null, false, null, null, null, i, i, i) ;
				EpargneNEW.setVercement(EpargnePrecedant.getVercement()-EpargnePrecedant.getVercement());
				EpargneNEW.setInteretAcquis((float) (EpargneNEW.getVercement()*interest));
				EpargneNEW.setVercementReg(ce.getVercementReg());
				EpargneNEW.setVercement(EpargneNEW.getVercementReg()-EpargneNEW.getInteretAcquis());
				ListEpargne[i]=EpargneNEW;
				
			}
			
			
			
			return ListEpargne;
		}
	
	@Override
	public CompteEpargne Simulateur(CompteEpargne ce) {
		System.out.println(ce.getVercement());
		CompteEpargne simulator =new CompteEpargne(null, null, null, null, null, false, null, null, null, 0, 0, 0);
		//mnt total
		simulator.setTotalVercement(0);
		//mnt interet
		simulator.setInteretAcquis(0);
		//mnt monthly
		simulator.setVercementReg(ce.getVercementReg());		
		CompteEpargne[] Epargnetab = TabAmortissement(ce);
		float s=0;
		float s1=0;
		for (int i=0; i < Epargnetab.length; i++) {
		s1=(float) (s1+Epargnetab[i].getInteretAcquis());
		}
		//mnt interet
		simulator.setInteretAcquis(s1);
		 //mnt total
		simulator.setTotalVercement(ce.getVercement()+s1);
		//mnt credit
		simulator.setVercement(ce.getVercement());
		return simulator;
	}

*/
	//Fonction qui calcule le taux de remuniration  
	public float GenerateTauxRémuniration (CompteEpargne e) {
		float TMM = ((float) 7.030);
		float TRE = (TMM-1)/100;
		return TRE;	
	}
	//Fonction qui calcul le montant de gain mensuelle 
	public float GenerateMontantMensuelle(CompteEpargne e) {
		//Fixée par la banque centrale
		float TMM = ((float) 7.030);
		float TRE = (TMM-1)/100;
		//formule d'interet composé
		//capitalFinal = capitalInitial * (1+TRE) puiss (nbr d'année)
		float totalM = (float) (e.getVercementReg()*(Math.pow((1+TRE),e.getDuree())));	
		return totalM;
	}
	//Fonction qui calcule le tableau de Rendement de l'epargne
	
	public Epargne[] TabRendement(Epargne ce) {   		 
		float interest= (float) ((7.030-1)/100); //taux de remuniration mentuel
	    System.out.println("taux de remuniration");
	    System.out.println(ce.getTauxRemuneration());
	    int leng=(int) (ce.getAnnee()*12); //Duree en mois 
	    System.out.println("periode");
	    System.out.println(ce.getAnnee()*12);
	    System.out.println(leng);
	        
	    Epargne[] ListEpargne =new Epargne[leng];
		Epargne epar=new Epargne() ;
		/*
		epar.setAnnee(1);
		epar.setTauxRemuneration(interest);
		epar.setVercementReg(ce.getVercementReg());
		epar.setVercementInitial(ce.getVercementInitial());
	    epar.setTotalVercement(ce.getVercementInitial()+((ce.getVercementReg())));
	    //System.out.println(ce.getVercement()+((ce.getVercementReg())*(ce.getDuree()*12)));
	    //System.out.println(ce.getVercementReg());
	    epar.setInteretAcquis((float) (epar.getTotalVercement()*interest));
	    //System.out.println((epar.getTotalVercement()*interest));
	    epar.setGain(epar.getTotalVercement()+epar.getInteretAcquis());
	    //System.out.println(epar.getTotalVercement()+epar.getInteretAcquis());
	    */
	    ListEpargne[1]=epar;
		for (int i=0;i< ce.getAnnee()*12;i++) {
				Epargne eparNEW=new Epargne() ;
				eparNEW.setAnnee(i);
				eparNEW.setTauxRemuneration((float) ((7.030-1)/100));
				//System.out.println(eparNEW.getTauxRemuneration());
				eparNEW.setVercementReg(ce.getVercementReg());
				eparNEW.setTotalVercement(ce.getVercementInitial()+ce.getVercementReg()*i);
				eparNEW.setVercementInitial(ce.getVercementInitial());
			//	float total = (float) (e.getVercement()*(Math.pow((1+TRE),e.getDuree())));
				float total = (float) ((eparNEW.getTotalVercement())*(Math.pow((1+interest),i)));
				eparNEW.setGain(total);
				eparNEW.setInteretAcquis((float) (eparNEW.getTotalVercement()*interest));
				//eparNEW.setGain(eparNEW.getTotalVercement()+eparNEW.getInteretAcquis());
				ListEpargne[i]=eparNEW;				
			}	
			return ListEpargne;
		}
		
	@Override
	public Epargne Simulateur(Epargne ce) {
		System.out.println(ce.getVercementInitial());
		Epargne simulator =new Epargne();
		//mnt total
		simulator.setTotalVercement(0);
		//mnt interet acquis 
		simulator.setInteretAcquis(0);
		//mnt monthly
		simulator.setVercementReg(ce.getVercementReg());
		simulator.setAnnee(ce.getAnnee());
		simulator.setTauxRemuneration((float) ((7.030-1)/100));
		simulator.setVercementInitial(ce.getVercementInitial());
		Epargne[] Epargnetab = TabRendement(ce);
		float s=0;
		float s1=0;
		for (int i=0; i < Epargnetab.length; i++) {
		    s1=(float) (s1*Epargnetab[i].getInteretAcquis());
		}
		//mnt interet
		simulator.setInteretAcquis(s1);
		 //mnt total
		simulator.setTotalVercement(ce.getTotalVercement()+s1);
		//mnt epargne
		//simulator.setVercement(ce.getVercement());
		return simulator;
	}


}
