package dev.marvin.api;

import dev.marvin.business.UserService;
import dev.marvin.domain.response.ResponseDTO;
import dev.marvin.domain.response.UserResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "User Resource")
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<ResponseDTO<List<UserResponse>>> getAllUsers(Authentication authentication) {
        List<UserResponse> users = userService.getAllUsersExceptSelf(authentication);
        return ResponseEntity.ok(ResponseDTO.<List<UserResponse>>builder()
                .data(users)
                .build());
    }
}
