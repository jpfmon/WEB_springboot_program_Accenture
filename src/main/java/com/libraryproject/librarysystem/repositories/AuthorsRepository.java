package com.libraryproject.librarysystem.repositories;

import com.libraryproject.librarysystem.domain.Authors;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorsRepository extends JpaRepository<Authors, Integer> {
}
