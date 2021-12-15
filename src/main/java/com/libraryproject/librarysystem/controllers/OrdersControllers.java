package com.libraryproject.librarysystem.controllers;

import com.libraryproject.librarysystem.domain.*;
import com.libraryproject.librarysystem.repositories.BooksRepository;
import com.libraryproject.librarysystem.repositories.OrdersRepository;
import com.libraryproject.librarysystem.repositories.UsersRepository;
import com.libraryproject.librarysystem.security.MyUserDetails;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Controller
public class OrdersControllers {

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private BooksRepository booksRepository;

    @RequestMapping("/orderslist")
    public String allOrders(Model model) {
        List<Orders> orders;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails currentUser = (MyUserDetails) authentication.getPrincipal();
        Users user = usersRepository.getById(currentUser.getUserID());

        if (user.getAccessLevel() == AccessLevel.LIBRARIAN) {
            System.out.println("It's librarian " + currentUser);
            model.addAttribute("level","librarian");
            orders = ordersRepository.findAll();
        } else {
            System.out.println("It's user " + currentUser);
            model.addAttribute("level","user");
            orders = ordersRepository.findByUser(user);
        }

        model.addAttribute("orders", orders);
        return "orderslist.html";
    }

    @GetMapping("/addneworder")
    public String orderList(Model model) {


        model.addAttribute("users", usersRepository.findAll());
        model.addAttribute("books", booksRepository.findAll());
        model.addAttribute("statusFinished", OrderStatus.FINISHED);
        model.addAttribute("statusUninished", OrderStatus.UNFINISHED);
        model.addAttribute("available", Availability.AVAILABLE);
        return "addneworder.html";
    }

    @RequestMapping("/addthisneworder")
    public String addOrder(
            @RequestParam(value = "userParam") String userID,
            @RequestParam(value = "issueDate") String issueDate,
            @RequestParam(value = "orderInfo") String orderInfo,
            @RequestParam(value = "bookIds") List<String> list) throws ParseException {


        Orders order = new Orders();

        order.setUser(usersRepository.getById(Integer.parseInt(userID)));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm", Locale.ENGLISH);
        issueDate = issueDate.replace('T',' ');
        order.setIssueDate(formatter.parse(issueDate));
        List<Books> booksList = new ArrayList<>();
        for (String s:list) {
            Optional<Books> booksOptional = booksRepository.findById(Integer.parseInt(s));
            Books book = booksOptional.get();
            book.setAvailability(Availability.RESERVED);
            booksList.add(book);
        }
        order.setBooksList(booksList);

        if (orderInfo.equals("unfinished")){
            order.setOrderInfo(OrderStatus.UNFINISHED);
        } else{
            order.setOrderInfo(OrderStatus.FINISHED);
        }
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
