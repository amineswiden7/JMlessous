package com.jmlessous.services;




import com.jmlessous.entities.Credit;

import com.jmlessous.entities.CreditLibre;
import com.jmlessous.entities.Garantie;
import com.jmlessous.entities.Status;

import java.util.List;

public interface ICreditLibreService
{
    List<CreditLibre> retrieveAllCreditLibre();
    List<CreditLibre> listCreditLibreByClient(Long idUser);
    CreditLibre getCreditLibreByID(Long idCredit);
    List<CreditLibre> listCreditLibreByStatus(Status status);
    CreditLibre accepterCreditLibre(Long idCredit , Status status);
    CreditLibre addCreditLibre(CreditLibre credit , Long idUser,Garantie garantie);

    public Amortissement[] TabAmortissement(CreditLibre cr, Garantie garantie, float salaire);
    public Amortissement Simulateur(CreditLibre credit, Garantie garantie, float salaire);
    public CreditLibre CalculatePlusGrandCredit(Long idUser);
    public void deleteCreditLibre(Long idCreditLibre);
    CreditLibre updateCreditLibre(CreditLibre a,Long idCreditLibre );


}
