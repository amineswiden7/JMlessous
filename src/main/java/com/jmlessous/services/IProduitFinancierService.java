package com.jmlessous.services;

import com.jmlessous.entities.OffreAssurance;
import com.jmlessous.entities.Ordre;
import com.jmlessous.entities.ProduitFinancier;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface IProduitFinancierService {
    List<ProduitFinancier> retrieveAllProduits();

    //ProduitFinancier addProduit(ProduitFinancier p,Long id);

    ProduitFinancier addProduit(ProduitFinancier p,float prix,Long qte,Long id);

    void deleteProduit(Long id);


    ProduitFinancier retrieveProduit(Long id);

    void vendreProduit(ProduitFinancier p,float prix,Long qte,Long id);

}
