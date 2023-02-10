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
		String p= c.getPays();
		//c.setIban(GenerateIBanC(r));
		c.setIban(iban(r,p));
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
	
	
	@Override
	public String GenerateIBanC(String RibC) {
		String codePays="TN";
		String IbanC = codePays + RibC;
		return IbanC;
	}
	
	public String iban(String RibC,String p) {
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

	@Override
	public List<CompteCourant> CompteCourantByRib(String rib) {	
		return (List<CompteCourant>)comptecourantRepository.getcptByRib(rib);
	}

	@Override
	public List<CompteCourant> CompteCourantByUser(Long idUser) {
		return (List<CompteCourant>)comptecourantRepository.getCompteByUser(idUser);
	}
	
	

	

}
