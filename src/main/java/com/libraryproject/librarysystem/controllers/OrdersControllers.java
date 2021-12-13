package com.libraryproject.librarysystem.controllers;

import com.libraryproject.librarysystem.domain.Books;
import com.libraryproject.librarysystem.domain.OrderStatus;
import com.libraryproject.librarysystem.domain.Orders;
import com.libraryproject.librarysystem.domain.Users;
import com.libraryproject.librarysystem.repositories.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

public class OrdersControllers {

        @Autowired
        private OrdersRepository ordersRepository;

        @RequestMapping("/orderslist")
        public String allOrders(Model model) {
            model.addAttribute("orders", ordersRepository.findAll());
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
            //Orders order = ordersRepository.getById(orderID);
            model.addAttribute("order", order);
            return "infooneorder.html";
        }

        @GetMapping("/vieworder/edit/{id}")
        public String editOrder(Model model,@PathVariable int orderID) {
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

        @GetMapping("/vieworder/delete/{id}")
        public String deleteOrder(@PathVariable int orderID) {
            System.out.println("Trying to delete this order: " + orderID );
            ordersRepository.deleteById(orderID);
            return "redirect:/orderslist";
        }
}