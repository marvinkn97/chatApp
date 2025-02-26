package dev.marvin.dbaccess;

import dev.marvin.domain.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, String> {
    @Query("SELECT DISTINCT c FROM Chat c WHERE c.sender.id = :senderId OR c.recipient.id = :senderId ORDER BY createdDate DESC")
    Optional<Chat> findChatBySenderId(@Param("senderId") String id);

    @Query("SELECT DISTINCT c FROM Chat c WHERE (c.sender.id = :senderId AND c.recipient.id = :recipientId) OR (c.sender.id = :recipientId AND c.recipient.id = :senderId) ORDER BY createdDate DESC")
    Optional<Chat> findChatBySenderIdAndReceiverId(@Param("senderId") String senderId, @Param("recipientId") String recipientId);
}
