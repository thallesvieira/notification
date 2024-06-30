package com.modak.notification.domain.model;

import com.modak.notification.domain.service.INotificationRule;
import com.modak.notification.domain.service.impl.DayRuleImpl;
import com.modak.notification.domain.service.impl.HourRuleImpl;
import com.modak.notification.domain.service.impl.MinuteRuleImpl;
import lombok.Getter;

/* Enum implemented to register possibles types of e-mails.
 * Using the principle Open-Closed the code becomes more flexible to create a new rule
 * or even change the existing one. This allows for cleaner, more maintainable code.
 */
public enum Type {
    STATUS("Status", 2, new MinuteRuleImpl()),
    NEWS("News", 1, new DayRuleImpl()),
    MARKETING("Marketing", 3, new HourRuleImpl()),
    UPDATE("Update", 2, new DayRuleImpl()),
    CONFIRMATION("Confirmation", 2, new HourRuleImpl());

    @Getter
    INotificationRule notificationRule;
    @Getter
    int amount;

    Type(String name, int amount, INotificationRule notificationRule) {
        this.notificationRule = notificationRule;
        this.amount = amount;
    }
}
