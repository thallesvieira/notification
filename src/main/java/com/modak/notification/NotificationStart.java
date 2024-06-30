package com.modak.notification;

import com.modak.notification.domain.model.Type;
import com.modak.notification.domain.service.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotificationStart {

    @Autowired
    INotificationService notificationService;

    /*Method to initialize system and run some examples.*/
    @Bean
    public void start() throws Exception {
        notificationService.send(Type.NEWS, "user", "news 1");
        notificationService.send(Type.NEWS, "user", "news 2");
        notificationService.send(Type.NEWS, "user", "news 3");
        notificationService.send(Type.NEWS, "user2", "news 1");
        notificationService.send(Type.NEWS, "user2", "news 2");
        notificationService.send(Type.NEWS, "user2", "news 3");

        notificationService.send(Type.STATUS, "user", "status 1");
        notificationService.send(Type.STATUS, "user", "status 2");
        notificationService.send(Type.STATUS, "user", "status 3");
        notificationService.send(Type.STATUS, "user2", "status 1");
        notificationService.send(Type.STATUS, "user2", "status 2");
        notificationService.send(Type.STATUS, "user2", "status 3");

        notificationService.send(Type.MARKETING, "user", "Marketing 1");
        notificationService.send(Type.MARKETING, "user", "Marketing 2");
        notificationService.send(Type.MARKETING, "user", "Marketing 3");
        notificationService.send(Type.MARKETING, "user2", "Marketing 1");
        notificationService.send(Type.MARKETING, "user2", "Marketing 2");
        notificationService.send(Type.MARKETING, "user2", "Marketing 3");

        notificationService.send(Type.UPDATE, "user", "Update 1");
        notificationService.send(Type.UPDATE, "user", "Update 2");
        notificationService.send(Type.UPDATE, "user", "Update 3");
        notificationService.send(Type.UPDATE, "user2", "Update 1");
        notificationService.send(Type.UPDATE, "user2", "Update 2");
        notificationService.send(Type.UPDATE, "user2", "Update 3");

        notificationService.send(Type.CONFIRMATION, "user", "Confirmation 1");
        notificationService.send(Type.CONFIRMATION, "user", "Confirmation 2");
        notificationService.send(Type.CONFIRMATION, "user", "Confirmation 3");
        notificationService.send(Type.CONFIRMATION, "user2", "Confirmation 1");
        notificationService.send(Type.CONFIRMATION, "user2", "Confirmation 2");
        notificationService.send(Type.CONFIRMATION, "user2", "Confirmation 3");

    }

}
