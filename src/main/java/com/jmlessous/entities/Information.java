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
public class Information implements Serializable {
    @Id
    @Column(name ="idInformation")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idInformation;
    @Temporal (TemporalType.DATE)
    private Date datePublication;
    private String titre;
    private String image;
    private String déscription;
    @ManyToMany()
    private Set<Utilisateur> utilisateurs;
}
