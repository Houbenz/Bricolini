package com.bricolage.bricolageback.repositories;

import com.bricolage.bricolageback.entities.Annonce;
import com.bricolage.bricolageback.entities.Avis;
import com.bricolage.bricolageback.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AvisRepository extends JpaRepository<Avis, Long> {

    @Query("select a from Avis a inner join Annonce b " +
            "on a.user = b.client where a.annonce = ?1")
    Avis findClientAvisByAnnonce(Annonce annonce);

    @Query("select a from Avis a inner join Devis d " +
            "on a.user = d.professionnel where d.annonce = ?1")
    Avis findProfessionnelAvisByAnnonce(Annonce annonce);

    Optional<Avis> findAvisByAnnonceAndUser(Annonce annonce, User user);

    @Query("select avg(b.rate) " +
            "from Annonce a inner join Avis b on a.avis_pro = b.id " +
            "where a.client = ?1")
    Optional<Double> findAverageOfClient(User user);

    @Query("select avg(b.rate) " +
            "from Annonce a inner join Avis b on a.avis_client = b.id " +
            "inner join Devis d on d.annonce = a.id" +
            " where d.professionnel = ?1")
    Optional<Double> findAverageOfProfessionnel(User user);
}
