package develop.backend.Club_card.repositories;

import develop.backend.Club_card.models.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Integer> {
}
