package com.bricolage.bricolageback.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnnonceDTO {
    private long id;
    public long idDomaine;
    public long idUser;
    public String titre;
    public String description;
    public Date date;
    public String adresse;
    public int budgetClient;
    public String details;
    public MultipartFile image;
}
