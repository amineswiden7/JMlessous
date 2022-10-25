package com.jmlessous.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "COMPTE_EPARGNE")
public class CompteEpargne  extends Compte implements Serializable  {
    /*@Id
    @Column(name ="idEpargne ")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEpargne ;*/
	//private int index_remuneration;
    private float tauxRemuneration;
    private float vercement ;
    private float duree;
    private float totalVercement;
    private float interetAcquis;
    private NatureEpargne natureepargne;


}
