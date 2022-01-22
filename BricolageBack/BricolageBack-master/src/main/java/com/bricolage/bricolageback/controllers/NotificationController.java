package com.bricolage.bricolageback.controllers;

import com.bricolage.bricolageback.entities.NotificationUserAssoc;
import com.bricolage.bricolageback.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    NotificationService notificationService;

    @PostMapping("/{idNotif}")
    public void viewNotification(@PathVariable long idNotif) {
         notificationService.viewNotification(idNotif);
    }
    @GetMapping("")
    public List<NotificationUserAssoc> getNotification() {
        return notificationService.getNotifications();
    }

}

