package com.jmlessous.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Garantie  implements Serializable {
    @Id
    @Column(name ="idGarantie ")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idGarantie;

    private Float valeur;
    private String type;

    @Temporal (TemporalType.DATE)
    private Date date;
    private String etat;
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, mappedBy="garantie")
    private Credit credit;
}
