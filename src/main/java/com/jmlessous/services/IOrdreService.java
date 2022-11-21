package com.jmlessous.services;

import com.jmlessous.entities.Ordre;
import com.jmlessous.entities.Portfeuille;
import com.jmlessous.entities.ProduitFinancier;

import java.util.List;
import java.util.Set;

public interface IOrdreService {
    Ordre addOrdre(Ordre o);


    Ordre retrieveOrdre(Long id);

    List<Ordre> retrieveOrdres(Long id);

}
