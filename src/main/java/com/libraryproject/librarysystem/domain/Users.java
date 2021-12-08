package com.libraryproject.librarysystem.domain;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userID;

    private String userFullName;
    private String email;
    private String phone;
    private String password;
    @Enumerated
    private AccessLevel accessLevel;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.MERGE})
    @OrderBy("orderID DESC")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Orders> myOrders;

    public Users() {
    }

    public Users(int userID, String userFullName, String email, String phone, String password, AccessLevel accessLevel) {
        this.userID = userID;
        this.userFullName = userFullName;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.accessLevel = accessLevel;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AccessLevel getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(AccessLevel accessLevel) {
        this.accessLevel = accessLevel;
    }

}
