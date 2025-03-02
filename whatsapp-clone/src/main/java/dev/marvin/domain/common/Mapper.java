package dev.marvin.domain.common;

import dev.marvin.domain.model.Chat;
import dev.marvin.domain.model.Message;
import dev.marvin.domain.response.ChatResponse;
import dev.marvin.domain.response.MessageResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class Mapper {
    public ChatResponse toResponse(Chat chat, String senderId) {
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

    public static MessageResponse toResponse(Message message) {
        return MessageResponse.builder()
                .id(message.getId())
                .content(message.getContent())
                .messageType(message.getType())
                .senderId(message.getSenderId())
                .receiverId(message.getReceiverId())
                .createdAt(LocalDateTime.now())
                //TODO: read the media file
                .build();
    }
}
