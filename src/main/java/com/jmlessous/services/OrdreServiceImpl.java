package com.jmlessous.services;

import com.jmlessous.entities.OffreAssurance;
import com.jmlessous.entities.Ordre;
import com.jmlessous.entities.Portfeuille;
import com.jmlessous.repositories.OrdreRepository;
import com.jmlessous.repositories.PortfeuilleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdreServiceImpl implements IOrdreService{
    @Autowired
    OrdreRepository repository;
    @Autowired
    PortfeuilleRepository portfeuilleRepository;

    @Override
    public Ordre addOrdre(Ordre o) {
        return repository.save(o);
    }

    @Override
    public Ordre retrieveOrdre(Long id) {

        if (repository.findById(id).isPresent()) {
            return repository.findById(id).get();
        } else {
            System.out.println("Ordre n'existe pas !");
            return null;
        }
    }

    @Override
    public List<Ordre> retrieveOrdres(Long id) {
        Portfeuille portfeuille = portfeuilleRepository.findById(id).get();
        return (List<Ordre>) repository.retrieveOrdreByPorfeuille(portfeuille);
    }
}
