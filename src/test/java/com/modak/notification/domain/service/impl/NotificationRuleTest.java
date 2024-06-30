package com.modak.notification.domain.service.impl;

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

@ExtendWith(MockitoExtension.class)
class NotificationRuleTest {

    @InjectMocks
    private NotificationRule dayRule = new DayRuleImpl();

    @Mock
    INotificationRepository notificationRepository;

    @Test
    void isAllowed() {
        String userId = "userId";
        Type type = Type.NEWS;

        Notification notification = new Notification();
        notification.setDate(LocalDateTime.now());
        notification.setAmount(2);
        notification.setType(type);
        notification.setUserId(userId);

        List<Notification> notifications = Arrays.asList(notification);

        Mockito.when(notificationRepository.findNotificationByUserIdAndType(userId, type)).thenReturn(notifications);

        boolean isAllowed = dayRule.isAllowed(userId, type);

        Assertions.assertThat(isAllowed).isFalse();
    }

}