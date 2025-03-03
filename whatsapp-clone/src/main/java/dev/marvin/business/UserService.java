package dev.marvin.business;

import dev.marvin.domain.response.UserResponse;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface UserService {
    List<UserResponse> getAllUsersExceptSelf(Authentication authentication);
}
