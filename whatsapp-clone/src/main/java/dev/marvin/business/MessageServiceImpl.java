package dev.marvin.business;

import dev.marvin.dbaccess.ChatRepository;
import dev.marvin.dbaccess.MessageRepository;
import dev.marvin.domain.common.Mapper;
import dev.marvin.domain.constants.MessageState;
import dev.marvin.domain.constants.MessageType;
import dev.marvin.domain.model.Chat;
import dev.marvin.domain.model.Message;
import dev.marvin.domain.request.MessageRequest;
import dev.marvin.domain.response.MessageResponse;
import dev.marvin.errorhandling.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;
    private final FileService fileService;

    @Transactional
    @Override
    public void saveMessage(MessageRequest messageRequest) {
        log.info("Inside saveMessage method of MessageServiceImpl");
        Chat chat = chatRepository.findById(messageRequest.getChatId())
                .orElseThrow(() -> new ResourceNotFoundException("Chat not found"));

        Message message = new Message();
        message.setContent(message.getContent());
        message.setChat(chat);
        message.setSenderId(messageRequest.getSenderId());
        message.setReceiverId(messageRequest.getReceiverId());
        message.setType(messageRequest.getMessageType());
        message.setState(MessageState.SENT);

        messageRepository.save(message);

        //TODO: Notification
    }

    @Transactional(readOnly = true)
    @Override
    public List<MessageResponse> findChatMessages(String chatId) {
        log.info("Inside findChatMessages method of MessageServiceImpl");
        return messageRepository.findByChatId(chatId)
                .stream()
                .map(Mapper::toResponse)
                .toList();
    }

    @Transactional
    @Override
    public void setMessagesToSeen(String chatId, Authentication authentication) {
        log.info("Inside setMessagesToSeen method of MessageServiceImpl");
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new ResourceNotFoundException("Chat not found"));

        final String recipientId = getRecipientId(chat, authentication);

        messageRepository.setMessagesToSeenByChat(MessageState.SEEN.name(), recipientId);

        //TODO: notification
    }

    @Transactional
    @Override
    public void uploadMediaMessage(String chatId, MultipartFile file, Authentication authentication) {
        log.info("Inside uploadMediaMessage method of MessageServiceImpl");
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new ResourceNotFoundException("Chat not found"));

        final String senderId = getSenderId(chat, authentication);
        final String recipientId = getRecipientId(chat, authentication);

        final String filePath = fileService.saveFile(file, senderId);

        Message message = new Message();
        message.setChat(chat);
        message.setSenderId(senderId);
        message.setReceiverId(recipientId);
        message.setType(MessageType.IMAGE);
        message.setState(MessageState.SENT);
        message.setMediaFilePath(filePath);

        messageRepository.save(message);

        //TODO: notification
    }

    private String getRecipientId(Chat chat, Authentication authentication) {
        if (chat.getSender().getId().equals(authentication.getName())) {
            return chat.getRecipient().getId();
        }
        return chat.getSender().getId();
    }

    private String getSenderId(Chat chat, Authentication authentication) {
        if (chat.getSender().getId().equals(authentication.getName())) {
            return chat.getSender().getId();
        }
        return chat.getRecipient().getId();
    }

}
