package com.jmlessous.services;

import com.jmlessous.entities.ContratAssurance;
import com.jmlessous.entities.ContratAssurance;
import com.jmlessous.entities.TypeAssurance;
import com.jmlessous.entities.Utilisateur;

import java.util.List;

public interface IContratAssuranceService {
    List<ContratAssurance> retrieveAllContrats();

    ContratAssurance addContrat(ContratAssurance c,Long idOffre, Long idUser);

    void archiveContrat(Long id);

    void deleteContrat(Long id);

    ContratAssurance updateContrat(ContratAssurance c,Long idOffre, Long idUser);

    ContratAssurance retrieveContrat(Long id);

    List<ContratAssurance> getContratsByUtilisateur(Long u);

    List<ContratAssurance> getContratsByTypeAssurance(TypeAssurance ta);
}
