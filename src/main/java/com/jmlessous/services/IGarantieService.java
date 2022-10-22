package com.jmlessous.services;

import com.jmlessous.entities.Garantie;

import java.util.List;

public interface IGarantieService {
    List<Garantie> retrieveAllAbsences();

    Garantie addGarantie(Garantie a );


    public void deleteGarantie(Long idGarantie);
    Garantie updateGarantie(Garantie a,Long idGarantie );
}
