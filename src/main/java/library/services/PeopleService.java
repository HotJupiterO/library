package library.services;

import library.models.Book;
import library.models.Person;
import library.repositories.PeopleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class PeopleService {

    private final PeopleRepository peopleRepository;


    public List<Person> findAll() {
        return peopleRepository.findAll();
    }

    public Person show(int id) {
        return peopleRepository.findById(id).orElse(null);
    }

    public Optional<Person> show(String email) {
        return peopleRepository.findAll()
                .stream()
                .filter(person -> Objects.equals(person.getEmail(), email))
                .findAny();
    }


    @Transactional
    public void save(Person person) {
        peopleRepository.save(person);
    }

    @Transactional
    public void update(int id, Person updatedPerson) {
        updatedPerson.setPerson_id(id);
        peopleRepository.save(updatedPerson);
    }

    @Transactional
    public void delete(int id) {
        peopleRepository.deleteById(id);
    }

    public List<Book> takenBooks(int id) {
        Person person = show(id);
        return person == null ? null : person.getBooks();
    }
}