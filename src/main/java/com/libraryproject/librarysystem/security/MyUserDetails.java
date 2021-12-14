package com.libraryproject.librarysystem.security;

import com.libraryproject.librarysystem.domain.AccessLevel;
import com.libraryproject.librarysystem.domain.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class MyUserDetails implements UserDetails {

    private int userID;
    private String userName;
    private String password;
    private String userFullName;
    private String phone;
    private String email;
    private List<GrantedAuthority> accessLevel;



    public MyUserDetails() {

    }

    public MyUserDetails(Users user) {
        this.userID = user.getUserID();
        this.userName = user.getUserName();
        this.password = user.getPassword();
        this.userFullName = user.getUserFullName();
        this.phone = user.getPhone();
        this.email = user.getEmail();
        List<String> accessLevelsAsStrings = Arrays.stream(AccessLevel.values())
                .map(AccessLevel::name)
                .collect(Collectors.toList());


        this.accessLevel = Arrays.stream(accessLevelsAsStrings.toArray(String[]::new))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

    }

    public MyUserDetails(String username) {
        this.userName = username;
    }

    public int getUserID() { return userID; }

    public String getUserFullName() {
        return userFullName;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {return email;}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return accessLevel;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
