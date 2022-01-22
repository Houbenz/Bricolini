package com.bricolage.bricolageback;

import com.bricolage.bricolageback.config.security.Roles;
import com.bricolage.bricolageback.entities.Domaine;
import com.bricolage.bricolageback.entities.Role;
import com.bricolage.bricolageback.repositories.DomaineRepository;
import com.bricolage.bricolageback.repositories.RoleRepository;
import com.bricolage.bricolageback.repositories.UserRepository;
import com.bricolage.bricolageback.services.DomaineService;
import com.bricolage.bricolageback.services.PropositionDevisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication(scanBasePackages = "com.bricolage.bricolageback")
@RestController
public class BricolageBackApplication implements CommandLineRunner{

    @Autowired
    DomaineService domaineService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PropositionDevisService pDev;

    @Autowired
    DomaineRepository domaineRepository;
    public static void main(String[] args){
        SpringApplication.run(BricolageBackApplication.class, args);
    }

    //for entering the first page
    @GetMapping
    public List<String> hello(){
        return List.of("Hello ","World");
    }

    @Override
    public void run(String... args) throws Exception {
        /*userRepository.save(new User("ilyass","ejjaouchi","ilyass@gmail.com","adresse","0654343212","passs", true));
        userRepository.save(new User("amine","amine","amine@gmail.com","0654343212","adresse","passs", true));

        domaineRepository.save(new Domaine(0,"plombrie","description 2"));
        domaineRepository.save(new Domaine(0,"electricite","description 2"));
*/
        /*/*domaineRepository.save(new Domaine(0,"plomberie","description 2"));
        domaineRepository.save(new Domaine(0,"Electricité","description 2"));

        roleRepository.save(new Role(0, Roles.ADMINISTRATEUR));
        roleRepository.save(new Role(0,Roles.CLIENT));
        roleRepository.save(new Role(0,Roles.BRICOLEUR));*/
    }
}
