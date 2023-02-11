package com.jmlessous.repositories;


import com.jmlessous.entities.Garantie;
import com.jmlessous.entities.Status;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface GarantieRepository extends CrudRepository<Garantie,Long> {
     @Query("SELECT g FROM Garantie g WHERE g.credit.idCredit= :idCredit")
     Garantie getGarantieByCreditIdCredit(@Param("idCredit") Long idCredit);
}
