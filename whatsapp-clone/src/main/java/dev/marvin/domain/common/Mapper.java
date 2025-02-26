package dev.marvin.domain.common;

import dev.marvin.domain.model.Chat;
import dev.marvin.domain.response.ChatResponse;
import org.springframework.stereotype.Component;

@Component
public class Mapper {
    public ChatResponse toResponse(Chat chat, String senderId){
        return ChatResponse.builder()
                .id(chat.getId())
                .name(chat.getChatName(senderId))
                .unreadCount(chat.getUnreadMessages(senderId))
                .lastMessage(chat.getLastMessage())
                .isRecipientOnline(chat.getRecipient().isUserOnline())
                .senderId(chat.getSender().getId())
                .receiverId(chat.getRecipient().getId())
                .build();
    }
}
