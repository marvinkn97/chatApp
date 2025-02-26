package dev.marvin.business;

import dev.marvin.dbaccess.ChatRepository;
import dev.marvin.domain.common.Mapper;
import dev.marvin.domain.response.ChatResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ChatServiceImpl implements ChatService {
    private final ChatRepository chatRepository;
    private final Mapper mapper;

    @Override
    public List<ChatResponse> getChatsByReceiverId(Authentication currentUser) {
        final String userId = currentUser.getName();
        return chatRepository.findChatBySenderId(userId).stream()
                .map(chat -> mapper.toResponse(chat, userId))
                .toList();
    }
}
