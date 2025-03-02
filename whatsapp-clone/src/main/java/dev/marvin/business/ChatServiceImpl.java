package dev.marvin.business;

import dev.marvin.dbaccess.ChatRepository;
import dev.marvin.dbaccess.UserRepository;
import dev.marvin.domain.common.Mapper;
import dev.marvin.domain.model.Chat;
import dev.marvin.domain.model.User;
import dev.marvin.domain.response.ChatResponse;
import dev.marvin.errorhandling.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatServiceImpl implements ChatService {
    private final ChatRepository chatRepository;
    private final Mapper mapper;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    @Override
    public List<ChatResponse> getChatsByReceiverId(Authentication currentUser) {
        log.info("Inside getChatsByReceiverId method of ChatServiceImpl");
        final String userId = currentUser.getName();
        return chatRepository.findChatBySenderId(userId).stream()
                .map(chat -> mapper.toResponse(chat, userId))
                .toList();
    }

    @Transactional
    @Override
    public String createChat(String senderId, String receiverId) {
        log.info("Inside createChat method of ChatServiceImpl");
        Optional<Chat> existingChat = chatRepository.findChatBySenderIdAndReceiverId(senderId, receiverId);
        if (existingChat.isPresent()) {
            return existingChat.get().getId();
        }
        User sender = userRepository.findByPublicId(senderId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id %s not found".formatted(senderId)));

        User receiver = userRepository.findByPublicId(receiverId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id %s not found".formatted(receiverId)));

        Chat chat = new Chat();
        chat.setSender(sender);
        chat.setRecipient(receiver);
        Chat savedChat = chatRepository.save(chat);
        return savedChat.getId();
    }
}
