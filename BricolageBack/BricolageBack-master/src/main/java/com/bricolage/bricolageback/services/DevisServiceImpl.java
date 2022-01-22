package com.bricolage.bricolageback.services;

import com.bricolage.bricolageback.dto.DevisDTO;
import com.bricolage.bricolageback.dto.PropositionDTO;
import com.bricolage.bricolageback.dto.UserDTO;
import com.bricolage.bricolageback.entities.Annonce;
import com.bricolage.bricolageback.entities.Devis;
import com.bricolage.bricolageback.entities.Proposition;
import com.bricolage.bricolageback.repositories.AnnonceRepository;
import com.bricolage.bricolageback.repositories.DevisRepository;
import com.bricolage.bricolageback.repositories.PropositionRepository;
import com.bricolage.bricolageback.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DevisServiceImpl implements DevisService{
    @Autowired
    private DevisRepository devisRep;
    @Autowired
    private UserRepository userRep;
    @Autowired
    private AnnonceRepository annRep;
    @Autowired
    private PropositionRepository proRep;
    @Autowired
    private AvisService avisService;
    @Override
    public DevisDTO ajouterDevis(long idProposition) {

        Proposition d = proRep.findById(idProposition).get();

        Optional<Devis> testDevis = devisRep.findByAnnonce(d.getAnnonce());

        Devis devis = null;

        if(testDevis.isPresent()){
            devis = testDevis.get();
        }else{
            devis = new Devis();
            devis.setPrice(d.getPrice());
            devis.setAnnonce(d.getAnnonce());
            devis.setDate(d.getDate());
            devis.setProfessionnel(d.getProfessionnel());
            devis.setMessage(d.getMessage());
        }

        devisRep.save(devis);
        DevisDTO dev = new DevisDTO();
        dev.setId(devis.getId());
        dev.setDate(devis.getDate());
        dev.setIdAnnonce(devis.getAnnonce().getId());
        dev.setIdProfessionnel(devis.getProfessionnel().getId());
        dev.setMessage(devis.getMessage());
        dev.setPrice(devis.getPrice());
        return dev;
    }
    @Override
    public Optional<PropositionDTO> getDevisByAnnonce(long idAnnonce){
        Annonce annonce =  annRep.findById(idAnnonce).get();
        return devisRep.findByAnnonce(annonce).
                map(p -> new PropositionDTO(p.getId(),p.getMessage(),p.getPrice(),p.getDate(),p.getAnnonce().getId(),p.getProfessionnel().getId(),true, UserDTO.castUser(p.getProfessionnel(),avisService),p.getAnnonce()));
    }
}
