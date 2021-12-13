package com.libraryproject.librarysystem.repositories;

import com.libraryproject.librarysystem.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Integer> {
    Optional<Users> findByUserName(String username);
}
