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
public class Absence  implements Serializable {
    @Id
    @Column(name ="idAbsence ")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAbsence ;

    @Temporal (TemporalType.DATE)
    private Date dateDebut;
    @Temporal (TemporalType.DATE)
    private Date dateFin;
    private String motif ;
    private TypeAbsence typeAbsence;
    @ManyToMany()
    private Set<Utilisateur> utilisateursAbsence;
}
