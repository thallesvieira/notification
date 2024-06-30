package com.modak.notification.resource.persistence.repository;

import com.modak.notification.domain.model.Type;
import com.modak.notification.resource.persistence.entity.NotificationEntity;
import org.aspectj.weaver.ast.Not;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

/* Interface that extends JPA for handling entity methods
 */
public interface INotificationJpaRepository extends JpaRepository<NotificationEntity, Long> {
    List<NotificationEntity> findAllByUserIdAndTypeAndDateBetween(
            String userId,
            Type type,
            LocalDateTime initialDate,
            LocalDateTime finalDate);
}
