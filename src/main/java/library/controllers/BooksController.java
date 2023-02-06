package library.controllers;


import library.dao.BookDAO;
import library.models.Book;
import library.models.Person;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
@AllArgsConstructor
public class BooksController {

    private final BookDAO bookDAO;


    @GetMapping()
    public String index(Model model) {
        model.addAttribute("books", bookDAO.index());
        return "books/index";
    }

    @GetMapping("/new")
    public String newBook(Model model) {
        model.addAttribute("book", new Book());
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") Book book) {
        bookDAO.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable int id, Model model) {
        model.addAttribute("book", bookDAO.show(id));
        model.addAttribute("people", bookDAO.getListOfMembers());
        model.addAttribute("isTaken", bookDAO.isTaken(id));
        model.addAttribute("person", new Person());
        model.addAttribute("person_with_book", bookDAO.giveMemberOfTheBook(id));
        bookDAO.show(id);
        return "books/show";
    }

    @PostMapping("/{id}")
    public String giveToMember(@ModelAttribute("person") Person person,
                               @PathVariable int id) {
        bookDAO.setMemberToBook(person, id);
        return "redirect:/books/{id}";
    }

    @PostMapping("/{id}/getBack")
    public String getBack(@PathVariable int id) {
        bookDAO.removeMember(id);
        return "redirect:/books/{id}";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable int id) {
        model.addAttribute("book", bookDAO.show(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable int id,
                         @ModelAttribute("book") Book book) {
        bookDAO.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable int id) {
        bookDAO.delete(id);
        return "redirect:/books";
    }

}
