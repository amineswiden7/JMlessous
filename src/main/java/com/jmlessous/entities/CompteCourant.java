package com.jmlessous.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "COMPTE_COURANT")
public class CompteCourant  extends Compte implements Serializable {
   /* @Id
    @Column(name ="idCourant  ")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCourant  ;*/
    private Float montantDecouvert;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy="compteCredit")
    private Set<Credit> credits;


}
