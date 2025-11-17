package me.krumka.onlinebookshop.repository.user;

import me.krumka.onlinebookshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
}
