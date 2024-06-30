package com.modak.notification.domain.service;

import com.modak.notification.domain.model.Notification;
import com.modak.notification.domain.model.Type;

import java.util.List;

/* Interface created to respect patterns,
 * force classes the implements methods
 * and reduce coupling
 */
public interface INotificationService {
    void send(Type type, String userId, String message);
    Notification saveNotification(Type type, String userId, String message);
    Notification updateNotification(Notification notification);
}
