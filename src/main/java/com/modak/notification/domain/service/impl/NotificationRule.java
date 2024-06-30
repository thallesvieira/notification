package com.modak.notification.domain.service.impl;

import com.modak.notification.domain.exception.BadRequestException;
import com.modak.notification.domain.model.Notification;
import com.modak.notification.domain.model.Type;
import com.modak.notification.domain.repository.INotificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/* Abstract class to combine generic methods
 * and avoid being replicated in subclasses unnecessarily.
 */
public abstract class NotificationRule {

    Logger logger = LoggerFactory.getLogger(NotificationRule.class);
    private INotificationRepository notificationRepository;

    /* Method to instantiate repository, receiving it from subclass*/
    protected void setNotificationRepository(INotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    /* Method responsible to get the notifications
     * and to check whether is allowed send a new notification to same user and with same type.
     * If there is no exist notification, return true
     * If there is notification, then add up the number of notifications sent for the same date
     * and return is allowed to send a new notification.
     * There is the possibility of having more than one notification,
     * for example, if the rule changes: there were 3 notifications per hour
     * and now there are 3 per day,
     * when the filter happens it will return a registration list in this case,
     * so there is the sum of the amount sent.
     */
    public Boolean isAllowed(String userId, Type type) {
        try {
            List<Notification> notifications = notificationRepository.findNotificationByUserIdAndType(userId, type);

            if (notifications == null)
                return true;

            int amountSum = notifications.stream().reduce(0, (amountResult, n2) ->
                    amountResult + n2.getAmount(), Integer::sum);

            return amountSum < type.getAmount();
        } catch (Exception ex) {
            logger.error("Error to filter notifications with type: "+ type +
                " to user: " + userId + ": " +ex.getMessage());

            throw new BadRequestException(ex.getMessage());
        }
    }
}
