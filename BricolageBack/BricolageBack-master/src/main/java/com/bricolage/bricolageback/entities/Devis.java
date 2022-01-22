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
public class Devis {

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

}
