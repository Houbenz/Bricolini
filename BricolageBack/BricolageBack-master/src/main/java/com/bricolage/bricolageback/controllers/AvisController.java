package com.bricolage.bricolageback.controllers;

import com.bricolage.bricolageback.dto.AvisDto;
import com.bricolage.bricolageback.entities.User;
import com.bricolage.bricolageback.exceptions.*;
import com.bricolage.bricolageback.services.AccountService;
import com.bricolage.bricolageback.services.AvisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/avis")
public class AvisController {

    @Autowired
    AvisService avisService;

    @Autowired
    AccountService accountService;

    @PostMapping("/publier")
    public ResponseEntity<Boolean> publierAvis(Authentication authentication, @RequestBody AvisDto addAvisDto){

        User user = accountService.findUserByUsername((String) authentication.getPrincipal());

        try {
            avisService.ajoutAvis(user,addAvisDto);
        } catch (AnnonceNotFoundException | InvalidAvisException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping("/client/{id}")
    public ResponseEntity<AvisDto> getAvisClient(@PathVariable long id){
        try {
            return new ResponseEntity<>(avisService.getAvisClient(id),HttpStatus.OK);
        } catch (AnnonceNotFoundException | AvisNotFoundException e) {
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping("/professionnel/{id}")
    public ResponseEntity<AvisDto> getAvisProfessionnel(@PathVariable long id){
        try {
            return new ResponseEntity<>(avisService.getAvisProfessionnel(id),HttpStatus.OK);
        } catch (AnnonceNotFoundException | AvisNotFoundException e) {
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping("/rate/{id}")
    public ResponseEntity<Double> getRate(@PathVariable long id){
        try {
            return new ResponseEntity<>(avisService.getAverageRateOfUser(id),HttpStatus.OK);
        } catch (UserNotFoundException | EmptyUserException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
