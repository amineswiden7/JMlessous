package com.jmlessous.entities;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name = "CREDIT_IMMOBILIER")

public class CreditImmobilier  extends Credit implements Serializable {


    private Float tauxNominal;
    private String type;
    private String Objet;
    private String Produit;
    private String destination;
    private  float LeMontantDeLaTransaction;
    private float apportPersonnel;
    private float montantmensuelpretpayer;
    private float MontantReventResidencePrincipal;
    private float chargeMensuel;
    @Enumerated(EnumType.STRING)
    private Localisation localisation;

}
