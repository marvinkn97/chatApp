package dev.marvin.dbaccess;

import dev.marvin.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmail(@Param("email") String email);

    @Query("SELECT u FROM User u WHERE u.id != :publicId")
    List<User> findAllUsersExceptSelf(@Param("publicId") String id);

    @Query("SELECT u FROM User u WHERE u.id = :publicId")
    Optional<User> findByPublicId(@Param("publicId") String id);
}
