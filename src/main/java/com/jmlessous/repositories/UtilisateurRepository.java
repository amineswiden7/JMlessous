package com.jmlessous.repositories;

import com.jmlessous.entities.Absence;
import com.jmlessous.entities.Utilisateur;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UtilisateurRepository extends CrudRepository<Utilisateur,Long> {

    Utilisateur findByLogin(String login);




}
