package com.libraryproject.librarysystem.controllers;

import com.libraryproject.librarysystem.domain.AccessLevel;
import com.libraryproject.librarysystem.domain.Availability;
import com.libraryproject.librarysystem.domain.Books;
import com.libraryproject.librarysystem.domain.Users;
import com.libraryproject.librarysystem.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UsersControllers {

    @Autowired
    private UsersRepository usersRepository;

    @GetMapping("/signup")
    public String userSignup(Model model) {
        Users user = new Users();
        model.addAttribute("user", user);

        return "signup.html";
    }

    @PostMapping("/signupnewuser")
    public String addUser(@RequestParam String userName, String password, String userFullName, String phone, String email) {
        Users user = new Users(userName, password, userFullName, phone, email);
        user.setAccessLevel(AccessLevel.CLIENT);
        usersRepository.save(user);
        return "redirect:/login";
    }
}
