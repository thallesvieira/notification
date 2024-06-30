package com.modak.notification.domain.service.impl;

import com.modak.notification.domain.gateway.INotificationGateway;
import com.modak.notification.domain.model.Notification;
import com.modak.notification.domain.model.Type;
import com.modak.notification.domain.repository.INotificationRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class NotificationServiceImplTest {

    @Mock
    INotificationRepository notificationRepository;

    @Mock
    INotificationGateway notificationGateway;

    @Mock
    DayRuleImpl dayRule;

    @InjectMocks
    NotificationServiceImpl notificationService;

    @Test
    void sendMessageWhenIsAllowed() {
        String userId = "userId";
        String message = "message";
        Type type = Type.NEWS;

        Notification notification = new Notification();
        notification.setDate(LocalDateTime.now());
        notification.setAmount(0);
        notification.setType(type);
        notification.setUserId(userId);

        Mockito.when(notificationRepository.saveNotification(type, userId, message)).thenReturn(notification);
        Mockito.doNothing().when(notificationGateway).send(userId, message);

        notificationService.send(type, userId, message);

        Mockito.verify(notificationGateway,   times(1)).send(userId, message);
    }

    @Test
    void saveNotification() {
        String userId = "userId";
        String message = "message";
        Type type = Type.NEWS;

        Notification notification = new Notification();
        notification.setDate(LocalDateTime.now());
        notification.setAmount(1);
        notification.setLastMessage(message);
        notification.setType(type);
        notification.setUserId(userId);

        Mockito.when(notificationRepository.saveNotification(type, userId, message)).thenReturn(notification);

        Notification response = notificationRepository.saveNotification(type, userId, message);
        Assertions.assertThat(response.getLastMessage()).isEqualTo(message);
        Assertions.assertThat(response.getType()).isEqualTo(type);
        Assertions.assertThat(response.getUserId()).isEqualTo(userId);
        Assertions.assertThat(response.getDate()).isEqualTo(notification.getDate());
    }

    @Test
    void updateNotification() {
        String userId = "userId";
        String message = "message";
        Type type = Type.NEWS;

        Notification notification = new Notification();
        notification.setDate(LocalDateTime.now());
        notification.setAmount(1);
        notification.setLastMessage(message);
        notification.setType(type);
        notification.setUserId(userId);

        Mockito.when(notificationRepository.updateNotification(notification)).thenReturn(notification);

        Notification response = notificationRepository.updateNotification(notification);
        Assertions.assertThat(response.getLastMessage()).isEqualTo(message);
        Assertions.assertThat(response.getType()).isEqualTo(type);
        Assertions.assertThat(response.getUserId()).isEqualTo(userId);
        Assertions.assertThat(response.getDate()).isEqualTo(notification.getDate());
    }
}