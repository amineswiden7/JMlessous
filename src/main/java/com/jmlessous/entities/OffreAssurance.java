package com.jmlessous.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OffreAssurance  implements Serializable {
    @Id
    @Column(name ="idOffreAssurance ")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOffreAssurance ;
    private Float minInteret;
    private Float maxInteret;
    private Long minPeriode;
    private Long maxPeriode;
    @Enumerated(EnumType.STRING)
    private TypePeriode typePeriode;
    @Enumerated(EnumType.STRING)
    private TypeAssurance type;
    @Enumerated(EnumType.STRING)
    private CategorieAssurance categorie;
    @Column(columnDefinition="LONGTEXT")
    private String description;
    private String shortDescription;
    private String assurance;
    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String image;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy="offreAssurance")
    private Set<ContratAssurance> contratAssurances;

}
