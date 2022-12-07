package com.jmlessous.controllers;



import java.io.IOException;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.MessagingException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jmlessous.entities.Compte;
import com.jmlessous.entities.CompteCourant;
import com.jmlessous.entities.Transaction;
import com.jmlessous.entities.TypeTransaction;
import com.jmlessous.services.ICompteService;
import com.jmlessous.services.ITransactionService;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/Transaction")
@RestController
public class TransactionController {
	@Autowired
	ITransactionService transactionService;	
	@Autowired
    ICompteService compteService;
	@Autowired
    public JavaMailSender emailSender;
	/*
	// http://localhost:8083/JMLessous/add-Transaction1
		@PostMapping("/add-Transaction1")
		@ResponseBody
		public Transaction addTransaction1(@RequestBody Transaction o ) 
		{
		return transactionService.AjouterTransaction(o) ;
		}

*/
	// http://localhost:8083/JMLessous/add-Transaction
		@PostMapping("/add-Transaction")
		@ResponseBody
		public int addTransaction(@RequestBody Transaction o ) throws MessagingException, javax.mail.MessagingException
		{
		int Transaction = transactionService.addTransaction(o) ; 
		return Transaction ;
		}

	// http://localhost:8083/JMLessous/app-TransactionAng/code
			@PostMapping("/app-TransactionAng/{code}")
			@ResponseBody
			public String approveTransactionAng(@RequestBody Transaction o ,@PathVariable("code") Long code ) throws MessagingException, javax.mail.MessagingException
			{
			String Transaction = transactionService.approveTransaction(o);
			return Transaction ;
			}
			
			// http://localhost:8083/JMLessous/add-TransactionInter
			@PostMapping("/add-TransactionInter")
			@ResponseBody
			public int addTransactionInter(@RequestBody Transaction o ) throws MessagingException, javax.mail.MessagingException
			{
			int Transaction = transactionService.addTransactionInter(o);
			return Transaction ;
			}

		// http://localhost:8083/JMLessous/app-TransactionInterAng/code
				@PostMapping("/app-TransactionInterAng/{code}")
				@ResponseBody
				public String approveTransactionInterAng(@RequestBody Transaction o ,@PathVariable("code") Long code ) throws MessagingException, javax.mail.MessagingException
				{
				String Transaction = transactionService.approveTransactionInter(o);
				return Transaction ;
				}
				
	// http://localhost:8083/JMLessous/retrieve-all-Transactions
			@GetMapping("/retrieve-all-Transactions")
			@ResponseBody
			public List<Transaction> getTransactions() {
			List<Transaction> listTransactions = transactionService.AllTransaction();
			return listTransactions;
			}	
	// http://localhost:8083/JMLessous/retrieve-Transaction/id
			@GetMapping("/retrieve-Transaction/{Transaction}")
			@ResponseBody
			public Transaction retrieveTransaction(@PathVariable("Transaction") Long TransactionId) {
			return transactionService.AffTransaction(TransactionId);
			}
	//localhost:8083/JMLessous/retrieve-Transaction-by-rib/rib
			@GetMapping("/retrieve-Transaction-by-rib/{rib}")
			@ResponseBody
			public List<Transaction> getTransactions (@PathVariable("rib") String rib)
			{
				return transactionService.TransactionByRib(rib); 
			}
	// http://localhost:8083/JMLessous/AnnulerTransaction
			@PostMapping("/AnnulerTransaction")
			@ResponseBody
			public Transaction AnnulerTransaction(@RequestBody Transaction o ) 
			{
			return transactionService.AnnulerTransaction(o);
			}
	// http://localhost:8083/JMLessous/TransactionParType/type
			@GetMapping("TransactionParType/{type}")
			public List<Transaction> TransactionParType(@PathVariable("type") TypeTransaction typeTransaction)
			{
			return transactionService.TransactionParType(typeTransaction); 
			}
			
	

			 //http://localhost:8083/JMLessous/Transaction/export/0192000410JGRDSHKLU9Z40
			@GetMapping("/export/{rib}")
			public void exportToPDF(HttpServletResponse response,@PathVariable("rib") String rib) throws IOException, com.itextpdf.text.DocumentException {
				response.setContentType("application/pdf");
				DateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd_HH:mm");
				String currentDateTime = dateFormater.format(new Date());
				String headerKey = "Content-Disposition"; 
				String headerValue = "Attachement;filename=trans_"+ currentDateTime + ".pdf";
				response.setHeader(headerKey, headerValue);
				
				List<Transaction> listTransaction = transactionService.TransactionByRib(rib);
				List<CompteCourant> cptcs = compteService.CompteCourantByRib(rib);
				ReleveBancPDF exporter = new ReleveBancPDF(listTransaction,cptcs,rib);
				ReleveBancPDF exporter2 = new ReleveBancPDF(listTransaction,cptcs,rib);				
				exporter.export(response);
			
			} 		
}
