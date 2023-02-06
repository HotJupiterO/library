package library.dao;


import library.models.Book;
import library.models.Person;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class BookDAO {

    private final JdbcTemplate jdbcTemplate;


    public List<Book> index() {
        return jdbcTemplate.query("select * from book",
                new BeanPropertyRowMapper<>(Book.class));
    }

    public void save(Book book) {
        jdbcTemplate.update("insert into book (name, author, year) values (?,?,?)",
                book.getName(), book.getAuthor(), book.getYear());
    }

    public void update(int id, Book updatedBook) {
        jdbcTemplate.update("update book set name=?, author=?, year=? where book_id=?",
                updatedBook.getName(), updatedBook.getAuthor(), updatedBook.getYear(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("delete FROM book where book_id=?", id);
    }

    public Book show(int id) {
        return jdbcTemplate.query("select * from book where book_id=?",
                        new Object[]{id}, new BeanPropertyRowMapper<>(Book.class))
                .stream()
                .findAny()
                .orElse(null);
    }

    public boolean isTaken(int id) {
        return !jdbcTemplate.query("select * from book where book_id=? and person_id is not null ",
                new Object[]{id}, new BeanPropertyRowMapper<>(Book.class)).isEmpty();
    }

    public List<Person> getListOfMembers() {
        return jdbcTemplate.query("select * from person", new BeanPropertyRowMapper<>(Person.class));
    }

    public void setMemberToBook(Person person, int book_id) {
        jdbcTemplate.update("update book set person_id=? where book_id=?", person.getPerson_id(), book_id);
    }

    public void removeMember(int book_id) {
        jdbcTemplate.update("update book set person_id=null where book_id=?", book_id);
    }

    public String giveMemberOfTheBook(int book_id) {
        String sql = "select person_id from book where book_id=?";
        Integer index = jdbcTemplate.queryForObject(sql, new Object[]{book_id}, Integer.class);
        PersonDAO personDAO = new PersonDAO(jdbcTemplate);
        return index == null ? null : personDAO.show(index).getName();
    }

}
