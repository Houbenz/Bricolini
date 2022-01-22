package com.bricolage.bricolageback.services;

import com.bricolage.bricolageback.dto.AnnonceDTO;
import com.bricolage.bricolageback.entities.Annonce;
import com.bricolage.bricolageback.entities.Domaine;
import com.bricolage.bricolageback.entities.Image;
import com.bricolage.bricolageback.entities.User;
import com.bricolage.bricolageback.repositories.AnnonceRepository;
import com.bricolage.bricolageback.repositories.DomaineRepository;
import com.bricolage.bricolageback.repositories.ImageRepository;
import com.bricolage.bricolageback.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;


@Service
public class AnnonceServiceImpl implements AnnonceService{

    @Autowired
    UserRepository userRepository;
    @Autowired
    AnnonceRepository annonceRepository;
    @Autowired
    DomaineRepository domaineRepository;
    @Autowired
    ImageService imageService;
    @Autowired
    ImageRepository imageRepository;
    @Autowired
    NotificationService notificationService;

    @Override
    public Annonce ajoutDemandeService(AnnonceDTO annonceDto) throws IOException {
        Domaine domaine = domaineRepository.findById(annonceDto.getIdDomaine()).get();
        User user = userRepository.findByIdAndBloquedFalse(annonceDto.getIdUser());
        Image image = imageService.uploadImage(annonceDto.getImage(),0);
        Annonce annonce= new Annonce(annonceDto.getTitre(),
                annonceDto.getDescription(),
                annonceDto.getAdresse(),
                annonceDto.getDetails(),
                annonceDto.getDate(),
                annonceDto.getBudgetClient(),
                user,
                domaine,
                false);
        annonce.setImage(image);
        Annonce a = annonceRepository.save(annonce);
        notificationService.addNotification("",annonce,user);
        return a;
    }

    @Override
    public Annonce getAnnonce(long idAnnonce){
        Annonce annonce = annonceRepository.findById(idAnnonce).get();
        Image image = annonce.getImage();
        image.setPicByte(imageService.decompressBytes(image.getPicByte()));
        annonce.setImage(image);
        return annonce;
    }

    @Override
    public List<Annonce> getAnnonceByClient(long idClient) {
        User u = userRepository.findByIdAndBloquedFalse(idClient);
        return annonceRepository.findByClientAndDeletedFalse(u);
    }

    @Override
    public List<Annonce> getAnnonceByBricoleur(long idBricoleur) {
        User u = userRepository.findByIdAndBloquedFalse(idBricoleur);
        return annonceRepository.findByBricoleur(u);
    }

    @Override
    public List<Annonce> getAnnonces() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User u = userRepository.findByUsernameAndBloquedFalse(auth.getPrincipal().toString());
        annonceRepository.findByClientAndTermineFalseAndDeletedFalse(u);
        List<Annonce> annonces = annonceRepository.findByDeletedFalseAndTermineFalse();
        annonces.forEach(annonce -> {
            Image image = annonce.getImage();
            image.setPicByte(imageService.decompressBytes(image.getPicByte()));
            annonce.setImage(image);
        });
        return annonces;
    }

    @Override
    public Annonce modifierAnnonce(AnnonceDTO annonceDTO) throws IOException {
        Annonce annonce = annonceRepository.findById(annonceDTO.getId()).get();
        if (annonceDTO.getImage() != null){
            imageService.uploadImage(annonceDTO.getImage(), annonce.getImage().getId());
        }
        if (annonceDTO.getAdresse() != null)  annonce.setAdresse(annonceDTO.getAdresse());
        if (annonceDTO.getDate()!= null)  annonce.setDate(annonceDTO.getDate());
        if (annonceDTO.getDescription()!= null)  annonce.setDescription(annonceDTO.getDescription());
        if (annonceDTO.getDetails()!= null)  annonce.setDetails(annonceDTO.getDetails());
        if (annonceDTO.getTitre()!= null)  annonce.setTitre(annonceDTO.getTitre());
        if (annonceDTO.getBudgetClient()!= 0)  annonce.setBudgetClient(annonceDTO.getBudgetClient());
        if (annonceDTO.getIdDomaine()!= 0)  annonce.setDomaine(domaineRepository.findById(annonceDTO.getIdDomaine()).get());
        return annonceRepository.save(annonce);
    }

    @Override
    public void deleteAnnonce(long idAnnonce) {
        Annonce annonce = getAnnonce(idAnnonce);
        notificationService.deleteNotificationByAnnonce(annonce);
        annonce.setDeleted(true);
        annonceRepository.save(annonce);
    }
}
