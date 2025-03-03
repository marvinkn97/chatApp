package dev.marvin.domain.common;

import dev.marvin.domain.model.Chat;
import dev.marvin.domain.model.Message;
import dev.marvin.domain.model.User;
import dev.marvin.domain.response.ChatResponse;
import dev.marvin.domain.response.MessageResponse;
import dev.marvin.domain.response.UserResponse;
import dev.marvin.utils.FileUtils;
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
                .media(FileUtils.readFileFromLocation(message.getMediaFilePath()))
                .build();
    }

    public static UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .lastSeen(user.getLastSeen())
                .isOnline(user.isUserOnline())
                .build();
    }
}
