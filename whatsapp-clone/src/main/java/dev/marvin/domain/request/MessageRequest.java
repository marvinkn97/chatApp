package dev.marvin.domain.request;

import dev.marvin.domain.constants.MessageType;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MessageRequest {
    private String content;
    private String senderId;
    private String receiverId;
    private MessageType messageType;
    private String chatId;
}
