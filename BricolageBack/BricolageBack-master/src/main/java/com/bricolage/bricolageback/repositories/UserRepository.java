package com.bricolage.bricolageback.repositories;

import com.bricolage.bricolageback.entities.Domaine;
import com.bricolage.bricolageback.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository  extends JpaRepository<User, Long> {
    User findByUsernameAndBloquedFalse(String username);
    User findByIdAndBloquedFalse(long id);
    List<User> findByBloquedFalse();

    Optional<Domaine> findDomaineByUsername(String username);

    @Query("SELECT u from User u where u.username like %:username% and u.bloqued = false")
    List<User> searchUsers(@Param("username") String username);


}
