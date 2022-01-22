package com.bricolage.bricolageback.services;

import com.bricolage.bricolageback.dto.AvisDto;
import com.bricolage.bricolageback.entities.Annonce;
import com.bricolage.bricolageback.entities.Avis;
import com.bricolage.bricolageback.entities.Role;
import com.bricolage.bricolageback.entities.User;
import com.bricolage.bricolageback.exceptions.*;
import com.bricolage.bricolageback.repositories.AnnonceRepository;
import com.bricolage.bricolageback.repositories.AvisRepository;
import com.bricolage.bricolageback.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class AvisServiceImpl implements AvisService{

    @Autowired
    AvisRepository avisRepository;

    @Autowired
    AnnonceRepository annonceRepository;

    @Autowired
    UserRepository userRepository;

    /**
     * Si l'avis existe déjà, il est remplacé
     * détection automatique entre client et professionnel
     * */
    @Override
    public void ajoutAvis(User user, AvisDto addAvisDto) throws AnnonceNotFoundException, InvalidAvisException {

        Optional<Annonce> optionalAnnonce = annonceRepository.findById(addAvisDto.getId());

        if(optionalAnnonce.isEmpty())throw new AnnonceNotFoundException();

        if(addAvisDto.getRate() < 0 || addAvisDto.getRate() > 5)throw new InvalidAvisException();

        Optional<Avis> optionalAvis = avisRepository.findAvisByAnnonceAndUser(optionalAnnonce.get(),user);

        Avis avis;

        if(optionalAvis.isEmpty()){
            avis = new Avis(addAvisDto.getRate(),
                    addAvisDto.getMessage(),
                    optionalAnnonce.get(),
                    user);
        }else{
            avis = optionalAvis.get();
            avis.setAnnonce(optionalAnnonce.get());
            avis.setMessage(addAvisDto.getMessage());
            avis.setRate(addAvisDto.getRate());
            avis.setUser(user);
        }

        avisRepository.save(avis);

        Annonce annonce = optionalAnnonce.get();

        Collection<Role> r = user.getRoles();
        if(r == null || r.stream().anyMatch(role -> role.getRole().getRole().equals("CLIENT"))){
            annonce.setAvis_client(avis);
        }else{
            annonce.setAvis_pro(avis);
        }
        annonce.setTermine(true);//automatiquement terminé

        annonceRepository.save(annonce);
    }

    @Override
    public AvisDto getAvisClient(long annonceId) throws AnnonceNotFoundException, AvisNotFoundException {

        Optional<Annonce> annonceOptional = annonceRepository.findById(annonceId);
        if(annonceOptional.isEmpty())throw new AnnonceNotFoundException();

        Avis avis = avisRepository.findClientAvisByAnnonce(annonceOptional.get());//annonceRepository.getOne(annonceId).getAvis_client();

        if(avis == null)throw new AvisNotFoundException();

        return new AvisDto(avis.getId(),avis.getRate(),avis.getMessage());
    }

    @Override
    public AvisDto getAvisProfessionnel(long annonceId) throws AnnonceNotFoundException, AvisNotFoundException {

        Optional<Annonce> annonceOptional = annonceRepository.findById(annonceId);
        if(annonceOptional.isEmpty())throw new AnnonceNotFoundException();

        Avis avis = avisRepository.findProfessionnelAvisByAnnonce(annonceOptional.get());//annonceRepository.getOne(annonceId).getAvis_client();

        if(avis == null)throw new AvisNotFoundException();

        return new AvisDto(avis.getId(),avis.getRate(),avis.getMessage());
    }

    @Override
    public double getAverageRateOfUser(long id) throws UserNotFoundException, EmptyUserException {

        User u = userRepository.findByIdAndBloquedFalse(id);

        boolean isClient = u.getRoles().stream().anyMatch(ro -> ro.getRole().getRole().equals("CLIENT"));

        Optional<Double> res = isClient?
                avisRepository.findAverageOfClient(u):
                avisRepository.findAverageOfProfessionnel(u);

        if(res.isEmpty())return 0;

        return res.get();
    }
}
