package com.bricolage.bricolageback.controllers;

import com.bricolage.bricolageback.dto.SignalementDto;
import com.bricolage.bricolageback.entities.Signalement;
import com.bricolage.bricolageback.entities.User;
import com.bricolage.bricolageback.exceptions.AnnonceNotFoundException;
import com.bricolage.bricolageback.exceptions.UserNotFoundException;
import com.bricolage.bricolageback.repositories.SignalementRepository;
import com.bricolage.bricolageback.repositories.UserRepository;
import com.bricolage.bricolageback.services.AccountService;
import com.bricolage.bricolageback.services.SignalementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/signalement")
public class SignalementController {

    @Autowired
    SignalementService signalementService;
    @Autowired
    SignalementRepository signalementRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    AccountService accountService;

    @PostMapping
    public ResponseEntity<Boolean> ajouterSignalement(@RequestBody SignalementDto signalementDto){

        User brico = userRepository.findById(signalementDto.getIdBricoleur()).get();
        signalementService.ajouterSignalement(signalementDto, brico);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority({'ADMINISTRATEUR'})")
    @GetMapping
    public ResponseEntity<List<SignalementDto>> getSignalements(){
        return new ResponseEntity<>(signalementService.getSignalements(), HttpStatus.OK);
    }

    @GetMapping("/client/{annonceId}")
    public ResponseEntity<SignalementDto> getSignalementClient(@PathVariable long annonceId){
        try {
            return new ResponseEntity<>(signalementService.getSignalementFromClient(annonceId),HttpStatus.OK);
        } catch (AnnonceNotFoundException e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/professionnel/{annonceId}")
    public ResponseEntity<SignalementDto> getSignalementPro(@PathVariable long annonceId){
        try {
            return new ResponseEntity<>(signalementService.getSignalementFromBricoleur(annonceId),HttpStatus.OK);
        } catch (UserNotFoundException | AnnonceNotFoundException e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
}
