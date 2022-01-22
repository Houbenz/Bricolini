package com.bricolage.bricolageback.repositories;

import com.bricolage.bricolageback.entities.Annonce;
import com.bricolage.bricolageback.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnonceRepository extends JpaRepository< Annonce, Long> {

    List<Annonce> findByTermineTrue();
    List<Annonce> findByDeletedFalse();
    List<Annonce> findByDeletedFalseAndTermineFalse();

    List<Annonce> findByClientAndTermineFalseAndDeletedFalse(User client);
    List<Annonce> findByClientAndDeletedFalse(User client);

    @Query("select a from Annonce a inner join Devis d on  a.id = d.annonce.id "+
    "where d.professionnel = ?1 and a.termine = false")
    List<Annonce> findByBricoleur(User bricoleur);
}
