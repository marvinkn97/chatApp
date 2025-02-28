package dev.marvin.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class CustomJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    @Override
    public AbstractAuthenticationToken convert(@NonNull Jwt source) {
        return new JwtAuthenticationToken(source, Stream.concat(
                new JwtGrantedAuthoritiesConverter().convert(source).stream(), // gets roles for the entire realm
                extractResourceClaims(source).stream()  // get roles for specific client app
        ).collect(Collectors.toSet()));
    }

    private Collection<? extends GrantedAuthority> extractResourceClaims(Jwt jwt) {
        var resourceAccess = jwt.getClaimAsMap("resource_access");
        var internal = (Map<String, List<String>>) resourceAccess.get("account");  // represents keycloak account management / self service account management
        var roles = internal.get("roles");
        return roles.stream()
                .map(role -> "ROLE_" + role.replace("-", "_"))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }
}
