package com.bricolage.bricolageback.services;

import com.bricolage.bricolageback.dto.AnnonceDTO;
import com.bricolage.bricolageback.entities.Annonce;

import java.io.IOException;
import java.util.List;

public interface AnnonceService {
    Annonce ajoutDemandeService(AnnonceDTO annonce) throws IOException;
    Annonce getAnnonce(long idAnnonce);
    List<Annonce> getAnnonceByClient(long idClient);

    List<Annonce> getAnnonceByBricoleur(long idBricoleur);

    List<Annonce> getAnnonces();
    Annonce modifierAnnonce(AnnonceDTO annonce) throws IOException;
    void deleteAnnonce(long idAnnonce);
}
