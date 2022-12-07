package com.jmlessous.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jmlessous.entities.Transaction;
import com.jmlessous.entities.TypeTransaction;
@Repository
public interface TransactionRepository extends CrudRepository<Transaction,Long>{

	
	@Query("SELECT t FROM Transaction t WHERE t.comptesTransaction.rib= :rib")
	List<Transaction> getTransactionByRibAccount(@Param("rib") String rib);
	
	@Query("select t from Transaction t where t.type=?1")
    List<Transaction> findTransactionByTransactionType(TypeTransaction typeTransaction);
	
	/*
	@Query("SELECT t FROM Transaction t WHERE t.CompteCourant.Iban= :iban")
	List<Transaction> getTransaction2EmiseByRibAccount(@Param("iban") Long Iban);
	*/
//	@Query("SELECT t FROM Transaction t WHERE t.comptesTransaction.idUser= :idUser")
	//List<Transaction> getTransactionByidUser(@Param("idUser") Long idUser);
}
