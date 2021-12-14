package com.libraryproject.librarysystem.controllers;


import com.libraryproject.librarysystem.domain.Authors;
import com.libraryproject.librarysystem.repositories.AuthorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@Controller
public class AuthorsControllers {

    @Autowired
    private AuthorsRepository authorsRepository;

    @RequestMapping("/authorslist")
    public String allAuthors(Model model) {
        List<Authors> authors = authorsRepository.findAll();

        model.addAttribute("authors", authorsRepository.findAll());
        return "authorlist.html";
    }

    @GetMapping("/addnewauthor")
    public String authorList(Model model) {
        Authors author = new Authors();
        model.addAttribute("author", author);
        return "addnewauthor.html";
    }

    @PostMapping("/addthisnewauthor")
    public String addAuthor(@RequestParam String authorName, String authorCountry) {
        Authors author = new Authors(authorName, authorCountry);
        authorsRepository.save(author);
        return "redirect:/authorslist";
    }

    @GetMapping("/viewauthor/{authorID}")
    public String infoOneAuthor(Model model, @PathVariable int authorID) {
        Authors author = authorsRepository.getById(authorID);
        model.addAttribute("author", author);
        return "infooneauthor.html";
    }

    @GetMapping("/viewauthor/edit/{id}")
    public String editAuthor(Model model,@PathVariable int id) {
        Authors authors = authorsRepository.getById(id);
        model.addAttribute("author", authors);
        return "editauthor.html";
    }

    @PostMapping("/editthisauthor")
    public String editThisAuthor(@Valid Authors author, Model model) {
        authorsRepository.save(author);
        author = authorsRepository.getById(author.getAuthorID());
        model.addAttribute("author", author);
        return "redirect:/authorslist";
    }

    @GetMapping("/viewauthor/delete/{id}")
    public String deleteAuthor(@PathVariable int id, Model model) {
        System.out.println("Trying to delete this author: " + id );
        authorsRepository.deleteById(id);
        List<Authors> authors = authorsRepository.findAll();
        model.addAttribute("authors", authorsRepository.findAll());
        return "redirect:/authorslist";
    }
}
