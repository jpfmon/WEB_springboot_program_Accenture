package com.libraryproject.librarysystem.repositories;

import com.libraryproject.librarysystem.domain.Books;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BooksRepository extends JpaRepository<Books, Integer> {

}
