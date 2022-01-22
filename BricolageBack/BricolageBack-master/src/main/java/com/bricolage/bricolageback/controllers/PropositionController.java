package com.bricolage.bricolageback.controllers;

import com.bricolage.bricolageback.dto.PropositionDTO;
import com.bricolage.bricolageback.dto.UserDTO;
import com.bricolage.bricolageback.entities.Proposition;
import com.bricolage.bricolageback.entities.User;
import com.bricolage.bricolageback.exceptions.EmptyUserException;
import com.bricolage.bricolageback.exceptions.UserNotFoundException;
import com.bricolage.bricolageback.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/propositions")
public class PropositionController {

    @Autowired
    private PropositionDevisService propoService;
    @Autowired
    private UserDetailsService userS;
    @Autowired
    private AnnonceService annS;
    @Autowired
    private AvisService avisService;
    @Autowired
    AccountService accountService;
    @Autowired
    DevisService devisService;

    @PostMapping()
    private ResponseEntity<Proposition> ajoutProposition(Authentication authentication,@RequestBody PropositionDTO propo){
        User user = accountService.findUserByUsername((String) authentication.getPrincipal());
        propo.setIdProfessionnel(user.getId());
        return  ResponseEntity.status(HttpStatus.CREATED).body(propoService.ajouterProposition(propo));
    }

    @GetMapping("/annonce/{idAnnonce}")
    public List<PropositionDTO> getPropositionsByAnnonce(@PathVariable Long idAnnonce){
        Optional<PropositionDTO> opPropo = devisService.getDevisByAnnonce(idAnnonce);
        if (opPropo.isPresent()){
            return new ArrayList<PropositionDTO>(){{add(opPropo.get());}};
        }
        return propoService.listPropositionByAnnonce(
                annS.getAnnonce(idAnnonce)).stream().map(
                        p->new PropositionDTO(p.getId(),
                                p.getMessage(),
                                p.getPrice(),
                                p.getDate(),
                                p.getAnnonce().getId(),
                                p.getProfessionnel().getId(),
                                false,UserDTO.castUser(p.getProfessionnel(),avisService),p.getAnnonce())).collect(Collectors.toList());

    }

    @GetMapping
    public List<PropositionDTO> getPropositions(Authentication authentication){
        User user = accountService.findUserByUsername((String) authentication.getPrincipal());
        return propoService.listPropositionByProfessionnel(user.getId()).stream().map(p->new PropositionDTO(p.getId(),p.getMessage(),p.getPrice(),p.getDate(),p.getAnnonce().getId(),p.getProfessionnel().getId(),false,UserDTO.castUser(p.getProfessionnel(),avisService),p.getAnnonce())).collect(Collectors.toList());
    }

    @GetMapping("/currentUser")
    public List<UserDTO> getCurrentUser(Authentication authentication) throws UserNotFoundException, EmptyUserException {
        User user = accountService.findUserByUsername((String) authentication.getPrincipal());
        UserDTO usDTO = new UserDTO(user.getId(),user.getNom(),user.getPrenom(),"@",user.getTelephone(),
                user.getPassword(),user.getAdresse(), null,null,user.isPremium(),user.getPassword(),avisService.getAverageRateOfUser(user.getId()));
        List<UserDTO> list = new ArrayList<UserDTO>();
        list.add(usDTO);
        return list;
    }
    /*@PostMapping("/update")
    private ResponseEntity<Proposition> modifierProposition(@RequestBody Proposition propo){
        return  ResponseEntity.status(HttpStatus.CREATED).body(propoService.modifierProposition(propo));
    }
    @PostMapping("/findOne")
    private ResponseEntity<Proposition> trouverProposition(@RequestBody Proposition propo){
        return  ResponseEntity.status(HttpStatus.CREATED).body(propoService.trouverProposition(propo));
    }*/
    @DeleteMapping("{idProposition}")
    public void deleteProposition(@PathVariable Long idProposition) {
        propoService.annulerProposition(idProposition);
    }

    /*@PostMapping("/delete")
    private ResponseEntity<Proposition> supprimerProposition(@RequestBody Proposition propo){
        return  ResponseEntity.status(HttpStatus.CREATED).body(propoService.annulerProposition(propo));
    }*/
}
