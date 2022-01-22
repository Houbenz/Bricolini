package com.bricolage.bricolageback.entities;

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
public class Proposition {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String message;
    private String price;
    private Date date;

    @OneToOne
    private Annonce annonce;

    @ManyToOne
    @JoinColumn(name = "pro_id")
    private User professionnel;

    public Proposition(String message, String price, Date date, Annonce annonce, User professionnel) {
        this.message = message;
        this.price = price;
        this.date = date;
        this.annonce = annonce;
        this.professionnel = professionnel;
    }
}
