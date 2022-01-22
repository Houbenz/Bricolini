package com.bricolage.bricolageback.services;

import com.bricolage.bricolageback.dto.PropositionDTO;
import com.bricolage.bricolageback.entities.Annonce;
import com.bricolage.bricolageback.entities.Proposition;
import com.bricolage.bricolageback.repositories.AnnonceRepository;
import com.bricolage.bricolageback.repositories.PropositionRepository;
import com.bricolage.bricolageback.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropositionDevisServiceImp implements PropositionDevisService {

    @Autowired
    private PropositionRepository propoRep;
    @Autowired
    private UserRepository userRep;
    @Autowired
    private AnnonceRepository annRep;



    @Override
    public Proposition ajouterProposition(PropositionDTO p) {
        Proposition prop = new Proposition();
        if(p.equals(null)) {
            return null;
        }
        prop.setPrice(p.getPrice());
        prop.setAnnonce(annRep.findById(p.getIdAnnonce()).get());
        prop.setDate(p.getDate());
        prop.setProfessionnel(userRep.findById(p.getIdProfessionnel()).get());
        prop.setMessage(p.getMessage());

        propoRep.save(prop);

        return prop;
    }

    @Override
    public Proposition modifierProposition(Proposition p) {
        if(p.equals(null)) return null;
        if(propoRep.existsById(p.getId())){
            //this.ajouterProposition(p);
        }else {
            p = null;
        }
        return p;
    }

    @Override
    public Proposition trouverProposition(Proposition p) {
        if(p.equals(null)) return null;
        return propoRep.getOne(p.getId());
    }

    @Override
    public void annulerProposition(Long idPropo) {
        propoRep.delete(propoRep.findById(idPropo).get());
    }

    @Override
    public List<Proposition> listPropositionByAnnonce(Annonce a) {

        return propoRep.findByAnnonce(a);
    }

    @Override
    public List<Proposition> listPropositionByProfessionnel(long idUser) {
        return propoRep.findByProfessionnel(userRep.findById(idUser).get());
    }
}
