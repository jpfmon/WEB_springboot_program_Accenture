package com.libraryproject.librarysystem.controllers;

import com.libraryproject.librarysystem.domain.Availability;
import com.libraryproject.librarysystem.domain.Books;
import com.libraryproject.librarysystem.repositories.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BooksControllers {

    @Autowired
    private BooksRepository booksRepository;

    @GetMapping("/addnewbook")
    public String bookList(Model model) {
        Books book = new Books();
        model.addAttribute("book", book);

        return "addnewbook.html";
    }

    @PostMapping("/addthisnewbook")
    public String addBook(@RequestParam String title, String url) {
        Books book = new Books(title, url);
        booksRepository.save(book);
        return "redirect:/";
    }

    @GetMapping("/viewbook/{id}")
    public String infoOneBook(Model model, @PathVariable int id) {
        Books book = booksRepository.getById(id);
        model.addAttribute("book", book);
        return "infoonebook.html";
    }
}
