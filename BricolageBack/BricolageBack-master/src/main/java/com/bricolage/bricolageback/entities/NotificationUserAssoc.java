package com.bricolage.bricolageback.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "Notification_User_Association")
public class NotificationUserAssoc {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ManyToOne
    private User user;
    @OneToOne()
    private Annonce annonce;
    private String message;

    private boolean viewed;

    public NotificationUserAssoc(Annonce annonce, User user, boolean viewed) {
        this.user = user;
        this.annonce = annonce;
        this.viewed = viewed;
    }
}
