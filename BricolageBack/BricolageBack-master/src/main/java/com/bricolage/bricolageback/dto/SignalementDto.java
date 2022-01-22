package com.bricolage.bricolageback.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
@ToString
public class SignalementDto {

    private String message;

    private String raison;

    private long idAnnonce;

    private long idBricoleur;
}
