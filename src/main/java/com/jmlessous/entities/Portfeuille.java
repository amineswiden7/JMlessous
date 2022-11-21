package com.jmlessous.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Portfeuille implements Serializable {
    @Id
    @Column(name ="idPortfeuille")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPortfeuille;
    @Temporal (TemporalType.DATE)
    private Date dateOuverture;
    private Float  solde;
    @JsonIgnore
    @OneToOne()
    private Utilisateur utilisateur;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy="portfeuille")
    private Set<ProduitFinancier> produitFinanciers;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy="portfeuille")
    private Set<Ordre> ordres;

}
