package com.libraryproject.librarysystem.controllers;

import com.libraryproject.librarysystem.domain.*;
import com.libraryproject.librarysystem.repositories.OrdersRepository;
import com.libraryproject.librarysystem.repositories.UsersRepository;
import com.libraryproject.librarysystem.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@Controller
public class OrdersControllers {

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private UsersRepository usersRepository;

    @RequestMapping("/orderslist")
    public String allOrders(Model model) {
        List<Orders> orders = ordersRepository.findAll();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails currentUser = (MyUserDetails) authentication.getPrincipal();
        Users user = usersRepository.getById(currentUser.getUserID());

        if (user.getAccessLevel() == AccessLevel.LIBRARIAN) {
            System.out.println("It's librarian " + currentUser);
            model.addAttribute("level","librarian");
        } else {
            System.out.println("It's user " + currentUser);
            model.addAttribute("level","user");
        }

        model.addAttribute("orders", orders);
        return "orderslist.html";
    }

    @GetMapping("/addneworder")
    public String orderList(Model model) {
        Orders order = new Orders();
        model.addAttribute("order", order);
        return "addneworder.html";
    }

    @PostMapping("/addthisneworder")
    public String addOrder(@RequestParam Users user, List<Books> booksList, Date issueDate, Date returnDate, OrderStatus orderInfo) {
        Orders order = new Orders(user, booksList, issueDate, returnDate, orderInfo);
        ordersRepository.save(order);
        return "redirect:/orderslist";
    }

    @GetMapping("/vieworder/{orderID}")
    public String infoOneOrder(Model model, @PathVariable int orderID) {
        Orders order = ordersRepository.getById(orderID);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails currentUser = (MyUserDetails) authentication.getPrincipal();
        Users user = usersRepository.getById(currentUser.getUserID());

        if (user.getAccessLevel() == AccessLevel.LIBRARIAN) {
            System.out.println("It's librarian " + currentUser);
            model.addAttribute("level","librarian");
        } else {
            System.out.println("It's user " + currentUser);
            model.addAttribute("level","user");
        }


        model.addAttribute("order", order);
        return "infooneorder.html";
    }

    @GetMapping("/vieworder/edit/{orderID}")
    public String editOrder(Model model, @PathVariable int orderID) {
        Orders orders = ordersRepository.getById(orderID);
        model.addAttribute("order", orders);
        return "editorder.html";
    }

    @PostMapping("/editthisorder")
    public String editThisOrder(@Valid Orders order, Model model) {
        ordersRepository.save(order);
        order = ordersRepository.getById(order.getOrderID());
        model.addAttribute("order", order);
        return "redirect:/orderslist";
    }

    @GetMapping("/vieworder/finish/{orderID}")
    public String finishOrder(Model model, @PathVariable int orderID) {
        Orders order = ordersRepository.getById(orderID);
        order.setReturnDate(new Date());
        for (Books book: order.getBooksList()) {
            book.setAvailability(Availability.AVAILABLE);
        }
        order.setOrderInfo(OrderStatus.FINISHED);
        ordersRepository.save(order);

        model.addAttribute("orders", ordersRepository.findAll());

        return "redirect:/orderslist";
    }

    @GetMapping("/vieworder/delete/{orderID}")
    public String deleteOrder(@PathVariable int orderID) {
        System.out.println("Trying to delete this order: " + orderID);
        ordersRepository.deleteById(orderID);
        return "redirect:/orderslist";
    }
}
