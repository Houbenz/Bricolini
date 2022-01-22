package com.bricolage.bricolageback.repositories;

import com.bricolage.bricolageback.entities.Annonce;
import com.bricolage.bricolageback.entities.Devis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DevisRepository extends JpaRepository<Devis, Long> {

    Optional<Devis> findByAnnonce(Annonce annonce);
}
