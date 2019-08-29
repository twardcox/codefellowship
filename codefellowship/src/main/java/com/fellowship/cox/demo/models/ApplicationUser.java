package com.fellowship.cox.demo.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

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

    @ManyToMany
    @JoinTable(
      name = "user_likes",
      joinColumns = { @JoinColumn(name = "primaryUser")},
      inverseJoinColumns = { @JoinColumn(name = "likedUser")}
    )
    Set<ApplicationUser> UsersThatILike;

    @ManyToMany(mappedBy = "UsersThatILike")
    Set<ApplicationUser> UsersThatLikeMe;

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

    public void addLike(ApplicationUser likedUser) {
        UsersThatILike.add(likedUser);
    }

    public Set<ApplicationUser> getUsersThatILike(){
        return this.UsersThatILike;
    }

    public String toString(){
        StringBuilder likedUserString = new StringBuilder();
        if (this.UsersThatILike.size() > 0){
            likedUserString.append(" who likes ");
            for (ApplicationUser likedUser : this.UsersThatILike){
                likedUserString.append(likedUser.username);
                likedUserString.append(", ");
            }
            likedUserString.delete(likedUserString.length() - 2,
              likedUserString.length());
        }
        return String.format("%s %s %s", this.firstName, this.lastName,
          likedUserString.toString());
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
