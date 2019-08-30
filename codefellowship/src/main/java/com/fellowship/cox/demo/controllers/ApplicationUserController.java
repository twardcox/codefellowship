package com.fellowship.cox.demo.controllers;

import com.fellowship.cox.demo.models.ApplicationUser;
import com.fellowship.cox.demo.models.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;

@Controller
public class ApplicationUserController {

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @PostMapping("/users")
    public RedirectView createUser(String username, String password, String firstName, String lastName, Date dateOfBirth, String bio){
        ApplicationUser newUser = new ApplicationUser(username, encoder.encode(password), firstName, lastName, dateOfBirth, bio);

        applicationUserRepository.save(newUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(newUser, null, new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new RedirectView("/myprofile");
    }

    @GetMapping("/user/{id}")
    public String getUser(@PathVariable(required = false) long id, Model m, Principal p){
        ApplicationUser a = applicationUserRepository.findById(id);
        m.addAttribute("user", a);
        ApplicationUser loggedInUser = applicationUserRepository.findByUsername(p.getName());
        m.addAttribute("loggedInUser", loggedInUser);

        return "users";
    }

    @PostMapping("/user/{id}")
    public RedirectView addUserLike(long likedUser, Principal p) {
        ApplicationUser likingPerson = applicationUserRepository.findByUsername(p.getName());

        likingPerson.addLike(applicationUserRepository.findById(likedUser));
        applicationUserRepository.save(likingPerson);
        return new RedirectView("/");
    }

    @PostMapping(value="/like/{likedUser}")
    public RedirectView followUser(@PathVariable long likedUser, Principal p, Model m) {
        ApplicationUser likingPerson = applicationUserRepository.findByUsername(p.getName());
        likingPerson.addLike(applicationUserRepository.findById(likedUser));
        applicationUserRepository.save(likingPerson);
        return new RedirectView("/feed");
    }

}
