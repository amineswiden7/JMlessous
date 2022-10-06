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
public class Message implements Serializable {
    @Id
    @Column(name ="idMessage")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMessage;
    @Temporal (TemporalType.DATE)
    private Date dateEnvo;
    private String contenu;
    @ManyToMany()
    private Set<Utilisateur> utilisateurs;



}
