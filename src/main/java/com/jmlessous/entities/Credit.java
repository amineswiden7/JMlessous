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
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Credit implements Serializable {
    @Id
    @Column(name ="idCredit ")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCredit ;
    private Float montantCredit;
    private Float montantRestant;
    private Float montantPaye;
    @Temporal (TemporalType.DATE)
    private Date dateDemande;
    @Temporal (TemporalType.DATE)
    private Date dateFin;
    private Float tauxInteret;
    private Float mensualite;
    private Status STATUS;
    @ManyToOne
    private Compte compteCredit;
    @ManyToOne
    private PackCredit packCredit;
    @OneToMany(cascade = CascadeType.ALL, mappedBy="credit")
    private Set<Garantie> garanties;






}
