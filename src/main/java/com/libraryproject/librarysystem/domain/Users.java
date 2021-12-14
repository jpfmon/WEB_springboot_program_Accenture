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


    private String userName;
    private String password;
    private String userFullName;
    private String phone;
    private String email;

    @Enumerated
    private AccessLevel accessLevel;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.MERGE})
    @OrderBy("orderID DESC")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Orders> myOrders;

    public Users() {
    }

    public Users(String userName, String password, String userFullName, String phone, String email) {
        this.userFullName = userFullName;
        this.userName = userName;
        this.phone = phone;
        this.password = password;
        this.email = email;
    }

    public Users(int userID, String userName, String password, String userFullName, String phone, String email, AccessLevel accessLevel, List<Orders> myOrders) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
        this.userFullName = userFullName;
        this.phone = phone;
        this.email = email;
        this.accessLevel = accessLevel;
        this.myOrders = myOrders;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}
}
