package com.jmlessous.services;

import com.jmlessous.entities.Portfeuille;
import com.jmlessous.entities.ProduitFinancier;
import com.jmlessous.repositories.ProduitFinancierRepository;

import java.util.List;
import java.util.Set;


public interface IPortfeuilleService {

    Portfeuille addPortfeuille(float solde,Long id);

    void deletePortfeuille(Long id);

   // ProduitFinancier updateProduit(ProduitFinancier p);

    Portfeuille retrievePortfeuille(Long id);

    Portfeuille retrievePortfeuillebyUser(Long id);

    Set<ProduitFinancier> rerieveProduits(Long id);

    float calculPerformance(Long id);
    //void addProduitToPortfeuille(Long id,ProduitFinancier p);


}
