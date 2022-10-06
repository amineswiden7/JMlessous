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
public class OffreAssurance  implements Serializable {
    @Id
    @Column(name ="idOffreAssurance ")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOffreAssurance ;
    private Float interet;
    private Long periode;
    private TypeAssurance type;
    private String description;
    private String assurance;
    @OneToMany(cascade = CascadeType.ALL, mappedBy="offreAssurance")
    private Set<ContratAssurance> contratAssurances;

}
