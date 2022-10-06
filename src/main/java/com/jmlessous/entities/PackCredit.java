package com.jmlessous.entities;


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
public class PackCredit implements Serializable {
    @Id
    @Column(name ="idPackCredit ")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPackCredit ;
    private String type;
    private String titre;
    private Float tauxInteret;
    @OneToMany(cascade = CascadeType.ALL, mappedBy="packCredit")
    private Set<Credit> credits;
}
