package com.libraryproject.librarysystem.repositories;

import com.libraryproject.librarysystem.domain.Availability;
import com.libraryproject.librarysystem.domain.Books;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BooksRepository extends JpaRepository<Books, Integer> {

    List<Books> findByAvailability(Availability availability);
}
