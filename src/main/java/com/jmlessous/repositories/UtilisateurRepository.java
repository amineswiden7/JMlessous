package com.jmlessous.repositories;

import com.jmlessous.entities.Utilisateur;
import org.springframework.data.repository.CrudRepository;

public interface UtilisateurRepository extends CrudRepository<Utilisateur,Long> {

    Utilisateur findByLogin(String login);

}
