package dev.marvin.security;

import dev.marvin.dbaccess.UserRepository;
import dev.marvin.domain.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserSynchronizer {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public void synchronizeWithIDP(Jwt jwt) {
        log.info("Synchronizing user with idp");
        getUserEmail(jwt).ifPresent(email -> {
            log.info("Synchronizing user having email: {}", email);
            //Optional<User> optionalUser = userRepository.findByEmail(email);
            User user = userMapper.fromTokenAttributes(jwt.getClaims());
            //optionalUser.ifPresent(value -> user.setId(optionalUser.get().getId()));
            userRepository.save(user);
        });
    }


    private Optional<String> getUserEmail(Jwt jwt) {
        Map<String, Object> attributes = jwt.getClaims();
        if (attributes.containsKey("email")) {
            return Optional.of(attributes.get("email").toString());
        }
        return Optional.empty();
    }
}
