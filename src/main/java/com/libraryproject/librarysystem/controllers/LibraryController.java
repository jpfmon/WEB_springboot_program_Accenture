package com.libraryproject.librarysystem.controllers;


import com.libraryproject.librarysystem.domain.*;
import com.libraryproject.librarysystem.repositories.BooksRepository;
import com.libraryproject.librarysystem.repositories.OrdersRepository;
import com.libraryproject.librarysystem.repositories.UsersRepository;
import com.libraryproject.librarysystem.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class LibraryController {

    @Autowired
    private BooksRepository booksRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private OrdersRepository ordersRepository;

    @GetMapping("/login")
    public String home() {

        return "login.html";
    }

    @RequestMapping("/")
    public String dashboard() {
        Books book = booksRepository.getById(11);
        return "dashboard.html";
    }

    @RequestMapping("/bookslist")
    public String bookListUser(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails currentUser = (MyUserDetails) authentication.getPrincipal();
        Users user = usersRepository.getById(currentUser.getUserID());

        List<Books> books;
        if (user.getAccessLevel() == AccessLevel.LIBRARIAN) {
            System.out.println("It's librarian " + currentUser);
            model.addAttribute("level","librarian");
        } else {
            System.out.println("It's user " + currentUser);
            model.addAttribute("level","user");
        }

        if (user.getAccessLevel() == AccessLevel.LIBRARIAN) {
            books = booksRepository.findAll();
        } else {
            books = booksRepository.findByAvailability(Availability.AVAILABLE);
        }

//        books = booksRepository.findAll();

        model.addAttribute("books", books);
        model.addAttribute("available", Availability.AVAILABLE);
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

        ArrayList<Books> list = new ArrayList<>();

        for (String number : booksSelected) {
            Integer n =  Integer.parseInt(number.trim());
            list.add(booksRepository.getById(n));
        }

        model.addAttribute("booksSelected", list);

        return "/confirmreservation.html";
    }

    @RequestMapping("/library/confirmreservationend")
    public String confirmreservationend(@RequestParam MultiValueMap<String, Integer> map, Model model) {

        System.out.println("Ended reservation");
        String[] booksSelected = map.get("bookIds").toString().replaceAll("\\[","").replaceAll("\\]","").split(",");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails currentUser = (MyUserDetails) authentication.getPrincipal();
        Users user = usersRepository.getById(currentUser.getUserID());

        List<Books> listOfBooks = new ArrayList<>();

        for (String number:booksSelected) {
            Integer bookId = Integer.parseInt(number.trim());
            System.out.println("Id of book: " + bookId);
            Optional<Books> bookOp = booksRepository.findById(bookId);
            Books book = (Books) bookOp.get();
            System.out.println("Book: " + book);
            book.setAvailability(Availability.RESERVED);
            listOfBooks.add(book);
        }
        Orders order = new Orders(user, listOfBooks,new Date(), OrderStatus.UNFINISHED);
        ordersRepository.save(order);

        return "/confirmreservationend.html";
    }
}
