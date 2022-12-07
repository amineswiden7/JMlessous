package com.jmlessous.services;

import java.util.List;

import org.springframework.messaging.MessagingException;

import com.jmlessous.entities.Transaction;
import com.jmlessous.entities.TypeTransaction;



public interface ITransactionService {
	
	//public Transaction AjouterTransaction(Transaction t);
	int  addTransaction(Transaction s )throws MessagingException, javax.mail.MessagingException ;
	public String  approveTransaction(Transaction s)throws MessagingException, javax.mail.MessagingException ; 
	public String  approveTransactionAng(Transaction s, Long code )throws MessagingException, javax.mail.MessagingException;
	//internationale
	int  addTransactionInter(Transaction s )throws MessagingException, javax.mail.MessagingException ;
	public String  approveTransactionInter(Transaction s)throws MessagingException, javax.mail.MessagingException ; 
	public String  approveTransactionAngInter(Transaction s, Long code )throws MessagingException, javax.mail.MessagingException;
	
	List <Transaction> AllTransaction();
	List <Transaction> TransactionByRib(String rib);
	Transaction AffTransaction (Long id);
	public Transaction AnnulerTransaction(Transaction t);
	void SuppTransaction(Long id);
	void SuppAllTransaction();
	List<Transaction> TransactionParType (TypeTransaction typeTransaction);
	
	
}
