package com.modak.notification.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/* This class is used to work with Notification model,
 * avoiding the domain directory access the entity package,
 * which is in the resource directory.
 */
@Getter
@Setter
public class Notification {
    Long id;
    String userId;
    String lastMessage;
    LocalDateTime date;
    Type type;
    Integer amount;
}
