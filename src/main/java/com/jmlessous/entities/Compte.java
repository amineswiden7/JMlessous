package com.jmlessous.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@Table(name = "COMPTE")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public  class Compte  {
    @Id
    @Column(name ="numCompte")
    @GeneratedValue(strategy = GenerationType.TABLE)
    private String numCompte;
    private Float solde;
    private String rib;
    private String iban;
    @Temporal (TemporalType.DATE)
    private Date dateOuverture;
    private boolean state;
    @JsonIgnore
    @ManyToOne
    private Utilisateur utilisateurC;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy="compteCredit")
    private Set<Credit> credits;
    @JsonIgnore
   // @ManyToMany(mappedBy="comptesTransaction", cascade = CascadeType.ALL)
   // private Set<Transaction> transactions;

    @OneToMany(mappedBy = "comptesTransaction")
 	private Set<Transaction> Transactions ; 

}
