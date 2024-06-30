package com.modak.notification.domain.service.impl;

import com.modak.notification.domain.model.Type;
import com.modak.notification.domain.repository.INotificationRepository;
import com.modak.notification.domain.service.INotificationRule;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/* This class is specific to implement the rules regarding day.
 * Extends of the abstract class and implement the interface.
 */
@Service
public class DayRuleImpl extends NotificationRule implements INotificationRule {

    public DayRuleImpl() {
        super();
    }

    /* As it is a class instantiated without using the @Autowired annotation,
     * it was necessary to receive the repository already instantiated,
     * which is passed on to the abstract class.
     * This method sends the repository to the abstract class and uses the class's generic method.
     * If it is necessary to implement a specific rule,
     * just implement it here and do not call the abstract class method.
     */
    @Override
    public Boolean isAllowed(String userId, Type type, INotificationRepository notificationRepository) {
        setNotificationRepository(notificationRepository);
        return super.isAllowed(userId, type);
    }

    /* This method is necessary to get the initial date of this rule,
     * where it will be used to find the notification
     */
    @Override
    public LocalDateTime getInitialDate() {
        return LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
    }

    /* This method is necessary to get the final date of this rule,
     * where it will be used to find the notification
     */
    @Override
    public LocalDateTime getFinalDate() {
        return LocalDateTime.now().withHour(23).withMinute(59).withSecond(59);
    }
}
