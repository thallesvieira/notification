package com.modak.notification.domain.service;

import com.modak.notification.domain.model.Type;
import com.modak.notification.domain.repository.INotificationRepository;

import java.time.LocalDateTime;

/* Interface created to respect patterns,
 * force classes the implements methods
 * and reduce coupling
 */
public interface INotificationRule {
    Boolean isAllowed(String userId, Type type, INotificationRepository notificationRepository);
    LocalDateTime getInitialDate();
    LocalDateTime getFinalDate();
}
