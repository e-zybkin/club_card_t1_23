package develop.backend.Club_card.repository;

import develop.backend.Club_card.entity.DeletionRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeletionRequestRepository extends JpaRepository<DeletionRequest, Integer> {

    Optional<DeletionRequest> findByUsername(String username);
}
