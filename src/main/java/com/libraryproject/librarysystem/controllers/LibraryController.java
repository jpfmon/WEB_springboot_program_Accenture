package com.libraryproject.librarysystem.controllers;


import com.libraryproject.librarysystem.domain.Books;
import com.libraryproject.librarysystem.repositories.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class LibraryController {

    @Autowired
    private BooksRepository booksRepository;

    @GetMapping("/login")
    public String home() {

        return "login.html";
    }

    @RequestMapping("/")
    public String dashboard() {

        Books book = booksRepository.getById(11);

        System.out.println("Author of Quijote: " + book.getAuthorsList());
        System.out.println("Author of Quijote: " + book.getAuthorsList().get(0));
        return "dashboard.html";
    }

    @RequestMapping("/bookslist")
    public String bookListUser(Model model) {
        model.addAttribute("books", booksRepository.findAll());
        return "booklistuser.html";
    }

    @RequestMapping("/library/confirmreservation")
    public String startReservation(@RequestParam MultiValueMap<String, Integer> map, Model model) {

        System.out.println("Retrieved: " + map);
        if(map.isEmpty()){
            model.addAttribute("empty", "add books to make confirmation");
            model.addAttribute("books", booksRepository.findAll());
            return "redirect:/";
        }

        String[] booksSelected = map.get("bookIds").toString().replaceAll("\\[","").replaceAll("\\]","").split(",");

        for (String number:booksSelected) {
            System.out.println("Book selected: " + number.trim());
        }

        ArrayList<Books> list = new ArrayList<>();

        for (String number : booksSelected) {
            System.out.println("ok here with " +  number);
            Integer n =  Integer.parseInt(number.trim());
            list.add(booksRepository.getById(n));
            System.out.println("Added " + n);
        }

        model.addAttribute("booksSelected", list);

        return "/confirmreservation.html";
    }

    @RequestMapping("/library/confirmreservationend")
    public String confirmreservationend(@RequestParam MultiValueMap<String, Integer> map, Model model) {

        System.out.println("Ended reservation");
        String[] booksSelected = map.get("bookIds").toString().replaceAll("\\[","").replaceAll("\\]","").split(",");
        for (String number:booksSelected) {
            System.out.println("Book confirmed: " + number.trim());
        }

        return "/confirmreservationend.html";
    }
}
