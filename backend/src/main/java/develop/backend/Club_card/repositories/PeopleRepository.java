package develop.backend.Club_card.repositories;

import develop.backend.Club_card.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
    Optional<Person> findByName(String name);

    Integer getUserIdByName(String name);
}