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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

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

    @GetMapping("/users/{id}")
    public String getUser(@PathVariable(required = false) long id, Model m){
        ApplicationUser a = applicationUserRepository.findById(id);
        m.addAttribute("user", a);

        return "root";
    }


    @GetMapping("/login")
    public String getLoginPage(){
        return "login";
    }

}
