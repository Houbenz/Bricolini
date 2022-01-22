package com.bricolage.bricolageback.services;

import com.bricolage.bricolageback.dto.AddDomaineDto;
import com.bricolage.bricolageback.entities.Domaine;

import java.util.List;
import java.util.Optional;

public interface DomaineService {

    Domaine addDomaine(AddDomaineDto addDomaineDto);
    Domaine getDomaine(long id);
    List<Domaine> getdomaines();
    boolean deleteDomaine(long id);
    Optional<Domaine> updateDomaine(AddDomaineDto domaine, long domaineToFind);
}
