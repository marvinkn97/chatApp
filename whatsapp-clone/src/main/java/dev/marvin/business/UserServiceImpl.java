package dev.marvin.business;

import dev.marvin.dbaccess.UserRepository;
import dev.marvin.domain.common.Mapper;
import dev.marvin.domain.response.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    @Override
    public List<UserResponse> getAllUsersExceptSelf(Authentication authentication) {
        return userRepository.findAllUsersExceptSelf(authentication.getName())
                .stream()
                .map(Mapper::toResponse)
                .toList();
    }
}
