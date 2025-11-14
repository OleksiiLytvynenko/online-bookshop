package me.krumka.onlinebookshop.repository.user;

import java.util.Optional;
import me.krumka.onlinebookshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
