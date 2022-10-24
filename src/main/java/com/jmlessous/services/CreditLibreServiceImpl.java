package com.jmlessous.services;


import com.jmlessous.entities.CreditLibre;
import com.jmlessous.entities.Garantie;
import com.jmlessous.repositories.CreditLibreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreditLibreServiceImpl implements ICreditLibreService {
    @Autowired
    CreditLibreRepository creditLibre;

    @Override
    public List<CreditLibre> retrieveAllCreditLibre() {
        return (List<CreditLibre>) creditLibre.findAll();
    }

    @Override
    public CreditLibre addCreditLibre(CreditLibre a) {
        creditLibre.save(a);
        return a;
    }

    @Override
    public void deleteCreditLibre(Long idCreditLibre) {
       creditLibre.deleteById(idCreditLibre);
    }

    @Override
    public CreditLibre updateCreditLibre(CreditLibre a, Long idCreditLibre) {
       CreditLibre creditLibre1=creditLibre.findById(idCreditLibre).orElse(null);
       creditLibre1.setCompteCredit(a.getCompteCredit());
        creditLibre1.setTauxInteret(a.getTauxInteret());
        creditLibre1.setMontantCredit(a.getMontantCredit());
        creditLibre1.setMensualite(a.getMensualite());
        creditLibre1.setSTATUS(a.getSTATUS());
        creditLibre1.setDateDemande(a.getDateDemande());
        creditLibre1.setDateFin(a.getDateFin());
        creditLibre1.setGarantie(a.getGarantie());
        creditLibre.save(creditLibre1);
        return creditLibre1;
    }
}
