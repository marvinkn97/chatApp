package dev.marvin.business;

import dev.marvin.domain.request.MessageRequest;
import dev.marvin.domain.response.MessageResponse;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MessageService {
    void saveMessage(MessageRequest messageRequest);
    List<MessageResponse> findChatMessages(String chatId);
    void setMessagesToSeen(String chatId, Authentication authentication);
    void uploadMediaMessage(String chatId, MultipartFile file, Authentication authentication);
}
