package com.modak.notification.resource.persistence.entity;

import com.modak.notification.domain.model.Type;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

/* Entity notification there is saved in database.
 */
@Entity
@Table(name = "notification")
@Getter
@Setter
public class NotificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;
    @Column(name = "user_id")
    String userId;
    @Column(name = "last_message")
    String lastMessage;
    @Column(name = "date")
    LocalDateTime date;
    @Column(name = "type")
    Type type;
    @Column(name = "amount")
    Integer amount;
}
