package com.fellowship.cox.demo.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
public class ApplicationUser implements UserDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;
    public String username;
    String password;
    public String firstName;
    public String lastName;
    public Date dateOfBirth;
    public String bio;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    List<UserPost> userPost;

    public List<UserPost> getUserPost(){
        return this.userPost;
    }

    public long getId() { return this.id; }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Date getDateOfBirth() {
        return this.dateOfBirth;
    }

    public String getBio() {
        return this.bio;
    }

    public ApplicationUser(String username, String password, String firstName, String lastName, Date dateOfBirth, String bio){
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.bio = bio;
    }

    public ApplicationUser(){};

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
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
