package com.bricolage.bricolageback.services;

import com.bricolage.bricolageback.dto.SignalementDto;
import com.bricolage.bricolageback.entities.Annonce;
import com.bricolage.bricolageback.entities.Devis;
import com.bricolage.bricolageback.entities.Signalement;
import com.bricolage.bricolageback.entities.User;
import com.bricolage.bricolageback.exceptions.AnnonceNotFoundException;
import com.bricolage.bricolageback.exceptions.UserNotFoundException;
import com.bricolage.bricolageback.repositories.AnnonceRepository;
import com.bricolage.bricolageback.repositories.DevisRepository;
import com.bricolage.bricolageback.repositories.SignalementRepository;
import com.bricolage.bricolageback.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SignalementServiveImpl implements SignalementService{

    @Autowired
    AnnonceRepository annonceRepository;

    @Autowired
    SignalementRepository signalementRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    DevisRepository devisRepository;

    public void ajouterSignalement(SignalementDto signalementDto, User user){
        Signalement signalement = new Signalement();

        Annonce annonce = annonceRepository.getOne(signalementDto.getIdAnnonce());

        Optional<Signalement> potential = signalementRepository.findByAnnonceAndUser(annonce,user);
        if(potential.isPresent())signalement = potential.get();

        signalement.setAnnonce(annonce);
        signalement.setUser(user);
        signalement.setMessage(signalementDto.getMessage());
        signalement.setRaison(signalementDto.getRaison());

        signalementRepository.save(signalement);
    }

    public SignalementDto getSignalement(long annonceId,User user){

        Annonce annonce = annonceRepository.findById(annonceId).get();

        Signalement signalement = signalementRepository.findByAnnonceAndUser(annonce,user).get();

        return new SignalementDto(signalement.getMessage(), signalement.getRaison(), annonceId, user.getId());
    }

    public List<SignalementDto> getSignalements(){
        return signalementRepository.findAll().stream().map(s -> new SignalementDto(s.getMessage(),s.getRaison(),s.getAnnonce().getId(),s.getUser().getId())).collect(Collectors.toList());
    }

    public SignalementDto getSignalementFromClient(long annonceId) throws AnnonceNotFoundException {
        SignalementDto signalementDto = new SignalementDto();

        Annonce annonce;
        try {
            annonce = annonceRepository.findById(annonceId).get();
        }catch (NoSuchElementException e){
            throw new AnnonceNotFoundException();
        }
        User user = annonce.getClient();

        Signalement signalement = signalementRepository.findByAnnonceAndUser(annonce,user).get();

        return new SignalementDto(signalement.getMessage(), signalement.getRaison(), annonceId, user.getId());

    }

    public SignalementDto getSignalementFromBricoleur(long annonceId) throws UserNotFoundException, AnnonceNotFoundException {
        SignalementDto signalementDto = new SignalementDto();

        Annonce annonce;

        try {
            annonce = annonceRepository.findById(annonceId).get();
        }catch (NoSuchElementException e){
            throw new AnnonceNotFoundException();
        }

        Devis devis = null;
        try{
            devisRepository.findByAnnonce(annonce).get();
        }catch (NoSuchElementException e){
            throw new UserNotFoundException();
        }


        User user = devis.getProfessionnel();

        Signalement signalement = signalementRepository.findByAnnonceAndUser(annonce,user).get();

        return new SignalementDto(signalement.getMessage(), signalement.getRaison(), annonceId, user.getId());

    }

}
