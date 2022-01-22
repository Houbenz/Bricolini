package com.bricolage.bricolageback.controllers;

import com.bricolage.bricolageback.dto.DevisDTO;
import com.bricolage.bricolageback.entities.User;
import com.bricolage.bricolageback.services.AccountService;
import com.bricolage.bricolageback.services.AnnonceService;
import com.bricolage.bricolageback.services.DevisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/devis")
public class DevisController {
    @Autowired
    private DevisService devisService;
    @Autowired
    private UserDetailsService userS;
    @Autowired
    private AnnonceService annS;
    @Autowired
    AccountService accountService;

    @PostMapping()
    private ResponseEntity<DevisDTO> ajoutDevis(Authentication authentication, @RequestBody long idProposition){
        User user = accountService.findUserByUsername((String) authentication.getPrincipal());
        //devis.setIdProfessionnel(user.getId());
        return  ResponseEntity.status(HttpStatus.CREATED).body(devisService.ajouterDevis(idProposition));
    }
}
