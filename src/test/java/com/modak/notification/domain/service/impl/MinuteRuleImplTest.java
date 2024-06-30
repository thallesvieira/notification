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

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MinuteRuleImplTest {
    private MinuteRuleImpl minuteRuleImpl = new MinuteRuleImpl();

    @Test
    void isNotAllowedWhenAmountIsEqualsRule() {
        String userId = "userId";
        Type type = Type.STATUS;

        Notification notification = new Notification();
        notification.setDate(LocalDateTime.now());
        notification.setAmount(2);
        notification.setType(type);
        notification.setUserId(userId);

        List<Notification> notifications = Arrays.asList(notification);

        INotificationRepository notificationRepository = Mockito.mock(INotificationRepository.class);
        Mockito.when(notificationRepository.findNotificationByUserIdAndType(userId, type)).thenReturn(notifications);

        boolean isAllowed = minuteRuleImpl.isAllowed(userId, type, notificationRepository);

        Assertions.assertThat(isAllowed).isFalse();
    }

    @Test
    void isAllowedWhenThereIsLessThanRule() {
        String userId = "userId";
        Type type = Type.STATUS;

        Notification notification = new Notification();
        notification.setDate(LocalDateTime.now());
        notification.setAmount(1);
        notification.setType(type);
        notification.setUserId(userId);

        INotificationRepository notificationRepository = Mockito.mock(INotificationRepository.class);
        Mockito.when(notificationRepository.findNotificationByUserIdAndType(userId, type)).thenReturn(null);

        boolean isAllowed = minuteRuleImpl.isAllowed(userId, type, notificationRepository);

        Assertions.assertThat(isAllowed).isTrue();
    }

    @Test
    void getInitialDate() {
        LocalDateTime date = minuteRuleImpl.getInitialDate();
        Assertions.assertThat(LocalDateTime.now()).isAfter(date);
        Assertions.assertThat(LocalDateTime.now().getMinute()).isEqualTo(date.getMinute());
    }

    @Test
    void getFinalDate() {
        LocalDateTime date = minuteRuleImpl.getFinalDate();
        Assertions.assertThat(LocalDateTime.now()).isBefore(date);
        Assertions.assertThat(LocalDateTime.now().getMinute()).isEqualTo(date.getMinute());
    }
}