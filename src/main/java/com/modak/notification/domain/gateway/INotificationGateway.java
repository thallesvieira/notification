package com.modak.notification.domain.gateway;

/* Interface created to respect patterns,
 * force classes the implements methods
 * and reduce coupling
 */
public interface INotificationGateway {
    void send(String userId, String message);
}
