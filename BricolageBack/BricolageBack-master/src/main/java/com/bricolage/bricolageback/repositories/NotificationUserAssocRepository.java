package com.bricolage.bricolageback.repositories;


import com.bricolage.bricolageback.entities.Annonce;
import com.bricolage.bricolageback.entities.NotificationUserAssoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface NotificationUserAssocRepository extends JpaRepository<NotificationUserAssoc, Long> {
        public List<NotificationUserAssoc> findByViewedFalse();
        public NotificationUserAssoc findByAnnonce(Annonce a);
}
