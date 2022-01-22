package com.bricolage.bricolageback.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Avis {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private int rate;
    private String message;

    @OneToOne
    private Annonce annonce;

    @OneToOne
    private User user;

    public Avis(int rate, String message, Annonce annonce, User user) {
        this.rate = rate;
        this.message = message;
        this.annonce = annonce;
        this.user = user;
    }
}
