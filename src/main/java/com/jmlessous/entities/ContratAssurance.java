package com.jmlessous.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContratAssurance implements Serializable {
    @Id
    @Column(name ="idContrat")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idContrat;
    @Temporal (TemporalType.DATE)
    private Date dateDebut;
    @Temporal (TemporalType.DATE)
    private Date dateFin;
    private LocalDate dateAjout;
    private Long cinAssure;
    private Long telAssure;
    private String emailAssure;
    private String nomPrenomAssure;
    @Enumerated(EnumType.STRING)
    private TypeAssurance type;
    @Enumerated(EnumType.STRING)
    private CategorieAssurance categorie;
    @Enumerated(EnumType.STRING)
    private StatutContratAssurance statut;
    private Boolean regulated;
    private Float annuité;
    private Float prime ;
    private String typePrime;
    private int nbreAnnuites;
    private int nbreRemb;
    private Float montantRente;
    private Float interet;
    private Float commission ;
    @JsonIgnore
    @ManyToOne
    private Utilisateur utilisateurCA;
    @JsonIgnore
    @ManyToOne
    private OffreAssurance offreAssurance;


}
