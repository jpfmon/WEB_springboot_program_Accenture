package com.libraryproject.librarysystem.controllers;


import com.libraryproject.librarysystem.domain.Authors;
import com.libraryproject.librarysystem.repositories.AuthorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthorsControllers {

    @Autowired
    private AuthorsRepository authorsRepository;

    @RequestMapping("/listOfAuthors")
    public String allAuthors(Model model) {
        model.addAttribute("books", authorsRepository.findAll());
        return "authorlist.html";
    }

    @GetMapping("/addNewAuthor")
    public String authorList(Model model) {
        Authors author = new Authors();
        model.addAttribute("author", author);
        return "addnewauthor.html";
    }

    @PostMapping("/addThisNewAuthor")
    public String addAuthor(@RequestParam String authorName, String authorCountry) {
        Authors author = new Authors(authorName, authorCountry);
        authorsRepository.save(author);
        return "redirect:/";
    }

    @GetMapping("/viewAuthor/{authorID}")
    public String infoOneAuthor(Model model, @PathVariable int authorID) {
        Authors author = authorsRepository.getById(authorID);
        model.addAttribute("author", author);
        return "infooneauthor.html";
    }
}
