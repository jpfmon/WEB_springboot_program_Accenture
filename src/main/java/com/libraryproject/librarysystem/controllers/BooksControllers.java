package com.libraryproject.librarysystem.controllers;

import com.libraryproject.librarysystem.domain.Availability;
import com.libraryproject.librarysystem.domain.Books;
import com.libraryproject.librarysystem.repositories.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/books")
public class BooksControllers {

    @Autowired
    private BooksRepository booksRepository;

    @GetMapping()
    public String bookList() {
        return "book.html";
    }
    /*@GetMapping(value = "/allBooks")
    public @ResponseBody
    Iterable<Books> getAll () {
        return booksRepository.findAll();
    }*/



    /*@PostMapping(value = "/addBook")
    public @ResponseBody String addBook(@RequestParam String title) {
        Books book = new Books(title, Availability.AVAILABLE);
        booksRepository.save(book);
        return "Success";
    }*/
}
