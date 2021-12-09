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
        book.setAvailability(Availability.AVAILABLE);
        booksRepository.save(book);
        return "redirect:/";
    }

    @GetMapping("/viewbook/{id}")
    public String viewOneBook(Model model, @PathVariable int id) {
        Books book = booksRepository.getById(id);
        model.addAttribute("book", book);
        return "infoonebook.html";
    }

    @GetMapping("/viewbook/delete/{id}")
    public String deleteBook(@PathVariable int id) {
        booksRepository.deleteById(id);

        return "redirect:/";
    }

    @GetMapping("/viewbook/edit/{id}")
    public String editBook(Model model) {
        Books book = new Books();
        model.addAttribute("book", book);

        return "editbook.html";
    }

    @GetMapping("/editthisbook")
    public String editThisBook(Model model, @PathVariable int id, @RequestParam String title, String url) {
        Books book = booksRepository.getById(id);
        book.setTitle(title);
        book.setUrl(url);
        booksRepository.save(book);
        book = booksRepository.getById(id);
        model.addAttribute("bookb", book);
        return "infoonebook.html";
    }

}
