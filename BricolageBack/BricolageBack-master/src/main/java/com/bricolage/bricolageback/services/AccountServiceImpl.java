package com.bricolage.bricolageback.services;

import com.bricolage.bricolageback.config.security.Roles;
import com.bricolage.bricolageback.config.security.dto.RegistrationForm;
import com.bricolage.bricolageback.dto.ResetPasswordDTO;
import com.bricolage.bricolageback.dto.UserDTO;
import com.bricolage.bricolageback.entities.Role;
import com.bricolage.bricolageback.entities.User;
import com.bricolage.bricolageback.exceptions.BadRequestException;
import com.bricolage.bricolageback.repositories.RoleRepository;
import com.bricolage.bricolageback.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private DomaineService domaineService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public void updateUser(User user,UserDTO userDTO){
        user.setAdresse(userDTO.getAdresse());
        user.setTelephone(userDTO.getPhone_number());
        user.setPrenom(userDTO.getPrenom());
        user.setNom(userDTO.getNom());
        user.setDomaine(domaineService.getDomaine(Integer.parseInt(userDTO.getDomaine())));

        if(!userDTO.getPassword().isEmpty() &&
        userDTO.getPassword().equals(userDTO.getRepassword())){
            user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        }
        userRepository.save(user);
    }

    public Role saveRole(Role r) {
        Role role = roleRepository.findByRole(r.getRole());
        if (role == null) {
            return roleRepository.save(r);
        } else {
            throw new BadRequestException("le role existe déja");
        }
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsernameAndBloquedFalse(username);
    }

    public void addRoleToUser(String username, Roles r) {
        User user = userRepository.findByUsernameAndBloquedFalse(username);
        Role role = roleRepository.findByRole(r);
        user.addRole(role);
        userRepository.save(user);
    }

    public User signUp(RegistrationForm data) {
        User user = findUserByUsername(data.getEmail());
        if (user != null) throw new BadRequestException("Cet utilisateur existe déjà, Essayez avec un autre email");
        String password = data.getPassword();
        String repassword = data.getRepassword();
        if (!password.equals(repassword))
            throw new RuntimeException("Vous devez confirmer votre mot de passe");
        User u = new User(
                data.getNom(),
                data.getPrenom(),
                data.getEmail(),
                data.getAdresse(),
                data.getPhone_number(),
                data.getPassword(),
                data.isPremium());

        if(data.getRole().equals(Roles.BRICOLEUR))
            u.setDomaine(domaineService.getDomaine(data.getDomaine()));

        saveUser(u);
        addRoleToUser(data.getEmail(), data.getRole());
        return (u);
    }
    public User resetPassword(ResetPasswordDTO data) {
        User user = userRepository.findByUsernameAndBloquedFalse(data.getUsername());
        if (user!= null && bCryptPasswordEncoder.matches(data.getPasswordActual(),user.getPassword())){
            if (data.getPassword().equals(data.getRepassword())){
                user.setPassword(bCryptPasswordEncoder.encode(data.getPassword()));
                userRepository.save(user);
            }else{
                throw new BadRequestException("Les mot de passes ne sont pas égaux");
            }
        }else{
            throw new BadRequestException("L'utilisateur n'existe pas ou le mot de passe actuel est incorrect");
        }
        return null;
    }

    public List<RegistrationForm> getAllUsersByUsername(String username){
        List<User> users = userRepository.searchUsers(username);

        List<RegistrationForm> usersMapped =users.stream().map(user ->
                RegistrationForm.builder()
                        .id(user.getId())
                        .nom(user.getNom())
                        .prenom(user.getPrenom())
                        .adresse(user.getAdresse())
                        .phone_number(user.getTelephone())
                        .domaine(0)
                        .email(user.getUsername())
                        .role(user.getRoles().iterator().next().getRole())//not sure is the correct code
                        .premium(user.isPremium())
                        .build()).collect(Collectors.toList());
        return usersMapped;
    }

    public List<User> getAllUsers(){
        return userRepository.findByBloquedFalse();
    }

    public boolean blockUser(long id){
        User u = userRepository.findByIdAndBloquedFalse(id);
        u.setBloqued(true);
        userRepository.save(u);
        return userRepository.findById(id).get().isBloqued();
    }

}
