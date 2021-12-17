package com.libraryproject.librarysystem.repositories;

import com.libraryproject.librarysystem.domain.Users;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;



import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class UsersRepositoryTest {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private TestEntityManager entityManager;


    @BeforeEach
    void setUp() {

        Users expected = usersRepository.getById(14);

        entityManager.persist(expected);
    }

    @Test
    @DisplayName("Testing findByUserName method")
    public void whenFoundByUserName_thenReturnID() {
        Users actual = usersRepository.findByUserName("test").get();
        assertEquals(14, actual.getUserID());
    }
}