package com.jmlessous.services;

import com.jmlessous.entities.ContratAssurance;
import com.jmlessous.entities.ContratAssurance;
import com.jmlessous.entities.TypeAssurance;
import com.jmlessous.entities.Utilisateur;

import java.text.ParseException;
import java.util.List;

public interface IContratAssuranceService {
    List<ContratAssurance> retrieveAllContrats();

    ContratAssurance addContrat(ContratAssurance c,Long idOffre, Long idUser) throws ParseException;

    void archiveContrat(Long id);

    void deleteContrat(Long id);

    ContratAssurance updateContrat(ContratAssurance c,Long idOffre, Long idUser);

    ContratAssurance retrieveContrat(Long id);

    ContratAssurance declineContrat(Long id);

    ContratAssurance acceptContrat(Long id);

    List<ContratAssurance> getContratsByUtilisateur(Long u);

    List<ContratAssurance> getContratsByOffre(Long id);

    List<ContratAssurance> getContratsByTypeAssurance(TypeAssurance ta);
}
