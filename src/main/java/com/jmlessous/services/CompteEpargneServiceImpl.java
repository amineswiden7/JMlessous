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
		String p= e.getPays();
		//c.setIban(GenerateIBanC(r));
		e.setIban(ibanE(r,p));
		//e.setIban(GenerateIBanE(r));
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
	public String ibanE(String RibC,String p) {
		String IbanC="";
		switch (p) {
		  case "France":
			   IbanC = "FR" + RibC;
		    break;
		  case "Afghanistan":
			   IbanC = "AF" + RibC;
		    break;
		  case "Afrique_Centrale":
			   IbanC = "AC" + RibC;
		    break;
		  case "Afrique_du_Sud":
			   IbanC = "AS" + RibC;
		    break;
		  case "Albanie":
			   IbanC = "AL" + RibC;
		    break;
		  case "Algerie":
			   IbanC = "DZ" + RibC;
		    break;
		  case "Allemagne":
			   IbanC = "DE" + RibC;
		    break;
		  case "Andorre":
			   IbanC = "AD" + RibC;
		    break;
		  case "Angola":
			   IbanC = "AO" + RibC;
		    break;
		  case "Arabie_Saoudite":
			   IbanC = "SA" + RibC;
		    break;
		  case "Argentine":
			   IbanC = "AR" + RibC;
		    break;
		  case "Armenie":
			   IbanC = "AM" + RibC;
		    break;
		  case "Australie":
			   IbanC = "AU" + RibC;
		    break;
		  case "Autriche":
			   IbanC = "AT" + RibC;
		    break;
		  case "Azerbaidjan":
			   IbanC = "AZ" + RibC;
		    break;
		  case "Bahamas":
			   IbanC = "BS" + RibC;
		    break;
		  case "Bangladesh":
			   IbanC = "BD" + RibC;
		    break;
		  case "Barbade":
			   IbanC = "BA" + RibC;
		    break;
		  case "Bahrein":
			   IbanC = "BH" + RibC;
		    break;
		  case "Belgique":
			   IbanC = "BE" + RibC;
		    break;
		  case "Belize":
			   IbanC = "BZ" + RibC;
		    break;
		  case "Benin":
			   IbanC = "BJ" + RibC;
		    break;
		  case "Bermudes":
			   IbanC = "BR" + RibC;
		    break;
		  case "Bielorussie":
			   IbanC = "BY" + RibC;
		    break;
		  case "Bolivie":
			   IbanC = "BO" + RibC;
		    break;
		  case "Botswana":
			   IbanC = "BW" + RibC;
		    break;
		  case "Bhoutan":
			   IbanC = "BT" + RibC;
		    break;
		  case "Boznie_Herzegovine":
			   IbanC = "BV" + RibC;
		    break;
		  case "Bresil":
			   IbanC = "BR" + RibC;
		    break;
		  case "Brunei":
			   IbanC = "BI" + RibC;
		    break;
		  case "Bulgarie":
			   IbanC = "BG" + RibC;
		    break;
		  case "Burkina_Faso":
			   IbanC = "BF" + RibC;
		    break;
		  case "Burundi":
			   IbanC = "BI" + RibC;
		    break;
		  case "Caiman":
			   IbanC = "CA" + RibC;
		    break;
		  case "Cambodge":
			   IbanC = "KH" + RibC;
		    break;
		  case "Cameroun":
			   IbanC = "CM" + RibC;
		    break;
		  case "Canada":
			   IbanC = "CA" + RibC;
		    break;
		  case "Canaries":
			   IbanC = "CR" + RibC;
		    break;
		  case "Cap_Vert":
			   IbanC = "CV" + RibC;
		    break;
		  case "Chili":
			   IbanC = "CL" + RibC;
		    break;
		  case "Chine":
			   IbanC = "CH" + RibC;
		    break;
		  case "Chypre":
			   IbanC = "CY" + RibC;
		    break;
		  case "Colombie":
			   IbanC = "CO" + RibC;
		    break;
		  case "Comores":
			   IbanC = "KM" + RibC;
		    break;
		  case "Congo":
			   IbanC = "CG" + RibC;
		    break;
		  case "Congo_democratique":
			   IbanC = "CD" + RibC;
		    break;
		  case "Cook":
			   IbanC = "CK" + RibC;
		    break;
		  case "Coree_du_Nord":
			   IbanC = "CN" + RibC;
		    break;
		  case "Coree_du_Sud":
			   IbanC = "CS" + RibC;
		    break;
		  case "Costa_Rica":
			   IbanC = "CR" + RibC;
		    break;
		  case "Côte_d_Ivoire":
			   IbanC = "CI" + RibC;
		    break;
		  case "Croatie":
			   IbanC = "CR" + RibC;
		    break;
		  case "Cuba":
			   IbanC = "CU" + RibC;
		    break;
		  case "Danemark":
			   IbanC = "DK" + RibC;
		    break;
		  case "Djibouti":
			   IbanC = "DJ" + RibC;
		    break;
		  case "Dominique":
			   IbanC = "DO" + RibC;
		    break;
		  case "Egypte":
			   IbanC = "EG" + RibC;
		    break;
		  case "Emirats_Arabes_Unis":
			   IbanC = "UA" + RibC;
		    break;
		  case "Equateur":
			   IbanC = "AQ" + RibC;
		    break;
		  case "Erythree":
			   IbanC = "ER" + RibC;
		    break;
		  case "Espagne":
			   IbanC = "ES" + RibC;
		    break;
		  case "Estonie":
			   IbanC = "ET" + RibC;
		    break;
		  case "Etats_Unis":
			   IbanC = "US" + RibC;
		    break;
		  case "Ethiopie":
			   IbanC = "ET" + RibC;
		    break;
		  case "Falkland":
			   IbanC = "FA" + RibC;
		    break;
		  case "Feroe":
			   IbanC = "FE" + RibC;
		    break;
		  case "Fidji":
			   IbanC = "FI" + RibC;
		    break;
		  case "Finlande":
			   IbanC = "FN" + RibC;
		    break;
		  case "Gabon":
			   IbanC = "GB" + RibC;
		    break;
		  case "Gambie":
			   IbanC = "GM" + RibC;
		    break;
		  case "Georgie":
			   IbanC = "GE" + RibC;
		    break;
		  case "Ghana":
			   IbanC = "GH" + RibC;
		    break;
		  case "Gibraltar":
			   IbanC = "GI" + RibC;
		    break;
		  case "Grece":
			   IbanC = "GR" + RibC;
		    break;
		  case "Grenade":
			   IbanC = "GD" + RibC;
		    break;
		  case "Groenland":
			   IbanC = "GL" + RibC;
		    break;
		  case "Guadeloupe":
			   IbanC = "GP" + RibC;
		    break;
		  case "Guam":
			   IbanC = "GU" + RibC;
		    break;
		  case "Guatemala":
			   IbanC = "GT" + RibC;
		    break;
		  case "Guernesey":
			   IbanC = "GG" + RibC;
		    break;
		  case "Guinee":
			   IbanC = "GN" + RibC;
		    break;
		  case "Guinee_Bissau":
			   IbanC = "GW" + RibC;
		    break;
		  case "Guinee_Equatoriale":
			   IbanC = "GE" + RibC;
		    break;
		  case "Guyana":
			   IbanC = "GY" + RibC;
		    break;
		  case "Guyane_Francaise":
			   IbanC = "GF" + RibC;
		    break;
		  case "Haiti":
			   IbanC = "HT" + RibC;
		    break;
		  case "Hawaii":
			   IbanC = "HW" + RibC;
		    break;
		  case "Honduras":
			   IbanC = "HN" + RibC;
		    break;
		  case "Hong_Kong":
			   IbanC = "HG" + RibC;
		    break;
		  case "Hongrie":
			   IbanC = "HO" + RibC;
		    break;
		  case "Inde":
			   IbanC = "IN" + RibC;
		    break;
		  case "Indonesie":
			   IbanC = "ID" + RibC;
		    break;
		  case "Iran":
			   IbanC = "IR" + RibC;
		    break;
		  case "Iraq":
			   IbanC = "IQ" + RibC;
		    break;
		  case "Irlande":
			   IbanC = "IE" + RibC;
		    break;
		  case "Islande":
			   IbanC = "IS" + RibC;
		    break;
		  case "italie":
			   IbanC = "IT" + RibC;
		    break;
		  case "Jamaique":
			   IbanC = "JM" + RibC;
		    break;
		  case "Jan_Mayen":
			   IbanC = "JN" + RibC;
		    break;
		  case "Japon":
			   IbanC = "JA" + RibC;
		    break;
		  case "Jersey":
			   IbanC = "JE" + RibC;
		    break;
		  case "Jordanie":
			   IbanC = "JO" + RibC;
		    break;
		  case "Kazakhstan":
			   IbanC = "KA" + RibC;
		    break;
		  case "Kenya":
			   IbanC = "KE" + RibC;
		    break;
		  case "Kirghizistan":
			   IbanC = "KI" + RibC;
		    break;
		  case "Kiribati":
			   IbanC = "KR" + RibC;
		    break;
		  case "Koweit":
			   IbanC = "KW" + RibC;
		    break;
		  case "Laos":
			   IbanC = "LA" + RibC;
		    break;
		  case "Lesotho":
			   IbanC = "LE" + RibC;
		    break;
		  case "Lettonie":
			   IbanC = "LT" + RibC;
		    break;
		  case "Liban":
			   IbanC = "LB" + RibC;
		    break;
		  case "Liberia":
			   IbanC = "LR" + RibC;
		    break;
		  case "Liechtenstein":
			   IbanC = "LI" + RibC;
		    break;
		  case "Lituanie":
			   IbanC = "LT" + RibC;
		    break;
		  case "Luxembourg":
			   IbanC = "LU" + RibC;
		    break;
		  case "Lybie":
			   IbanC = "LY" + RibC;
		    break;
		  case "Macao":
			   IbanC = "MA" + RibC;
		    break;
		  case "Macedoine":
			   IbanC = "MC" + RibC;
		    break;
		  case "Madagascar":
			   IbanC = "MG" + RibC;
		    break;
		  case "Madère":
			   IbanC = "MA" + RibC;
		    break;
		  case "Malaisie":
			   IbanC = "ML" + RibC;
		    break;
		  case "Malawi":
			   IbanC = "MW" + RibC;
		    break;
		  case "Maldives":
			   IbanC = "MA" + RibC;
		    break;
		  case "Mali":
			   IbanC = "ML" + RibC;
		    break;
		  case "Malte":
			   IbanC = "MA" + RibC;
		    break;
		  case "Man":
			   IbanC = "MN" + RibC;
		    break;
		  case "Mariannes":
			   IbanC = "MI" + RibC;
		    break;
		  case "Maroc":
			   IbanC = "MA" + RibC;
		    break;
		  case "Marshall":
			   IbanC = "MH" + RibC;
		    break;
		  case "Martinique":
			   IbanC = "MT" + RibC;
		    break;
		  case "Maurice":
			   IbanC = "MU" + RibC;
		    break;
		  case "Mauritanie":
			   IbanC = "MR" + RibC;
		    break;
		  case "Mayotte":
			   IbanC = "MO" + RibC;
		    break;
		  case "Mexique":
			   IbanC = "MX" + RibC;
		    break;
		  case "Micronesie":
			   IbanC = "MI" + RibC;
		    break;
		  case "Midway":
			   IbanC = "MI" + RibC;
		    break;
		  case "Moldavie":
			   IbanC = "MO" + RibC;
		    break;
		  case "Monaco":
			   IbanC = "MC" + RibC;
		    break;
		  case "Mongolie":
			   IbanC = "MN" + RibC;
		    break;
		  case "Montserrat":
			   IbanC = "MS" + RibC;
		    break;
		  case "Mozambique":
			   IbanC = "MZ" + RibC;
		    break;
		  case "Namibie":
			   IbanC = "NA" + RibC;
		    break;
		  case "Nauru":
			   IbanC = "NR" + RibC;
		    break;
		  case "Nepal":
			   IbanC = "NP" + RibC;
		    break;
		  case "Nicaragua":
			   IbanC = "NC" + RibC;
		    break;
		  case "Niger":
			   IbanC = "NG" + RibC;
		    break;
		  case "Nigeria":
			   IbanC = "NI" + RibC;
		    break;
		  case "Niue":
			   IbanC = "NU" + RibC;
		    break;
		  case "Norfolk":
			   IbanC = "NO" + RibC;
		    break;
		  case "Norvege":
			   IbanC = "NR" + RibC;
		    break;
		  case "Nouvelle_Caledonie":
			   IbanC = "NC" + RibC;
		    break;
		  case "Nouvelle_Zelande":
			   IbanC = "NZ" + RibC;
		    break;
		  case "Oman":
			   IbanC = "OM" + RibC;
		    break;
		  case "Ouganda":
			   IbanC = "OU" + RibC;
		    break;
		  case "Ouzbekistan":
			   IbanC = "OZ" + RibC;
		    break;
		  case "Pakistan":
			   IbanC = "PK" + RibC;
		    break;
		  case "Palau":
			   IbanC = "PW" + RibC;
		    break;
		  case "Palestine":
			   IbanC = "PS" + RibC;
		    break;
		  case "Panama":
			   IbanC = "PA" + RibC;
		    break;
		  case "Papouasie_Nouvelle_Guinee":
			   IbanC = "PG" + RibC;
		    break;
		  case "Paraguay":
			   IbanC = "PY" + RibC;
		    break;
		  case "Pays_Bas":
			   IbanC = "PB" + RibC;
		    break;
		  case "Perou":
			   IbanC = "PE" + RibC;
		    break;
		  case "Philippines":
			   IbanC = "PH" + RibC;
		    break;
		  case "Pologne":
			   IbanC = "PL" + RibC;
		    break;
		  case "Polynesie":
			   IbanC = "PY" + RibC;
		    break;
		  case "Porto_Rico":
			   IbanC = "PR" + RibC;
		    break;
		  case "Portugal":
			   IbanC = "PT" + RibC;
		    break;
		  case "Qatar":
			   IbanC = "QA" + RibC;
		    break;
		  case "Republique_Dominicaine":
			   IbanC = "RD" + RibC;
		    break;
		  case "Republique_Tcheque":
			   IbanC = "RT" + RibC;
		    break;
		  case "Reunion":
			   IbanC = "RU" + RibC;
		    break;
		  case "Roumanie":
			   IbanC = "RO" + RibC;
		    break;
		  case "Royaume_Uni":
			   IbanC = "UK" + RibC;
		    break;
		  case "Russie":
			   IbanC = "RU" + RibC;
		    break;
		  case "Rwanda":
			   IbanC = "RW" + RibC;
		    break;
		  case "Sahara_Occidental":
			   IbanC = "SO" + RibC;
		    break;
		  case "Sainte_Lucie":
			   IbanC = "SL" + RibC;
		    break;
		  case "Saint_Marin":
			   IbanC = "SM" + RibC;
		    break;
		  case "Salomon":
			   IbanC = "SA" + RibC;
		    break;
		  case "Salvador":
			   IbanC = "SL" + RibC;
		    break;
		  case "Samoa_Occidentales":
			   IbanC = "SO" + RibC;
		    break;
		  case "Samoa_Americaine":
			   IbanC = "SA" + RibC;
		    break;
		  case "Sao_Tome_et_Principe":
			   IbanC = "SP" + RibC;
		    break;
		  case "Senegal":
			   IbanC = "SN" + RibC;
		    break;
		  case "Seychelles":
			   IbanC = "SC" + RibC;
		    break;
		  case "Sierra_Leone":
			   IbanC = "SL" + RibC;
		    break;
		  case "Singapour":
			   IbanC = "SG" + RibC;
		    break;
		  case "Slovaquie":
			   IbanC = "SK" + RibC;
		    break;
		  case "Slovenie":
			   IbanC = "SI" + RibC;
		    break;
		  case "Somalie":
			   IbanC = "SO" + RibC;
		    break;
		  case "Soudan":
			   IbanC = "SD" + RibC;
		    break;
		  case "Sri_Lanka":
			   IbanC = "SL" + RibC;
		    break;
		  case "Suede":
			   IbanC = "SU" + RibC;
		    break;
		  case "Suisse":
			   IbanC = "CH" + RibC;
		    break;
		  case "Surinam":
			   IbanC = "SR" + RibC;
		    break;
		  case "Swaziland":
			   IbanC = "SW" + RibC;
		    break;
		  case "Syrie":
			   IbanC = "SY" + RibC;
		    break;
		  case "Tadjikistan":
			   IbanC = "TA" + RibC;
		    break;
		  case "Taiwan":
			   IbanC = "TW" + RibC;
		    break;
		  case "Tonga":
			   IbanC = "TO" + RibC;
		    break;
		  case "Tanzanie":
			   IbanC = "TZ" + RibC;
		    break;
		  case "Tchad":
			   IbanC = "TC" + RibC;
		    break;
		  case "Thailande":
			   IbanC = "TH" + RibC;
		    break;
		  case "Tibet":
			   IbanC = "TI" + RibC;
		    break;
		  case "Timor_Oriental":
			   IbanC = "TO" + RibC;
		    break;
		  case "Togo":
			   IbanC = "TG" + RibC;
		    break;
		  case "Trinite_et_Tobago":
			   IbanC = "TT" + RibC;
		    break;
		  case "Tristan_da_cunha":
			   IbanC = "TC" + RibC;
		    break;
		  case "Tunisie":
			   IbanC = "TU" + RibC;
		    break;
		  case "Turkmenistan":
			   IbanC = "TK" + RibC;
		    break;
		  case "Turquie":
			   IbanC = "TR" + RibC;
		    break;
		  case "Ukraine":
			   IbanC = "UA" + RibC;
		    break;
		  case "Uruguay":
			   IbanC = "UR" + RibC;
		    break;
		  case "Vanuatu":
			   IbanC = "VA" + RibC;
		    break;
		  case "Vatican":
			   IbanC = "VT" + RibC;
		    break;
		  case "Venezuela":
			   IbanC = "VE" + RibC;
		    break;
		  case "Vierges_Americaines":
			   IbanC = "VA" + RibC;
		    break;
		  case "Vierges_Britanniques":
			   IbanC = "VB" + RibC;
		    break;
		  case "Vietnam":
			   IbanC = "VA" + RibC;
		    break;
		  case "Wake":
			   IbanC = "WA" + RibC;
		    break;
		  case "Wallis_et_Futuma":
			   IbanC = "WF" + RibC;
		    break;
		  case "Yemen":
			   IbanC = "YE" + RibC;
		    break;
		  case "Yougoslavie":
			   IbanC = "YO" + RibC;
		    break;
		  case "Zambie":
			   IbanC = "ZA" + RibC;
		    break;
		  case "Zimbabwe":
			   IbanC = "ZI" + RibC;
		    break;
		}
		return IbanC;
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

	@Override
	public List<CompteEpargne> CompteEpargneByUser(Long idUser) {
	
		return (List<CompteEpargne>)compteepargneRepository.getCompteEByUser(idUser);
	}


}
