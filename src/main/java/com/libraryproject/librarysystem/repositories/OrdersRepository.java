package com.libraryproject.librarysystem.repositories;


import com.libraryproject.librarysystem.domain.Orders;
import com.libraryproject.librarysystem.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdersRepository extends JpaRepository<Orders, Integer> {
    List<Orders> findByUser(Users user);
}
