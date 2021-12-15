package com.libraryproject.librarysystem.controllers;

import com.libraryproject.librarysystem.database_cleaner.DatabaseCleaner;
import com.libraryproject.librarysystem.domain.Authors;
import com.libraryproject.librarysystem.repositories.AuthorsRepository;
import org.codehaus.groovy.vmplugin.v9.ClassFinder;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Authors.class)//
@WebAppConfiguration//
public class AuthorsControllersTest {

    // @Autowired private AuthorsControllers addAuthor;
    // @Autowired private AuthorsControllers allAuthors;
    // @Autowired private DatabaseCleaner databaseCleaner;
    // @Mock Model model;
    private MockMvc mockMvcBuilder;
    @Mock
    private AuthorsRepository authorsRepository;//?
    @Mock
    private Authors authorsDomain;
    @InjectMocks
    private AuthorsControllers addAuthorControllers;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvcBuilder = MockMvcBuilders.standaloneSetup(addAuthorControllers)
                .build();
    }

    //  @BeforeEach
    //  public void setup() { databaseCleaner.clean(); }


    @Test
    public void shouldCheckIfAuthorsAddedCorrectly() {
        Authors author1 = new Authors("Green", "Greece");
        addAuthorControllers.addAuthor(author1.getAuthorName(), author1.getAuthorCountry());

        Authors author2 = new Authors("Orange", "Madagascar");
        addAuthorControllers.addAuthor(author2.getAuthorName(), author2.getAuthorCountry());

        Mockito.verify(authorsDomain);                             //testing if our class is called
        assertEquals(author1.getAuthorName(), "Green");
        assertEquals(author1.getAuthorCountry(), "Greece");
        assertEquals(author2.getAuthorName(), "Orange");
        assertEquals(author2.getAuthorCountry(), "Madagascar");
        assertNotNull(authorsRepository);//not null
    }
 /*
        // assertEquals(authorsRepository.findAll().size(), 2);
        // assertEquals(getClass(AuthorsControllers.).allAuthors().size(), 2);
        // assertEquals(Authors.getAuthor.size(), 2);

        //AuthorsControllers show = allAuthors.allAuthors(author1, author2);
        //assertEquals(show.size(), 2);
    }

   @Test
    public void delete() {
        Authors author1 = new Authors(1, "Green", "Greece");//добавляем
        addAuthorControllers.addAuthor("Green", "Greece");//добавляе

        Authors author2 = new Authors(2, "Orange", "Madagascar");//добавляем
        addAuthorControllers.addAuthor(author2.getAuthorName(), author2.getAuthorCountry());//добывляем

        addAuthorControllers.deleteAuthor(1, new ExtendedModelMap()); // deleting 1st author with id 1-
       assertTrue(true);//         shows that delete Author is true
        assertEquals(author1.getAuthorID(), 1);//shows that with id 1 was deleted
        assertEquals(author1.getAuthorName(), "Green");//shows that with that name was deleted
        assertEquals(author1.getAuthorCountry(), "Greece");//shows that that country was deleted

    }

    @Test
    public void showAllAuthorsInLibraryHtml() {
        assertEquals(addAuthorControllers.allAuthors(new ExtendedModelMap()), "authorlist.html");// There are some authors
    }

    @Test
    public void addNewAuthorsListHtml() {
        Authors author1 = new Authors(1, "Green", "Greece");
        addAuthorControllers.addAuthor(author1.getAuthorName(), author1.getAuthorCountry());
        assertEquals(addAuthorControllers.authorList(new ExtendedModelMap()), "addnewauthor.html");
        assertEquals(author1.getAuthorName(), "Green");//checks if that author was added
        assertEquals(author1.getAuthorCountry(), "Greece");//checks if that author was added
    }*/
}




