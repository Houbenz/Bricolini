package com.bricolage.bricolageback.dto;

import com.bricolage.bricolageback.entities.Annonce;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PropositionDTO {
    private Long id;
    private String message;
    private String price;
    private Date date;
    private Long idAnnonce;
    private Long idProfessionnel;
    private boolean devis;
    private UserDTO bricoleur;
    private Annonce annonce;
}
