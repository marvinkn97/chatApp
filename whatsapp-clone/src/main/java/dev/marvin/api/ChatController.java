package dev.marvin.api;

import dev.marvin.business.ChatService;
import dev.marvin.domain.response.ChatResponse;
import dev.marvin.domain.response.ResponseDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/chats")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Chat Resource")
public class ChatController {
    private final ChatService chatService;

    @PostMapping
    public ResponseEntity<ResponseDTO<String>> createChat(@RequestParam(name = "senderId") String senderId, @RequestParam(name = "receiverId") String receiverId) {
        log.info("Inside createChat method of ChatController");
        final String chatId = chatService.createChat(senderId, receiverId);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ResponseDTO.<String>builder()
                        .message("Chat created successfully")
                        .data(chatId)
                        .build()
        );
    }

    @GetMapping
    public ResponseEntity<ResponseDTO<List<ChatResponse>>> getChatsByReceiver(Authentication authentication) {
        log.info("Inside getChatsByReceiver method of ChatController");
        List<ChatResponse> chats = chatService.getChatsByReceiverId(authentication);
        return ResponseEntity.ok(ResponseDTO.<List<ChatResponse>>builder()
                .data(chats)
                .build());
    }
}
