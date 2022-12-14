package com.jmlessous.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Utilisateur implements Serializable, UserDetails {

    @Id
    @Column(name ="idUser")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;
    private Long  cin;

    private String nom;
    private String prénom;
    @Temporal (TemporalType.DATE)
    private Date dateNaissance;
    private Long  téléphone;
    private Float  salaire;
    @Enumerated(EnumType.STRING)
    private Profession profession;
    private String email;
    private String login;
    private String motDePasse;
    private String adresse;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String rib ;
    private String iban; 
    private Boolean creditAuthorization;
    @ManyToMany()
    private Set<Message> messages;
    @ManyToMany(mappedBy="utilisateurs", cascade = CascadeType.ALL)
    private Set<Information> informations;
    @OneToOne(mappedBy="utilisateur")
    private Portfeuille portfeuille;
    @OneToMany(cascade = CascadeType.ALL, mappedBy="utilisateurC")
    private Set<Compte> comptes;
    @ManyToMany(mappedBy="utilisateursFormation", cascade = CascadeType.ALL)
    private Set<Formation> formations;
    @OneToMany(mappedBy="utilisateursAbsence", cascade = CascadeType.ALL)
    private Set<Absence> absences;
    @OneToMany(cascade = CascadeType.ALL, mappedBy="utilisateurCA")
    private Set<ContratAssurance> contratAssurances;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.motDePasse;
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
