package com.bricolage.bricolageback.controllers;

import com.bricolage.bricolageback.dto.AnnonceDTO;
import com.bricolage.bricolageback.entities.Annonce;
import com.bricolage.bricolageback.services.AnnonceService;
import com.bricolage.bricolageback.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("")
public class AnnonceController {

    @Autowired
    AnnonceService annonceService;
    @Autowired
    ImageService imageService;

    @PreAuthorize("hasAnyAuthority({'CLIENT'})")
    @PostMapping("/annonces")
    public Annonce ajouterAnnonce(@ModelAttribute AnnonceDTO annonceDTO) throws IOException {
        return annonceService.ajoutDemandeService(annonceDTO);
    }

    @PreAuthorize("hasAnyAuthority({'CLIENT'})")
    @PutMapping("/annonces")
    public Annonce modifierAnnonce(@ModelAttribute AnnonceDTO annonceDTO) throws IOException {
        return annonceService.modifierAnnonce(annonceDTO);
    }

    @PreAuthorize("hasAnyAuthority({'CLIENT','BRICOLEUR','ADMINISTRATEUR'})")
    @GetMapping("/annonces/{idAnnonce}")
    public Annonce getAnnnonce(@PathVariable long idAnnonce) {
        return annonceService.getAnnonce(idAnnonce);
    }

    @PreAuthorize("hasAnyAuthority({'CLIENT'})")
    @DeleteMapping("/annonces/{idAnnonce}")
    public void deleteAnnonce(@PathVariable long idAnnonce) {
        annonceService.deleteAnnonce(idAnnonce);
    }

    @PreAuthorize("hasAnyAuthority({'CLIENT','BRICOLEUR','ADMINISTRATEUR'})")
    @GetMapping("/annonces")
    public List<Annonce> getAnnonces() {
        return annonceService.getAnnonces();
    }

    @PreAuthorize("hasAnyAuthority({'CLIENT'})")
    @GetMapping("clients/{idClient}/annonces")
    public List<Annonce> getAnnoncesByClient(@PathVariable long idClient) {
        return annonceService.getAnnonceByClient(idClient);
    }

    @PreAuthorize("hasAnyAuthority({'BRICOLEUR'})")
    @GetMapping("bricoleurs/{idBricoleur}/annonces")
    public List<Annonce> getAnnoncesByBricoleur(@PathVariable long idBricoleur) {
        return annonceService.getAnnonceByBricoleur(idBricoleur);
    }
}

