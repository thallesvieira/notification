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
class HourRuleImplTest {

    private HourRuleImpl hourRuleImpl = new HourRuleImpl();

    @Test
    void isNotAllowedWhenAmountIsEqualsRule() {
        String userId = "userId";
        Type type = Type.CONFIRMATION;

        Notification notification = new Notification();
        notification.setDate(LocalDateTime.now());
        notification.setAmount(2);
        notification.setType(type);
        notification.setUserId(userId);

        List<Notification> notifications = Arrays.asList(notification);

        INotificationRepository notificationRepository = Mockito.mock(INotificationRepository.class);
        Mockito.when(notificationRepository.findNotificationByUserIdAndType(userId, type)).thenReturn(notifications);

        boolean isAllowed = hourRuleImpl.isAllowed(userId, type, notificationRepository);

        Assertions.assertThat(isAllowed).isFalse();
    }

    @Test
    void isAllowedWhenThereIsLessThanRule() {
        String userId = "userId";
        Type type = Type.CONFIRMATION;

        Notification notification = new Notification();
        notification.setDate(LocalDateTime.now());
        notification.setAmount(1);
        notification.setType(type);
        notification.setUserId(userId);

        INotificationRepository notificationRepository = Mockito.mock(INotificationRepository.class);
        Mockito.when(notificationRepository.findNotificationByUserIdAndType(userId, type)).thenReturn(null);

        boolean isAllowed = hourRuleImpl.isAllowed(userId, type, notificationRepository);

        Assertions.assertThat(isAllowed).isTrue();
    }

    @Test
    void getInitialDate() {
        LocalDateTime date = hourRuleImpl.getInitialDate();
        Assertions.assertThat(LocalDateTime.now()).isAfter(date);
        Assertions.assertThat(LocalDateTime.now().getHour()).isEqualTo(date.getHour());
    }

    @Test
    void getFinalDate() {
        LocalDateTime date = hourRuleImpl.getFinalDate();
        Assertions.assertThat(LocalDateTime.now()).isBefore(date);
        Assertions.assertThat(LocalDateTime.now().getHour()).isEqualTo(date.getHour());
    }
}