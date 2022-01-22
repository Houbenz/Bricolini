package com.bricolage.bricolageback.services;

import com.bricolage.bricolageback.dto.AddDomaineDto;
import com.bricolage.bricolageback.entities.Domaine;
import com.bricolage.bricolageback.entities.User;
import com.bricolage.bricolageback.repositories.DomaineRepository;
import com.bricolage.bricolageback.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DomaineServiceImpl implements DomaineService {


    @Autowired
    private DomaineRepository domaineRepository;

    @Autowired
    private UserRepository userRepository;


    @Transactional
    @Override
    public Domaine addDomaine(AddDomaineDto addDomaineDto) {

        Domaine domaine = Domaine.builder()
                .domaine(addDomaineDto.getDomaine())
                .description(addDomaineDto.getDescription())
                .build();

         return domaineRepository.save(domaine);

    }

    @Override
    public Domaine getDomaine(long id) {
        return domaineRepository.findById(id).get();
    }

    @Override
    public List<Domaine> getdomaines() {
        return domaineRepository.findByDeletedFalse();
    }

    @Override
    public boolean deleteDomaine(long id) {
        Domaine d = domaineRepository.findById(id).get();
        d.setDeleted(true);
        domaineRepository.save(d);
        return d.isDeleted();

    }

    @Override
    public Optional<Domaine> updateDomaine(AddDomaineDto addDomaineDto , long domaineToFind) {
        Optional<Domaine> domaineOptional = domaineRepository.findById(domaineToFind);
        Domaine domaine;

        if(domaineOptional.isPresent()){
            domaine = domaineOptional.get();
            domaine.setDomaine(addDomaineDto.getDomaine());
            domaine.setDescription(addDomaineDto.getDescription());
            domaineRepository.save(domaine);
        }
        return  domaineRepository.findById(domaineToFind);
    }
}
