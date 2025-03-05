package dev.marvin.business;

import dev.marvin.domain.model.Notification;

public interface NotificationService {
    void sendNotification(String userId, Notification notification);
}
