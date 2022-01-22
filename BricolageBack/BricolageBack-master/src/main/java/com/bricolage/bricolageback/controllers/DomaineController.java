package com.bricolage.bricolageback.controllers;


import com.bricolage.bricolageback.dto.AddDomaineDto;
import com.bricolage.bricolageback.dto.StringResponse;
import com.bricolage.bricolageback.entities.Domaine;
import com.bricolage.bricolageback.services.DomaineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/domaines")
public class DomaineController {

    @Autowired
    private DomaineService domaineService;


    @PostMapping()
    public ResponseEntity<StringResponse> addDomaine(@RequestBody AddDomaineDto addDomaineDto) {
        Domaine added = domaineService.addDomaine(addDomaineDto);
        return ResponseEntity.status(added != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
                .body(new StringResponse(
                        added != null ?"Domaine "+added.getDomaine()+" ajouté avec succéss"
                                :
                                "Une erreur est survenu"));
    }

    @GetMapping()
    public List<Domaine> getDomaines() {
        return domaineService.getdomaines();
    }

    @GetMapping("/{id}")
    public int getDomaine(@PathVariable(name = "id") int id){
        return id;
        //return domaineService.getdomaines().get(0);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteDomaine(@PathVariable(name = "id") int id){

        boolean result = domaineService.deleteDomaine(id);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(result);
    }

    @PutMapping()
    public ResponseEntity<Domaine> updateDomaine(@RequestBody AddDomaineDto addDomaineDto){
       Optional<Domaine> domaine  =  domaineService.updateDomaine(addDomaineDto, addDomaineDto.getId());

       if(domaine.isPresent()){
           return ResponseEntity
                   .status(HttpStatus.OK)
                   .body(domaine.get());
       }
       return  ResponseEntity
               .status(HttpStatus.NOT_ACCEPTABLE)
               .body(null);
    }
}
