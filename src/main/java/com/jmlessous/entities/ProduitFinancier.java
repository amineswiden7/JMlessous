package com.jmlessous.entities;


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
public class ProduitFinancier  implements Serializable {
    @Id
    @Column(name ="idProduit ")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProduit ;
    @Temporal (TemporalType.DATE)
    private Date dateAchat;
    @Temporal (TemporalType.DATE)
    private Date dateVente;
    private Float montantAchat;
    private Float montantVente;
    private String titre;
    private Long quantité;
    private TypeProduit TYPEPRODUIT;
    @ManyToOne
    private Portfeuille portfeuille;
}
