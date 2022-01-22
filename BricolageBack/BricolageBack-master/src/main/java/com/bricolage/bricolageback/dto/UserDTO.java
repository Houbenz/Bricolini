package com.bricolage.bricolageback.dto;

import com.bricolage.bricolageback.entities.User;
import com.bricolage.bricolageback.exceptions.EmptyUserException;
import com.bricolage.bricolageback.exceptions.UserNotFoundException;
import com.bricolage.bricolageback.services.AvisService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    long id;
    String nom;
    String prenom;
    String email;
    String phone_number;
    String password;
    String adresse;
    String role;
    String domaine;
    boolean premium;
    String repassword;
    double rate;

    public static UserDTO castUser(User u, AvisService as){
        UserDTO uDTO = new UserDTO();
        uDTO.setId(u.getId());
        uDTO.setAdresse(u.getAdresse());
        //this.bricoleur.setDomaine(u.);
        //this.bricoleur.setEmail();
        uDTO.setNom(u.getNom());
        uDTO.setPhone_number(u.getTelephone());
        uDTO.setPrenom(u.getPrenom());
        try {
            uDTO.setRate(as.getAverageRateOfUser(u.getId()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return uDTO;
    }
}
