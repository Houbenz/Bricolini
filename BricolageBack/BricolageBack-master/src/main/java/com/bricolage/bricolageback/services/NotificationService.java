package com.bricolage.bricolageback.services;

import com.bricolage.bricolageback.entities.Annonce;
import com.bricolage.bricolageback.entities.NotificationUserAssoc;
import com.bricolage.bricolageback.entities.User;

import java.util.List;

public interface NotificationService {
    void viewNotification(long idNotifiction);
    List<NotificationUserAssoc> getNotifications();
    void deleteNotificationByAnnonce(Annonce a);
    void addNotification(String message, Annonce annonce, User client);
}
