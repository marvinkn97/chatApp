package dev.marvin.business;


import dev.marvin.domain.model.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public void sendNotification(String userId, Notification notification) {
        log.info("Sending WS notification to {} with payload {}", userId, notification);
        simpMessagingTemplate.convertAndSendToUser(userId, "/chat", notification);
    }
}
