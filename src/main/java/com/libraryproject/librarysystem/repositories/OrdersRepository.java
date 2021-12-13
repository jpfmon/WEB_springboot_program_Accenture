package com.libraryproject.librarysystem.repositories;


import com.libraryproject.librarysystem.domain.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders, Integer> {
}
