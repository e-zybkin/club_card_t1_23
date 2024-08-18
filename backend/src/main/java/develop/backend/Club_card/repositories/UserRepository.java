package develop.backend.Club_card.repositories;

import develop.backend.Club_card.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findUserByUsername(String username);

    boolean existsUserByUsername(String username);
}