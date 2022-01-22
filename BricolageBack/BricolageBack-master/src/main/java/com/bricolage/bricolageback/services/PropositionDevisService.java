package com.bricolage.bricolageback.services;

import com.bricolage.bricolageback.dto.PropositionDTO;
import com.bricolage.bricolageback.entities.Annonce;
import com.bricolage.bricolageback.entities.Proposition;

import java.util.List;

public interface PropositionDevisService {
    public Proposition ajouterProposition(PropositionDTO p); //ok
    public Proposition modifierProposition(Proposition p); //ok
    public Proposition trouverProposition(Proposition p);
    public void annulerProposition(Long idPropo);//ok

    public List<Proposition> listPropositionByAnnonce(Annonce a);
    public List<Proposition> listPropositionByProfessionnel(long idUser);
}
