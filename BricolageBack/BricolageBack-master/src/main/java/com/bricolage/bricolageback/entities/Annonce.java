package com.bricolage.bricolageback.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Annonce {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String titre;
    private String description;
    private String adresse;
    private String details;
    private int budgetClient;
    private Date date;
    private boolean termine;
    private boolean deleted;

    @OneToOne
    private Image image;;


    @ManyToOne
    private User client;

    @ManyToOne
    private Domaine domaine;

    @JsonIgnore
    @ToString.Exclude
    @OneToOne
    private Avis avis_client;

    @JsonIgnore
    @ToString.Exclude
    @OneToOne
    private Avis avis_pro;

    public Annonce(String titre, String description, String adresse, String details, Date date,int budgetClient, User client, Domaine domaine,boolean termine) {
        this.titre = titre;
        this.description = description;
        this.adresse = adresse;
        this.details = details;
        this.date = date;
        this.budgetClient = budgetClient;
        this.client = client;
        this.domaine = domaine;
        this.termine = termine;
    }
}
