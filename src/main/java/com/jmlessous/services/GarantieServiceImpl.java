package com.jmlessous.services;

import com.jmlessous.entities.Absence;
import com.jmlessous.entities.Garantie;
import com.jmlessous.repositories.AbsenceRepository;
import com.jmlessous.repositories.GarantieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class GarantieServiceImpl implements IGarantieService{
    @Autowired
    GarantieRepository garantieRepo;

    @Override
    public List<Garantie> retrieveAllAbsences() {
        return (List<Garantie>) garantieRepo.findAll();
    }

    @Override
    public Garantie addGarantie(Garantie a) {
        garantieRepo.save(a);
       return a;
    }

    @Override
    public void deleteGarantie(Long idGarantie) {
        garantieRepo.deleteById(idGarantie);
    }

    @Override
    public Garantie updateGarantie(Garantie a,Long idGarantie) {
        Garantie gar= garantieRepo.findById(idGarantie).orElse(null);
        gar.setDate(a.getDate());
        gar.setEtat(a.getEtat());
        gar.setType(a.getType());
        gar.setValeur(a.getValeur());
        garantieRepo.save(gar);
        return a;
    }
}
