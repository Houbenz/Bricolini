package com.bricolage.bricolageback.repositories;

import com.bricolage.bricolageback.entities.Annonce;
import com.bricolage.bricolageback.entities.Proposition;
import com.bricolage.bricolageback.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface PropositionRepository extends JpaRepository<Proposition, Long> {

    public List<Proposition> findByAnnonce(Annonce a);
    public List<Proposition> findByProfessionnel(User user);
    public Optional<Proposition> findById(Long id);
}
