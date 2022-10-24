package com.jmlessous.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.jmlessous.entities.Compte;
import com.jmlessous.entities.CompteCourant;
import com.jmlessous.entities.CompteEpargne;
import com.jmlessous.entities.Utilisateur;
import com.jmlessous.services.ICompteEService;
import com.jmlessous.services.ICompteService;
import com.jmlessous.services.IUtilisateurService;

@RestController
public class CompteController {
	@Autowired
    IUtilisateurService utilisateurService;
	@Autowired
    ICompteService compteService;
	@Autowired
    ICompteEService compteEService;
	
	public String RibC = ""; 


	//http://localhost:8083/JMLessous/Listaccounts
		@GetMapping("/Listaccounts")
		@ResponseBody
		public List<CompteCourant> getAllAcounts() {
			List <CompteCourant> list=compteService.retrieveAllAccountsC();
			return list ; 
		} 
		
	//http://localhost:8083/JMLessous/AddAccount
	@PostMapping("/AddAccount/{idUser}")
	@ResponseBody
	public Compte AddAccount (@RequestBody CompteCourant c ,@PathVariable ("idUser")  Long idUser)
	{
		return compteService.addAccountC(c,idUser) ;
	}
	
	//http://localhost:8083/JMLessous/AddAccountE
	@PostMapping("/AddAccountE/{idUser}")
	@ResponseBody
	public Compte AddAccountE (@RequestBody CompteEpargne e ,@PathVariable ("idUser")  Long idUser)
	{
		return compteEService.addAccountE(e, idUser);
	}
	
	 // http://localhost:8083/JMLessous/modify-Account
		@PutMapping("/modify-Account")
		@ResponseBody
		public CompteCourant modifyAccount(@RequestBody CompteCourant account) {
		return compteService.updateAccountC(account);
		}
	
	//http://localhost:8083/JMLessous/DeleteAccount/1
		@DeleteMapping("/DeleteAccount/{idacc}")
		@ResponseBody
		public void deleteAcc (@PathVariable("idacc") Long idacc ) {
			compteService.deleteAccount(idacc);
		}
		
		//http://localhost:8083/JMLessous/archive-cpt/1
		@PutMapping("/archive-cpt/{c-id}")
		@ResponseBody
		public CompteCourant ArchiveCompteC(@PathVariable("c-id") Long idc) {
		return compteService.ArchiveCompteC(idc);
		}
		
		//CE
		
		//http://localhost:8083/JMLessous/ListaccountsE
				@GetMapping("/ListaccountsE")
				@ResponseBody
				public List<CompteEpargne> getAllAcountsE() {
					List <CompteEpargne> list=compteEService.retrieveAllAccountsE();
					return list ; 
				} 
				
		
			
			 // http://localhost:8083/JMLessous/modify-AccountE
				@PutMapping("/modify-AccountE")
				@ResponseBody
				public CompteEpargne modifyAccountE(@RequestBody CompteEpargne accountE) {
				return compteEService.updateAccountE(accountE);
				}
			
			//http://localhost:8083/JMLessous/DeleteAccountE/1
				@DeleteMapping("/DeleteAccountE/{idace}")
				@ResponseBody
				public void deleteAccE (@PathVariable("idace") Long idace ) {
					compteEService.deleteAccount(idace);
				}
				
			//http://localhost:8083/JMLessous/archive-cptE/1
				@PutMapping("/archive-cptE/{c-id}")
				@ResponseBody
				public CompteEpargne ArchiveCompteE(@PathVariable("e-id") Long ide) {
				return compteEService.ArchiveCompteE(ide);
				}
				
				//http://localhost:8083/JMLessous/rib
				@GetMapping("/rib")
						@ResponseBody
						public String generateRib ()
						{
					this.RibC = compteService.GenerateRibC();
							return this.RibC ;
						}
				
				//http://localhost:8083/JMLessous/iban	
				@GetMapping("/iban")
						@ResponseBody
						public String generateIBan ()
						{
							return compteService.GenerateIBanC(this.RibC);
						}
				
				
				
				/*
				
				//http://localhost:8083/JMLessous/taux	
				@PostMapping("/taux")
				@ResponseBody
				public void taux () {
					compteEService.GetInterestAmount();
				}	*/
}
