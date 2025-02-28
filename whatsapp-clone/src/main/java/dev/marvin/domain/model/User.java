package dev.marvin.domain.model;

import dev.marvin.domain.common.BaseAuditingEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends BaseAuditingEntity {
    private static final int LAST_ACTIVE_INTERVAL = 5;
    @Id
    private String id;   ////keycloak id
    private String firstName;
    private String lastName;
    private String email;
    private LocalDateTime lastSeen;

    @OneToMany(mappedBy = "sender")
    private List<Chat> chatsAsSender;

    @OneToMany(mappedBy = "recipient")
    private List<Chat> chatsAsRecipient;

    @Transient
    public boolean isUserOnline(){
        return lastSeen != null && lastSeen.isAfter(LocalDateTime.now().minusMinutes(LAST_ACTIVE_INTERVAL));
    }
}
