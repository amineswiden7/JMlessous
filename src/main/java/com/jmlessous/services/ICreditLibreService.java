package com.jmlessous.services;



import com.jmlessous.entities.Credit;
import com.jmlessous.entities.CreditLibre;

import java.util.List;

public interface ICreditLibreService
{
    List<CreditLibre> retrieveAllCreditLibre();

    CreditLibre addCreditLibre(CreditLibre credit , Long idUser, Long idGarantie);

    public Amortissement[] TabAmortissement(CreditLibre cr);
    public Amortissement Simulateur(CreditLibre credit);
    public void deleteCreditLibre(Long idCreditLibre);
    CreditLibre updateCreditLibre(CreditLibre a,Long idCreditLibre );
}
