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
public class ProduitFinancier  implements Serializable {
    @Id
    @Column(name ="idProduit ")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProduit ;
    private String isin;
    private String symbol;
    private Float montantAchat;

    private String titre;
    private Long quantite;
    @Enumerated(EnumType.STRING)
    private TypeProduit typeProduit;
    @Enumerated(EnumType.STRING)
    private StatusProduit statusProduit;
    @JsonIgnore
    @ManyToOne
    private Portfeuille portfeuille;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy="produitFinancier")
    private Set<Ordre> ordres;
}
