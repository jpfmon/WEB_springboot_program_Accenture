package com.libraryproject.librarysystem.domain;

import javax.persistence.*;
import java.util.List;

@Entity
public class Authors {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int authorID;

    private String authorName;
    private String authorCountry;

    @ManyToMany
    private List<Books> booksList;

    public Authors() {
    }

    public Authors(int authorID, String authorName, String authorCountry) {
        this.authorID = authorID;
        this.authorName = authorName;
        this.authorCountry = authorCountry;
    }

    public int getAuthorID() {
        return authorID;
    }

    public void setAuthorID(int authorID) {
        this.authorID = authorID;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorCountry() {
        return authorCountry;
    }

    public void setAuthorCountry(String authorCountry) {
        this.authorCountry = authorCountry;
    }
}
