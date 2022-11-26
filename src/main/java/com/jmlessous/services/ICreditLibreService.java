package com.jmlessous.services;




import com.jmlessous.entities.Credit;

import com.jmlessous.entities.CreditLibre;
import com.jmlessous.entities.Status;

import java.util.List;

public interface ICreditLibreService
{
    List<CreditLibre> retrieveAllCreditLibre();
    List<CreditLibre> listCreditLibreByClient(Long idUser);
    CreditLibre getCreditLibreByID(Long idCredit);
    List<CreditLibre> listCreditLibreByStatus(Status status);
    CreditLibre accepterCreditLibre(Long idCredit , Status status);
    CreditLibre addCreditLibre(CreditLibre credit , Long idUser, Long idGarantie);

    public Amortissement[] TabAmortissement(CreditLibre cr);
    public Amortissement Simulateur(CreditLibre credit);
    public float CalculatePlusGrandCredit(Long idUser);
    public void deleteCreditLibre(Long idCreditLibre);
    CreditLibre updateCreditLibre(CreditLibre a,Long idCreditLibre );


}
