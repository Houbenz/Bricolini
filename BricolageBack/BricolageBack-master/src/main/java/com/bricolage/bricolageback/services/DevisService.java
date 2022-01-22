package com.bricolage.bricolageback.services;

import com.bricolage.bricolageback.dto.DevisDTO;
import com.bricolage.bricolageback.dto.PropositionDTO;

import java.util.Optional;

public interface DevisService {
    public DevisDTO ajouterDevis(long idProposition); //ok
    public Optional<PropositionDTO> getDevisByAnnonce(long idAnnonce);






    /*public Proposition modifierProposition(Proposition p); //ok
    public Proposition trouverProposition(Proposition p);
    public void annulerProposition(Long idPropo);//ok

    public List<Proposition> listPropositionByAnnonce(Annonce a);
    public List<Proposition> listPropositionByProfessionnel(long idUser);*/
}

