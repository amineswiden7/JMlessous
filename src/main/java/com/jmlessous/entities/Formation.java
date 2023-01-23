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
public class Formation implements Serializable {
    @Id
    @Column(name ="idFormation ")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFormation ;
    @Temporal (TemporalType.DATE)
    private Date datedebut;
    private String localisation;
    private String intitule;
    private String description;
    private String categorie;
    private String duree;
   
    @ManyToMany()
    private Set<Utilisateur> utilisateursFormation;
}
