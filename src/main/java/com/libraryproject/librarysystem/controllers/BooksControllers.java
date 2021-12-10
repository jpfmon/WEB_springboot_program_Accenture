package com.libraryproject.librarysystem.controllers;

import com.libraryproject.librarysystem.domain.Availability;
import com.libraryproject.librarysystem.domain.Books;
import com.libraryproject.librarysystem.repositories.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


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
        return "redirect:/bookslist";
    }

    @GetMapping("/viewbook/{id}")
    public String viewOneBook(Model model, @PathVariable int id) {
        Books book = booksRepository.getById(id);
        model.addAttribute("book", book);
        return "infoonebook.html";
    }

    @GetMapping("/viewbook/delete/{id}")
    public String deleteBook(@PathVariable int id) {
        System.out.println("Trying to delete this book: " + id );
        booksRepository.deleteById(id);

        return "redirect:/bookslist";
    }

    @GetMapping("/viewbook/edit/{id}")
    public String editBook(Model model,@PathVariable int id) {
//        We don't need to create new book, but rather
//        get the existing book from database, so the html displays its actual info in the text fields
//        Books book = new Books();
        Books book = booksRepository.getById(id);

        System.out.println(book.getId() + " | " + book.getAvailability() + " | " + book.getTitle() + " | " + book.getUrl());
        model.addAttribute("book", book);
        return "editbook.html";
    }

    /**
     * Few changes required, I'm leaving this method commented so you can compare with new one below
      */
//    @GetMapping("/editthisbook")
//    public String editThisBook(Model model, @PathVariable int id, @RequestParam String title, String url) {
//        Books book = booksRepository.getById(id);
//        book.setTitle(title);
//        book.setUrl(url);
//        booksRepository.save(book);
//        book = booksRepository.getById(id);
//        model.addAttribute("bookb", book);
//        return "infoonebook.html";
//    }

    // As written in the html code, it's needed post mapping, to receive the whole java object
    @PostMapping("/editthisbook")
    public String editThisBook(@Valid Books book, Model model) {
        booksRepository.save(book);
        System.out.println("This is the id of the book from html: " + book.getId());
        book = booksRepository.getById(book.getId());
        model.addAttribute("book", book);
        return "redirect:/bookslist";
    }

}
