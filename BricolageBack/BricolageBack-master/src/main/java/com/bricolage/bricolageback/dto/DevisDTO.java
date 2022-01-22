package com.bricolage.bricolageback.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DevisDTO {
    private Long id;
    private String message;
    private String price;
    private Date date;
    private Long idAnnonce;
    private Long idProfessionnel;
}
