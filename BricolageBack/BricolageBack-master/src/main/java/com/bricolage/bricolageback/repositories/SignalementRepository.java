package com.bricolage.bricolageback.repositories;

import com.bricolage.bricolageback.entities.Annonce;
import com.bricolage.bricolageback.entities.Signalement;
import com.bricolage.bricolageback.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SignalementRepository extends JpaRepository<Signalement, Long> {

    Optional<Signalement> findByAnnonceAndUser(Annonce annonce, User user);
}
