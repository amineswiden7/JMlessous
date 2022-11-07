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
public class Transaction implements Serializable {
    @Id
    @Column(name ="idTransaction ")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTransaction;
    @Temporal (TemporalType.DATE)
    private Date dateTransaction;
    private Float montant;
    private String destinataire;
    private String emetteur;
    private String motif;
    private TypeTransaction type;
   // @ManyToMany()
   // private Compte comptesTransaction;

    @ManyToOne()
	Compte comptesTransaction ; 
	
}
