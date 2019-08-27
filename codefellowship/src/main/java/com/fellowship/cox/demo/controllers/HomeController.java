package com.fellowship.cox.demo.controllers;

import com.fellowship.cox.demo.models.ApplicationUser;
import com.fellowship.cox.demo.models.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController {
    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @GetMapping("/")
    public String getRoot(Principal p, Model m){

        ApplicationUser applicationUser = applicationUserRepository.findByUsername(p.getName());

        m.addAttribute("user", applicationUser);

        return "root";
    }

    @GetMapping("/myprofile")
    public String getProfile(Principal p, Model m){
        m.addAttribute("user", p);
        return "myprofile";
    }

    @GetMapping("/signup")
    public String getSignUp(){
        return "registration";
    }
}
