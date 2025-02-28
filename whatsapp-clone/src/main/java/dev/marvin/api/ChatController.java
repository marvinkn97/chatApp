package dev.marvin.api;

import dev.marvin.business.ChatService;
import dev.marvin.domain.response.ChatResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/chats")
@RequiredArgsConstructor
@Slf4j
public class ChatController {
    private final ChatService chatService;

    @PostMapping
    public ResponseEntity<Object> createChat(@RequestParam(name = "senderId") String senderId, @RequestParam(name = "receiverId") String receiverId) {
        final String chatId = chatService.createChat(senderId, receiverId);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("response", chatId));
    }

    @GetMapping
    public ResponseEntity<List<ChatResponse>> getChatsByReceiver(Authentication authentication) {
        return ResponseEntity.ok(chatService.getChatsByReceiverId(authentication));
    }
}
