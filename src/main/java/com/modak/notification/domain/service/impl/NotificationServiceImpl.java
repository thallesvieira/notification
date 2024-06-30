package com.modak.notification.domain.service.impl;

import com.modak.notification.domain.exception.BadRequestException;
import com.modak.notification.domain.gateway.INotificationGateway;
import com.modak.notification.domain.model.Notification;
import com.modak.notification.domain.model.Type;
import com.modak.notification.domain.repository.INotificationRepository;
import com.modak.notification.domain.service.INotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/* Class that implements interface.
 * Responsible for handling notifications and redirecting to actions,
 * such as sending notification and saving.
 * This class only use interfaces to call methods from other directory.
 */
@Service
public class NotificationServiceImpl implements INotificationService {
    Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);

    @Autowired
    private INotificationGateway notificationGateway;

    @Autowired
    private INotificationRepository notificationRepository;

    /* This method receives information about permission to send a notification
    *  and call the method responsible to send it.
    *  Then, call the method responsible to check if is a new notification to save or update.
    */
    @Override
    public void send(Type type, String userId, String message) {
        try {
            boolean isAllowed = type.getNotificationRule().isAllowed(userId, type, notificationRepository);

            if (isAllowed) {
                notificationGateway.send(userId, message);
                checkIfSaveOrUpdate(type, userId, message);
            }
        } catch (Exception ex) {
            logger.error("Error to send a new notification with type: "+ type +
                    " to user: " + userId + ": " +ex.getMessage());
            throw new BadRequestException(ex.getMessage());
        }
    }

    /* This method call the repository to save the notification.
     */
    @Override
    public Notification saveNotification(Type type, String userId, String message) {
     try {
         return notificationRepository.saveNotification(type, userId, message);
    } catch (Exception ex) {
        logger.error("Error to save a new notification with type: "+ type +
                " to user: " + userId + ": " +ex.getMessage());
        throw new BadRequestException(ex.getMessage());
    }
    }

    /* This method call the repository to update the notification.
     */
    @Override
    public Notification updateNotification(Notification notification) {
        try {
            return notificationRepository.updateNotification(notification);
        } catch (Exception ex) {
            logger.error("Error to update a new notification with type: "+ notification.getType() +
                    " to user: " +notification.getUserId() + ": " +ex.getMessage());
            throw new BadRequestException(ex.getMessage());
        }
    }

    /* This private method check if is a new notification or already exist.
     */
    private void checkIfSaveOrUpdate(Type type, String userId, String message) {
        try {
            List<Notification> notifications = notificationRepository.findNotificationByUserIdAndType(userId, type);
            if (notifications == null || notifications.isEmpty())
                saveNotification(type, userId, message);
            else {
                if (notifications.stream().count() > 1)
                    notifications.sort((n1, n2) -> n2.getDate().compareTo(n1.getDate()));

                Notification notification = notifications.stream().findFirst().get();
                notification.setLastMessage(message);
                updateNotification(notification);
            }
        } catch (Exception ex) {
            logger.error("Error to filter notifications with type: "+ type +
                    " to user: " + userId + ": " +ex.getMessage());
            throw new BadRequestException(ex.getMessage());
        }
    }
}
