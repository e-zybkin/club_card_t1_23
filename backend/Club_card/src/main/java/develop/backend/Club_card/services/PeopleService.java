package develop.backend.Club_card.services;

import develop.backend.Club_card.models.Person;
import develop.backend.Club_card.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PeopleService {

    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @Transactional(readOnly = true)
    public List<Person> findAll(){
        return peopleRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Person findOne(int id){
        return peopleRepository.findById(id).orElse(null);
    }

    public void save(Person person){
        peopleRepository.save(person);
    }

    public void update(int id, Person updatedPerson){
        updatedPerson.setId(id);
        peopleRepository.save(updatedPerson);
    }

    public void delete(int id){
        peopleRepository.deleteById(id);
    }


    public Optional<Person> findByName(String name){
        return peopleRepository.findByName(name);
    }

    public Integer getUserIdByName(String name){
        Optional<Person> personOptional = findByName(name);
        if (personOptional.isPresent()) {
            Person person = personOptional.get();
            return person.getId();
        } else {
            return null;
        }
    }

    public String getUserRole(String name) {
        Optional<Person> personOptional = findByName(name);
        if (personOptional.isPresent()) {
            Person person = personOptional.get();
            return person.getRole();
        } else {
            return "ROLE_NOT_FOUND";
        }
    }
}