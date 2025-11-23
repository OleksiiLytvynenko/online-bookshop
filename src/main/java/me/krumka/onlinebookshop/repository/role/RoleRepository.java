package me.krumka.onlinebookshop.repository.role;

import java.util.Optional;
import me.krumka.onlinebookshop.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(Role.RoleName name);
}
