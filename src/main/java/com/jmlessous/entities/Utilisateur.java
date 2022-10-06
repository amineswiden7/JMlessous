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
public class Utilisateur implements Serializable {

    @Id
    @Column(name ="idUser")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;
    private Long  cin;

    private String nom;
    private String prénom;
    @Temporal (TemporalType.DATE)
    private Date dateNaissance;
    private Long  téléphone;
    private Float  salaire;
    private String profession;
    private String email;
    private String login;
    private String motDePasse;
    private String adresse;
    private Role role;
    private Float rib ;
    @ManyToMany()
    private Set<Message> messages;
    @ManyToMany(mappedBy="utilisateurs", cascade = CascadeType.ALL)
    private Set<Information> informations;
    @OneToOne(mappedBy="utilisateur")
    private Portfeuille portfeuille;
    @OneToMany(cascade = CascadeType.ALL, mappedBy="utilisateurC")
    private Set<Compte> comptes;
    @ManyToMany(mappedBy="utilisateursFormation", cascade = CascadeType.ALL)
    private Set<Formation> formations;
    @ManyToMany(mappedBy="utilisateursAbsence", cascade = CascadeType.ALL)
    private Set<Absence> absences;
    @OneToMany(cascade = CascadeType.ALL, mappedBy="utilisateurCA")
    private Set<ContratAssurance> contratAssurances;




}
