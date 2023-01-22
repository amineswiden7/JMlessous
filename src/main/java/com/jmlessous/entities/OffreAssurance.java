package com.jmlessous.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.JsonObject;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import springfox.documentation.spring.web.json.Json;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Map;
import java.util.Set;
//class ScoreBonus{
//    Float b1;
//    Float b2;
//    Float b3;
//    Float b4;
//    Float b5;
//    Float b6;
//    Float b7;
//    Float b8;
//    Float b9;
//    Float b10;
//    Float b11;
//    Float b12;
//}
//class ScorePuissance{
//    Float cv2;
//    Float cv3;
//    Float cv4;
//    Float cv5;
//    Float cv6;
//    Float cv7;
//    Float cv8;
//    Float cv9;
//    Float cv10;
//    Float cv11;
//    Float cv12;
//    Float cv13;
//    Float cv14;
//    Float cv15p;
//}
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@TypeDef(name = "json", typeClass = JsonStringType.class)
public class OffreAssurance  implements Serializable {
    @Id
    @Column(name ="idOffreAssurance ")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOffreAssurance ;
    private int minAge;
    private int maxAge;
    private Float interet;
    private Float minPeriode;
    private Float maxPeriode;
    @Enumerated(EnumType.STRING)
    private TypePeriode typePeriode;
    @Enumerated(EnumType.STRING)
    private TypeAssurance type;
    @Enumerated(EnumType.STRING)
    private CategorieAssurance categorie;
    @Enumerated(EnumType.STRING)
    private StatutAssurance statut;
    @Column(columnDefinition="LONGTEXT")
    private String description;
    private String shortDescription;
    private String assurance;
    private Float primePure;
    private Float interFlex;
    private Float scoreHomme;
    private Float scoreFemme;
    private Float scoreCircMin;
    private Float scoreCircMax;
    private Float scoreValMin;
    private Float scoreValMax;
    private Float commission;
    private Float gainTotal;
    private int nbreContrats;
    @Type( type = "json" )
    private Map<String,Float> scoreBonus;
    @Type( type = "json" )
    private Map<String,Float> scorePuissance;
    private String lienForm;
    private String lienLogo;
    private Float seuilCouv;
    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String image;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy="offreAssurance")
    private Set<ContratAssurance> contratAssurances;

}
