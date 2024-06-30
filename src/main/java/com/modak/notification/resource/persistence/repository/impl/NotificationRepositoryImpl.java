package com.modak.notification.resource.persistence.repository.impl;

import com.modak.notification.domain.exception.BadRequestException;
import com.modak.notification.domain.model.Notification;
import com.modak.notification.domain.model.Type;
import com.modak.notification.domain.repository.INotificationRepository;
import com.modak.notification.domain.service.INotificationRule;
import com.modak.notification.resource.persistence.entity.NotificationEntity;
import com.modak.notification.resource.persistence.repository.INotificationJpaRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/* Repository responsible for handling notifications with the database.
 * The h2 database was used to save, update and query data.
 *
 * There were two options to be made here, depending on the company's needs.
 * One option saves all records in the database,
 * so it is possible to have a record of all notifications sent,
 * however, the cost is very high for saving so many records in the database.

 * The other option, which I chose, was to group notification records.
 * In this case, there will not be all notifications in the database,
 * only the last one, with the quantity already sent to a specific customer,
 * with a specific type and specific time.
 * In this case the cost is lower, because fewer records will be saved in the database.
 */
@Repository
public class NotificationRepositoryImpl implements INotificationRepository {

    private static final Logger logger = LoggerFactory.getLogger(NotificationRepositoryImpl.class);

    @Autowired
    private INotificationJpaRepository notificationJpaRepository;

    private ModelMapper mapper = new ModelMapper();

    /* Method responsible for save a new entity.
     */
    @Override
    public Notification saveNotification(Type type, String userId, String message) {
        try {
            NotificationEntity notificationEntity = new NotificationEntity();

            notificationEntity.setLastMessage(message);
            notificationEntity.setDate(LocalDateTime.now());
            notificationEntity.setType(type);
            notificationEntity.setUserId(userId);
            notificationEntity.setAmount(1);

            NotificationEntity savedNotification = notificationJpaRepository.save(notificationEntity);
            return mapper.map(savedNotification, Notification.class);
        } catch (Exception ex) {
            logger.error("Error to save a new notification with type: "+ type +
                    " to user: " + userId + ": " +ex.getMessage());
            throw new BadRequestException(ex.getMessage());
        }
    }

    /* Method responsible for save a new entity.
     * Saving a new amount of notifications and the last message.
     */
    @Override
    public Notification updateNotification(Notification notification) {
        try {
           NotificationEntity notificationEntity = mapper.map(notification, NotificationEntity.class);

            notificationEntity.setAmount(notificationEntity.getAmount() + 1);
            notificationEntity.setDate(LocalDateTime.now());

            return mapper.map(notificationJpaRepository.save(notificationEntity), Notification.class);
        } catch (Exception ex) {
            logger.error("Error to update a notification with type: "+ notification.getType() +
                " to user: " + notification.getUserId() + ": " +ex.getMessage());
            throw new BadRequestException(ex.getMessage());
        }
    }

    /* Method to find all notification depending on filters
     */
    @Override
    public List<Notification> findNotificationByUserIdAndType(String userId, Type type) {
        try {
            List<NotificationEntity> notifications = findAllByUserIdAndTypeAndDateBetween(userId, type);
            if (!notifications.isEmpty())
                return notifications.stream().map(n -> mapper.map(n, Notification.class)).toList();

            return null;
        } catch (Exception ex) {
            logger.error("Error to find notifications with type: "+ type +
                    " to user: " + userId + ": " +ex.getMessage());
            throw new BadRequestException(ex.getMessage());
        }
    }

    /* Private method to find all notification depending on filters,
     * but in this case, the method work with entity.
     */
    private List<NotificationEntity> findAllByUserIdAndTypeAndDateBetween(String userId, Type type) {
        try {
            INotificationRule notificationRule = type.getNotificationRule();

            LocalDateTime initialDate = notificationRule.getInitialDate();
            LocalDateTime finalDate = notificationRule.getFinalDate();

            return notificationJpaRepository.findAllByUserIdAndTypeAndDateBetween(userId, type, initialDate, finalDate);
        } catch (Exception ex) {
            logger.error("Error to find notifications in repository with type: "+ type +
                    " to user: " + userId + ": " +ex.getMessage());
            throw new BadRequestException(ex.getMessage());
        }
    }
}
