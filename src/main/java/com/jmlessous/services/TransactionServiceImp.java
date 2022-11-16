package com.jmlessous.services;

import java.security.SecureRandom;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import org.apache.catalina.connector.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

import com.jmlessous.entities.Compte;
import com.jmlessous.entities.CompteCourant;
import com.jmlessous.entities.Transaction;
import com.jmlessous.entities.TypeTransaction;
import com.jmlessous.repositories.CompteCourantRepository;
import com.jmlessous.repositories.CompteRepository;
import com.jmlessous.repositories.TransactionRepository;
@Service
public class TransactionServiceImp implements ITransactionService{
	@Autowired
	TransactionRepository transrepo ; 
	
	@Autowired 
	CompteRepository accrepo ; 
	
	@Autowired
	CompteCourantRepository compcourant;
	
	@Autowired
    public JavaMailSender emailSender;
	
	int code=0;
	
	
	/*
	@Override
	public Transaction AjouterTransaction(Transaction t) {
		
		Compte acc_emet = accrepo.findByRib(t.getEmetteur());
		Compte acc_dest = accrepo.findByRib(t.getDestinataire());
		
		
		if (t.getMontant() < acc_emet.getSolde())
		 {
			acc_emet.setSolde(acc_emet.getSolde()- t.getMontant());
			t.setComptesTransaction(acc_emet);
			t.setDateTransaction(new Date());
			acc_dest.setSolde(acc_dest.getSolde()+t.getMontant());
			transrepo.save(t);
		 }
		else
		{
			code=0;
		}
		return t ; 
	}
	
*/
	 public int sendAttachmentEmail(String ReciverEmail) throws MessagingException, javax.mail.MessagingException {
	   	  MimeMessage message = emailSender.createMimeMessage();
	         boolean multipart = true;         
	         MimeMessageHelper helper = new MimeMessageHelper(message, multipart, "utf-8");
	         int max = 999999 ;
	         int min = 9999 ; 
	         SecureRandom secureRandom = new SecureRandom();
	         int randomWithSecureRandomWithinARange = secureRandom.nextInt(max - min) + min;	         
	         String htmlMsg = "<h3>Code de valdation de la transaction</h3>"
	       		  + randomWithSecureRandomWithinARange;	         
	         message.setContent(htmlMsg, "text/html");  
	         helper.setTo(ReciverEmail);
	         helper.setSubject("Transaction Jmlessous"); 
	         this.emailSender.send(message);     
	         return randomWithSecureRandomWithinARange ;
	   }
	 
	@Override
	public int addTransaction(Transaction s) throws MessagingException, javax.mail.MessagingException {
		Compte acc_emet = accrepo.findByRib(s.getEmetteur());
		Compte acc_dest = accrepo.findByRib(s.getDestinataire());
		int code_tr = sendAttachmentEmail(acc_emet.getUtilisateurC().getEmail()) ;  
		this.code=code_tr;
		if  (s.getMontant() < acc_emet.getSolde())  
		 {
			acc_emet.setSolde(acc_emet.getSolde()- s.getMontant());
			s.setComptesTransaction(acc_emet);
			s.setDateTransaction(new Date());
			acc_dest.setSolde(acc_dest.getSolde()+s.getMontant());
		}
		else
		{
		code_tr=0;
	}
		
		return code_tr ;  
	}
	

		//Confirmation de la transaction 
	@Override
	public String approveTransaction(Transaction s) throws MessagingException, javax.mail.MessagingException {
		if(addTransaction(s)==code)
		{
		transrepo.save(s);
		return "Transaction approuvée " ; 
		}
		else 
		{
		return "Transaction non approuvée" ; 		 
	    }
	}
	
	@Override
	public String approveTransactionAng(Transaction s, Long code) throws MessagingException, javax.mail.MessagingException {
		if(addTransaction(s)==code) 
		{
		transrepo.save(s);
		return "transaction approuveé" ; 
		}
		else 
		{
		return "Transaction non approuvée" ; 		 
	    }
	}
	

	@Override
	public Transaction AnnulerTransaction(Transaction t) {
		Compte acc_emet = accrepo.findByRib(t.getEmetteur());
		Compte acc_dest = accrepo.findByRib(t.getDestinataire());
			acc_emet.setSolde(acc_emet.getSolde() + t.getMontant());
			t.setComptesTransaction(acc_emet);
			t.setDateTransaction(new Date());
			acc_dest.setSolde(acc_dest.getSolde() - t.getMontant());
			return transrepo.save(t);
	}
	
	@Override
	public List<Transaction> AllTransaction() {
		return (List<Transaction>) transrepo.findAll();	
	}

	@Override
	public Transaction AffTransaction(Long id) {
		 Transaction  tr = transrepo.findById(id).orElse(null);
		 return tr; 
	}
	
	@Override
	public List<Transaction> TransactionByRib(String rib) {
		return (List<Transaction>) transrepo.getTransactionByRibAccount(rib);
	}

	@Override
	public void SuppTransaction(Long id) {
		transrepo.deleteById(id);
	}

	@Override
	public void SuppAllTransaction() {
		transrepo.deleteAll();
	}

	@Override
	public List<Transaction> TransactionParType (TypeTransaction typeTransaction) {
		  return transrepo.findTransactionByTransactionType(typeTransaction);
	}

	

	
	
}
