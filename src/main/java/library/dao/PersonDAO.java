package library.dao;


import library.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import library.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;


    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Person> index() {
        return jdbcTemplate.query("select * from person",
                new BeanPropertyRowMapper<>(Person.class));
    }

    public Optional<Person> show(String email) {
        return jdbcTemplate.query("select * from person where email=?", new Object[]{email},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }

    public Person show(int id) {
        return jdbcTemplate.query("select * from person where person_id=?", new Object[]{id},
                        new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }

    public void save(Person person) {
        jdbcTemplate.update("insert into person(name,  email) values (?, ?)",
                person.getName(), person.getEmail());
    }

    public void update(int id, Person updatedPerson) {
        jdbcTemplate.update("update person set name=?, email=? where person_id=?",
                updatedPerson.getName(), updatedPerson.getEmail(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("delete from person where person_id=?", id);
    }

    public List<Book> takenBooks(int id) {
        List<Book> books = jdbcTemplate.query("select * from book where person_id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Book.class));
        return books;
    }
}
