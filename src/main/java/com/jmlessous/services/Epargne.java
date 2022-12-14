package com.jmlessous.services;

import com.jmlessous.entities.NatureEpargne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Epargne {
	private float annee;
	private float tauxRemuneration;
    private float vercementInitial ;
    private float vercementReg;
    
    private float totalVercement;
    private float interetAcquis;
    private float gain;
    public Epargne(float vercementInitial, float vercementReg ,float annee) {
		
    	this.vercementInitial=vercementInitial;
		this.vercementReg=vercementReg;
		this.annee=annee;
	}
	public Epargne() {
		// TODO Auto-generated constructor stub
	}
}
