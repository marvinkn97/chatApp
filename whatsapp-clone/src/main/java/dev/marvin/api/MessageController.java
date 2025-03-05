package dev.marvin.api;

import dev.marvin.business.MessageService;
import dev.marvin.domain.request.MessageRequest;
import dev.marvin.domain.response.MessageResponse;
import dev.marvin.domain.response.ResponseDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/messages")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Message Resource")
public class MessageController {
    private final MessageService messageService;

    @PostMapping
    public ResponseEntity<ResponseDTO<Void>> saveMessage(MessageRequest messageRequest) {
        messageService.saveMessage(messageRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseDTO.<Void>builder()
                        .build());
    }

    @PostMapping(value = "/upload-media", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseDTO<Void>> uploadMedia(@RequestParam("chatId") String chatId, @RequestPart("file") MultipartFile file, Authentication authentication) {
        messageService.uploadMediaMessage(chatId, file, authentication);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseDTO.<Void>builder()
                        .build());
    }

    @PatchMapping
    public ResponseEntity<ResponseDTO<Void>> setMessagesToSeen(@RequestParam("chatId") String chatId, Authentication authentication) {
        messageService.setMessagesToSeen(chatId, authentication);
        return ResponseEntity.ok(ResponseDTO.<Void>builder()
                .build());
    }

    @GetMapping("/chats/{chatId}")
    public ResponseEntity<ResponseDTO<List<MessageResponse>>> getMessages(@PathVariable("chatId") String chatId) {
        List<MessageResponse> messages = messageService.findChatMessages(chatId);
        return ResponseEntity.ok(ResponseDTO.<List<MessageResponse>>builder()
                .data(messages)
                .build());
    }

}
