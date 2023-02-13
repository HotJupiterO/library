package library.controllers;

import library.models.Book;
import library.models.Person;
import library.services.BookService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
@RequestMapping("/books")
public class BooksController {
    private final BookService bookService;


    @GetMapping()
    public String index(Model model) {
        model.addAttribute("books", bookService.index());
        return "books/index";
    }

    @GetMapping("/new")
    public String newBook(Model model) {
        model.addAttribute("book", new Book());
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") Book book) {
        bookService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable int id, Model model) {
        model.addAttribute("book", bookService.show(id));
        model.addAttribute("people", bookService.getListOfMembers());
        model.addAttribute("isTaken", bookService.isTaken(id));
        model.addAttribute("person", new Person());
        model.addAttribute("person_with_book", bookService.giveMemberOfTheBook(id));
        bookService.show(id);
        return "books/show";
    }

    @PostMapping("/{id}")
    public String giveToMember(@ModelAttribute("person") Person person,
                               @PathVariable int id) {
        bookService.setMemberToBook(person, id);
        return "redirect:/books/{id}";
    }

    @PostMapping("/{id}/getBack")
    public String getBack(@PathVariable int id) {
        bookService.removeMember(id);
        return "redirect:/books/{id}";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable int id) {
        model.addAttribute("book", bookService.show(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable int id,
                         @ModelAttribute("book") Book book) {
        bookService.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable int id) {
        bookService.delete(id);
        return "redirect:/books";
    }

}
