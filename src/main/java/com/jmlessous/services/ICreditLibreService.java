package com.jmlessous.services;



import com.jmlessous.entities.CreditLibre;

import java.util.List;

public interface ICreditLibreService
{
    List<CreditLibre> retrieveAllCreditLibre();

    CreditLibre addCreditLibre(CreditLibre a );


    public void deleteCreditLibre(Long idCreditLibre);
    CreditLibre updateCreditLibre(CreditLibre a,Long idCreditLibre );
}
