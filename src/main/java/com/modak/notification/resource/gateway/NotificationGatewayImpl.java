package com.modak.notification.resource.gateway;

import com.modak.notification.domain.gateway.INotificationGateway;
import org.springframework.stereotype.Service;

/* Class responsible for notifications methods.
 */
@Service
public class NotificationGatewayImpl implements INotificationGateway {

    /* Method responsible to send a notification.
     */
    @Override
    public void send(String userId, String message) {
        System.out.println("sending message: "+message+" to user " + userId);
    }
}
