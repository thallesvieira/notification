package com.modak.notification.resource.persistence.repository.impl;

import com.modak.notification.domain.model.Notification;
import com.modak.notification.domain.model.Type;
import com.modak.notification.resource.persistence.entity.NotificationEntity;
import com.modak.notification.resource.persistence.repository.INotificationJpaRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class NotificationRepositoryImplTest {

    @Mock
    private INotificationJpaRepository notificationJpaRepository;

    @InjectMocks
    NotificationRepositoryImpl notificationRepository;// = new NotificationRepositoryImpl();

    private ModelMapper mapper = new ModelMapper();

    @Test
    void saveNotification() {
        String userId = "userId";
        String message = "message";
        Type type = Type.NEWS;

        NotificationEntity notificationEntity = new NotificationEntity();

        notificationEntity.setLastMessage(message);
        notificationEntity.setDate(LocalDateTime.now());
        notificationEntity.setType(type);
        notificationEntity.setUserId(userId);
        notificationEntity.setAmount(1);

        Mockito.when(notificationJpaRepository.save(any())).thenReturn(notificationEntity);

        Notification response = notificationRepository.saveNotification(type, userId, message);

        Assertions.assertThat(response.getLastMessage()).isEqualTo(message);
        Assertions.assertThat(response.getType()).isEqualTo(type);
        Assertions.assertThat(response.getUserId()).isEqualTo(userId);
        Assertions.assertThat(response.getDate()).isEqualTo(notificationEntity.getDate());
    }

    @Test
    void updateNotification() {
        String userId = "userId";
        String message = "message";
        Type type = Type.NEWS;

        NotificationEntity notificationEntity = new NotificationEntity();

        notificationEntity.setLastMessage(message);
        notificationEntity.setDate(LocalDateTime.now());
        notificationEntity.setType(type);
        notificationEntity.setUserId(userId);
        notificationEntity.setAmount(1);

        Notification notification = mapper.map(notificationEntity, Notification.class);

        Mockito.when(notificationJpaRepository.save(any())).thenReturn(notificationEntity);

        Notification response = notificationRepository.updateNotification(notification);

        Assertions.assertThat(response.getLastMessage()).isEqualTo(message);
        Assertions.assertThat(response.getType()).isEqualTo(type);
        Assertions.assertThat(response.getUserId()).isEqualTo(userId);
        Assertions.assertThat(response.getDate()).isEqualTo(notificationEntity.getDate());
    }

    @Test
    void findNotificationByUserIdAndType() {
        String userId = "userId";
        String message = "message";
        Type type = Type.UPDATE;

        NotificationEntity notification = new NotificationEntity();
        notification.setDate(LocalDateTime.now());
        notification.setAmount(1);
        notification.setLastMessage(message);
        notification.setType(type);
        notification.setUserId(userId);

        NotificationEntity notification2 = new NotificationEntity();
        notification2.setDate(LocalDateTime.now());
        notification2.setAmount(1);
        notification2.setLastMessage(message);
        notification2.setType(type);
        notification2.setUserId(userId);

        List<NotificationEntity> notifications = Arrays.asList(notification, notification2);

        Mockito.when(
                notificationJpaRepository.findAllByUserIdAndTypeAndDateBetween(any(), any(), any(), any()))
                .thenReturn(notifications);

        List<Notification> response = notificationRepository.findNotificationByUserIdAndType(userId, type);

        Assertions.assertThat(response.stream().count()).isEqualTo(2);
    }

    @Test
    void findNotificationByUserIdAndTypeAndReturnNull() {
        String userId = "userId";
        Type type = Type.UPDATE;

        List<NotificationEntity> notifications = new ArrayList<>();

        Mockito.when(
                        notificationJpaRepository.findAllByUserIdAndTypeAndDateBetween(any(), any(), any(), any()))
                .thenReturn(notifications);

        List<Notification> response = notificationRepository.findNotificationByUserIdAndType(userId, type);

        Assertions.assertThat(response).isNull();
    }
}