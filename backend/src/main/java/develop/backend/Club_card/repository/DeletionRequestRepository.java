package develop.backend.Club_card.repository;

import develop.backend.Club_card.entity.DeletionRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeletionRequestRepository extends JpaRepository<DeletionRequest, Integer> {
}
