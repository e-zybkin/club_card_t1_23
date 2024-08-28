package develop.backend.Club_card.repository;

import develop.backend.Club_card.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findUserByEmail(String email);

    boolean existsUserByEmail(String username);

    void deleteUserByEmail(String email);
}