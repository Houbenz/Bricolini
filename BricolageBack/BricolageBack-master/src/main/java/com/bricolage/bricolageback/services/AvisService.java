package com.bricolage.bricolageback.services;

import com.bricolage.bricolageback.dto.AvisDto;
import com.bricolage.bricolageback.entities.User;
import com.bricolage.bricolageback.exceptions.*;

public interface AvisService {

    void ajoutAvis(User user , AvisDto addAvisDto) throws AnnonceNotFoundException, InvalidAvisException;
    AvisDto getAvisClient(long annonceId) throws AnnonceNotFoundException, AvisNotFoundException;
    AvisDto getAvisProfessionnel(long annonceId) throws AnnonceNotFoundException, AvisNotFoundException;

    double getAverageRateOfUser(long id) throws UserNotFoundException, EmptyUserException;
}