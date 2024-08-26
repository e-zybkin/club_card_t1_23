package develop.backend.Club_card.repository;

import develop.backend.Club_card.entity.ArchivedUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArchivedUserRepository extends JpaRepository<ArchivedUser, Integer> {

    void deleteArchivedUserByUsername(String username);

    void deleteArchivedUserByEmail(String email);

    boolean existsArchivedUserByUsername(String username);

    boolean existsArchivedUserByEmail(String email);
}
