package com.bricolage.bricolageback.services;

import com.bricolage.bricolageback.dto.SignalementDto;
import com.bricolage.bricolageback.entities.User;
import com.bricolage.bricolageback.exceptions.AnnonceNotFoundException;
import com.bricolage.bricolageback.exceptions.UserNotFoundException;

import java.util.List;

public interface SignalementService {

    void ajouterSignalement(SignalementDto signalementDto, User user);

    List<SignalementDto> getSignalements();

    SignalementDto getSignalementFromClient(long annonceId) throws AnnonceNotFoundException;
    SignalementDto getSignalementFromBricoleur(long annonceId) throws UserNotFoundException, AnnonceNotFoundException;
}
