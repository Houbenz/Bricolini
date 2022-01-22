package com.bricolage.bricolageback.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String nom;
    private String prenom;
    @Column(unique = true)
    private String username;
    private String adresse;
    private String telephone;
    private String password;
    private boolean premium;
    private boolean bloqued;

    @ManyToMany(fetch=FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();


    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "user")
    private List<NotificationUserAssoc> notifications;

    @ManyToOne(fetch = FetchType.EAGER)
    private Domaine domaine;

    @OneToMany
    private List<Devis> devis;


    @OneToMany
    private List<Annonce> annonces;

    public User(String nom, String prenom, String username, String adresse, String telephone, String password, boolean premium) {
        this.nom = nom;
        this.prenom = prenom;
        this.username = username;
        this.adresse = adresse;
        this.telephone = telephone;
        this.password = password;
        this.premium = premium;
    }

    public void addRole(Role role) {
        if (this.roles == null) {
            this.roles = new ArrayList<Role>();
            this.roles.add(role);
        }
        this.roles.add(role);
    }


}
