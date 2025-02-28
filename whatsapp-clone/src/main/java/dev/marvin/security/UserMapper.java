package dev.marvin.security;

import dev.marvin.domain.model.User;

import java.time.LocalDateTime;
import java.util.Map;

public class UserMapper {
    public User fromTokenAttributes(User user, Map<String, Object> attributes) {
        user.setId((String) attributes.getOrDefault("sub", user.getId()));

        user.setFirstName((String) attributes.getOrDefault("given_name",
                attributes.getOrDefault("nickname", user.getFirstName())));

        user.setLastName((String) attributes.getOrDefault("family_name", user.getLastName()));

        user.setEmail((String) attributes.getOrDefault("email", user.getEmail()));

        user.setLastSeen(LocalDateTime.now());
        return user;
    }
}