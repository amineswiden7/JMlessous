package com.jmlessous.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@Table(name = "CREDIT")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class Credit implements Serializable {
    @Id
    @Column(name ="idCredit ")
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long idCredit ;
    private Float montantCredit;
    private Float montantRestant;
    private Float duree;
    private Float montantPaye;
    @Temporal (TemporalType.DATE)
    private Date dateDemande;
    @Temporal (TemporalType.DATE)
    private Date dateFin;
    private Float mensualite;
    private Status STATUS;
    private Float risque;
    private Float taux;
    private String Motif ;
    private Boolean FinC;
    @ManyToOne
    private CompteCourant compteCredit;
    /*@ManyToOne
    private PackCredit packCredit;*/
    @OneToOne(cascade = CascadeType.ALL, mappedBy="credit")
    private Garantie garantie;






}
