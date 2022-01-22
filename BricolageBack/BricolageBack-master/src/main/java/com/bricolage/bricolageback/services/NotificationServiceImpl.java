package com.bricolage.bricolageback.services;
import com.bricolage.bricolageback.entities.Annonce;
import com.bricolage.bricolageback.entities.NotificationUserAssoc;
import com.bricolage.bricolageback.entities.User;
import com.bricolage.bricolageback.repositories.NotificationUserAssocRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private NotificationUserAssocRepository notifUserAssRepo;

    @Override
    public void viewNotification(long id) {
        NotificationUserAssoc notif = notifUserAssRepo.findById(id).get();
        notif.setViewed(true);
        notifUserAssRepo.save(notif);
    }

    @Override
    public List<NotificationUserAssoc> getNotifications() {
        return notifUserAssRepo.findByViewedFalse();
    }

    @Override
    public void deleteNotificationByAnnonce(Annonce a) {
        if (notifUserAssRepo.findByAnnonce(a)!= null)
        notifUserAssRepo.delete(notifUserAssRepo.findByAnnonce(a));
    }


    @Override
    public void addNotification(String message, Annonce annonce, User client) {
        notifUserAssRepo.save(new NotificationUserAssoc(annonce,client,false));
    }
}
