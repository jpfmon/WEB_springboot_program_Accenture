package com.libraryproject.librarysystem.controllers;

import com.libraryproject.librarysystem.domain.*;
import com.libraryproject.librarysystem.repositories.UsersRepository;
import com.libraryproject.librarysystem.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

import java.util.List;

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
    public String addUser(@Valid Users user, RedirectAttributes redirectAttributes) {
        user.setAccessLevel(AccessLevel.CLIENT);
        redirectAttributes.addFlashAttribute("message", "Succesfully signed up");
        usersRepository.save(user);
        return "redirect:/login";
    }


//    Controllers below are for CLIENT use.
//    Functionality: View profile. Edit profile(username, full name, email, phone).
    @GetMapping("/viewuserprofile")
    public String viewUserProfile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails currentUser = (MyUserDetails) authentication.getPrincipal();
        Users user = usersRepository.getById(currentUser.getUserID());
        model.addAttribute("user", user);
        return "infooneuser.html";
    }

    @GetMapping("/viewuserprofile/edit")
    public String editProfile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails currentUser = (MyUserDetails) authentication.getPrincipal();
        Users user = usersRepository.getById(currentUser.getUserID());
        System.out.println(user.getUserName() + " | " + user.getUserFullName() + " | "
                + user.getEmail() + " | " + user.getPhone());
        model.addAttribute("user", user);
        return "edituserprofile.html";
    }

    @PostMapping("/editthisuserprofile")
    public String editThisUserProfile(@Valid Users user, Model model) {
        usersRepository.save(user);
        user = usersRepository.getById(user.getUserID());
        model.addAttribute("user", user);
        return "redirect:/viewuserprofile";
    }



//    Controllers below are for LIBRARIAN use.
//    Added functionality: View users list. Edit access level. Delete users.
    @RequestMapping("/userslist")
    public String allUsers(Model model) {
        List<Users> users = usersRepository.findAll();

        model.addAttribute("users", users);
        return "authorlist.html";
    }

    @GetMapping("/viewuser/{id}")
    public String viewOneUser(Model model, @PathVariable int id) {
        Users user = usersRepository.getById(id);
        model.addAttribute("user", user);
        return "infooneuser.html";
    }

    @GetMapping("/viewuser/edit/{id}")
    public String editUser(Model model,@PathVariable int id) {
        Users user = usersRepository.getById(id);

        System.out.println(user.getUserName() + " | " + user.getUserFullName() + " | "
                + user.getEmail() + " | " + user.getPhone() + " | " + user.getAccessLevel());
        model.addAttribute("user", user);
        return "edituser.html";
    }

    @PostMapping("/editthisuser")
    public String editThisUser(@Valid Users user, Model model) {
        usersRepository.save(user);
        user = usersRepository.getById(user.getUserID());
        model.addAttribute("user", user);
        return "redirect:/userslist";
    }

    @GetMapping("/viewuser/delete/{id}")
    public String deleteUser(@PathVariable int id) {
        System.out.println("Trying to delete this user: " + id );
        usersRepository.deleteById(id);
        return "redirect:/userslist";
    }
}
