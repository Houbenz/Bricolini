package com.bricolage.bricolageback.repositories;

import com.bricolage.bricolageback.entities.Domaine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DomaineRepository extends JpaRepository<Domaine, Long> {

    Optional<Domaine> findByDomaine(String domaine);
    List<Domaine> findByDeletedFalse();

}
