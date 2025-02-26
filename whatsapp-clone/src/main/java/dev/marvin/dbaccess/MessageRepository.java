package dev.marvin.dbaccess;

import dev.marvin.domain.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("SELECT m FROM Message WHERE m.chat.id = :chatId ORDER BY m.createdDate")
    List<Message> findByChatId(@Param("chatId") String id);

    @Modifying
    @Query("UPDATE Message m SET m.state = :newState WHERE m.chat.id = :chatId")
    void setMessagesToSeenByChat(@Param("state") String state, @Param("chatId") String id);
}
