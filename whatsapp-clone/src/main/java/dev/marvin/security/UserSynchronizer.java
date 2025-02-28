package dev.marvin.security;

import dev.marvin.dbaccess.UserRepository;
import dev.marvin.domain.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserSynchronizer {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public void synchronizeWithIDP(Jwt jwt) {
        log.info("Synchronizing user with idp");
        String email = jwt.getClaims().get("email").toString();
        if (StringUtils.hasText(email)) {
            log.info("Synchronizing user having email: {}", email);
            Optional<User> optionalUser = userRepository.findByEmail(email);
            User user = optionalUser.orElseGet(User::new);
            userRepository.save(userMapper.fromTokenAttributes(user, jwt.getClaims()));
        }

    }
}
