package develop.backend.Club_card.repository;

import develop.backend.Club_card.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Integer> {


}
