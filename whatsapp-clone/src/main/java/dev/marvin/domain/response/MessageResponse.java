package dev.marvin.domain.response;

import dev.marvin.domain.constants.MessageState;
import dev.marvin.domain.constants.MessageType;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MessageResponse {
    private Long id;
    private String content;
    private MessageType messageType;
    private MessageState messageState;
    private String senderId;
    private String receiverId;
    private LocalDateTime createdAt;
    private byte[] media;
}
