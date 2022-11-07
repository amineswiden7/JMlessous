package com.jmlessous.services;

public class Amortissement {

    double montantR;
    double interest;
    double amortissement;
    double mensualite;
    public double getMontantR() {
        return montantR;
    }
    public void setMontantR(double montantR) {
        this.montantR = montantR;
    }
    public double getInterest() {
        return interest;
    }
    public void setInterest(double interest) {
        this.interest = interest;
    }
    public double getAmortissement() {
        return amortissement;
    }
    public void setAmortissement(double amortissement) {
        this.amortissement = amortissement;
    }
    public double getMensualite() {
        return mensualite;
    }
    public void setMensualite(double mensualite) {
        this.mensualite = mensualite;
    }
    public Amortissement(double montantR, double interest, double amortissement, double mensualite) {
        super();
        this.montantR = montantR;
        this.interest = interest;
        this.amortissement = amortissement;
        this.mensualite = mensualite;
    }
    public Amortissement() {
        super();
        // TODO Auto-generated constructor stub
    }
    @Override
    public String toString() {
        return "Amortissement [montantR=" + montantR + ", interest=" + interest + ", amortissement=" + amortissement
                + ", mensualité=" + mensualite + "]";
    }
}

