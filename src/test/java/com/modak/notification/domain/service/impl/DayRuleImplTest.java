package com.modak.notification.domain.service.impl;

import com.modak.notification.domain.model.Notification;
import com.modak.notification.domain.model.Type;
import com.modak.notification.domain.repository.INotificationRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class DayRuleImplTest {
    private DayRuleImpl dayRuleImpl = new DayRuleImpl();

    @Test
    void isNotAllowedWhenAmountIsGreaterThanRule() {
        String userId = "userId";
        Type type = Type.NEWS;

        Notification notification = new Notification();
        notification.setDate(LocalDateTime.now());
        notification.setAmount(2);
        notification.setType(type);
        notification.setUserId(userId);

        List<Notification> notifications = Arrays.asList(notification);

        INotificationRepository notificationRepository = Mockito.mock(INotificationRepository.class);
        Mockito.when(notificationRepository.findNotificationByUserIdAndType(userId, type)).thenReturn(notifications);

        boolean isAllowed = dayRuleImpl.isAllowed(userId, type, notificationRepository);

        Assertions.assertThat(isAllowed).isFalse();
    }

    @Test
    void isNotAllowedWhenSumOfAmountListIsGreaterThanRule() {
        String userId = "userId";
        Type type = Type.NEWS;

        Notification notification = new Notification();
        notification.setDate(LocalDateTime.now());
        notification.setAmount(1);
        notification.setType(type);
        notification.setUserId(userId);

        Notification notification2 = new Notification();
        notification2.setDate(LocalDateTime.now());
        notification2.setAmount(1);
        notification2.setType(type);
        notification2.setUserId(userId);

        List<Notification> notifications = Arrays.asList(notification, notification2);

        INotificationRepository notificationRepository = Mockito.mock(INotificationRepository.class);
        Mockito.when(notificationRepository.findNotificationByUserIdAndType(userId, type)).thenReturn(notifications);

        boolean isAllowed = dayRuleImpl.isAllowed(userId, type, notificationRepository);

        Assertions.assertThat(isAllowed).isFalse();
    }

    @Test
    void isAllowedWhenThereIsNoNotification() {
        String userId = "userId";
        Type type = Type.NEWS;

        INotificationRepository notificationRepository = Mockito.mock(INotificationRepository.class);
        Mockito.when(notificationRepository.findNotificationByUserIdAndType(userId, type)).thenReturn(null);

        boolean isAllowed = dayRuleImpl.isAllowed(userId, type, notificationRepository);

        Assertions.assertThat(isAllowed).isTrue();
    }

    @Test
    void getInitialDate() {
        LocalDateTime date = dayRuleImpl.getInitialDate();
        Assertions.assertThat(LocalDateTime.now()).isAfter(date);
        Assertions.assertThat(LocalDateTime.now().toLocalDate()).isEqualTo(date.toLocalDate());
    }

    @Test
    void getFinalDate() {
        LocalDateTime date = dayRuleImpl.getFinalDate();
        Assertions.assertThat(LocalDateTime.now()).isBefore(date);
        Assertions.assertThat(LocalDateTime.now().toLocalDate()).isEqualTo(date.toLocalDate());
    }
}