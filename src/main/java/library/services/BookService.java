package library.services;

import library.models.Book;
import library.models.Person;
import library.repositories.BookRepository;
import library.repositories.PeopleRepository;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final PeopleRepository peopleRepository;


    public List<Book> index() {
        return bookRepository.findAll();
    }

    @Transactional
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook) {
        updatedBook.setBook_id(id);
        bookRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id) {
        bookRepository.deleteById(id);
    }

    public Book show(int id) {
        Optional<Book> one = bookRepository.findById(id);
        Hibernate.initialize(one);
        return one.orElse(null);
    }

    public boolean isTaken(int id) {
        return bookRepository.getOne(id).getMember() != null;
    }

    public List<Person> getListOfMembers() {
        return peopleRepository.findAll();
    }

    @Transactional
    public void setMemberToBook(Person person, int book_id) {
        Book updatedBook = bookRepository.getOne(book_id);
        updatedBook.setMember(person);
        bookRepository.save(updatedBook);
    }

    @Transactional
    public void removeMember(int book_id) {
        setMemberToBook(null, book_id);
    }

    public String giveMemberOfTheBook(int book_id) {
        Book book = bookRepository.getOne(book_id);
        return book.getMember() == null ? null : book.getMember().getName();
    }
}
