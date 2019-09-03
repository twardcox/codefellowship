package com.fellowship.cox.demo.controllers;

import com.fellowship.cox.demo.models.ApplicationUser;
import com.fellowship.cox.demo.models.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.LongSummaryStatistics;

@Controller
public class HomeController {
    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    PasswordEncoder encoder;

    @GetMapping("/")
    public String getRoot(Principal p, Model m){

        ApplicationUser applicationUser = null;
        if(p != null){
            applicationUser=applicationUserRepository.findByUsername(p.getName());
        }
        m.addAttribute("user", applicationUser);

        return "homepage";
    }

    @GetMapping("/myprofile")
    public String getProfile(Principal p, Model m){
        ApplicationUser applicationUser = null;

        if(p!=null){
            applicationUser = applicationUserRepository.findByUsername(p.getName());
        }
        m.addAttribute("user", applicationUser);
        return "myprofile";
    }

    @GetMapping("/signup")
    public String getSignUp(){
        return "registration";
    }

    @PostMapping("/signup")
    public RedirectView createUser(String username, String password, String firstName, String lastName,
                                   Date dateOfBirth, String bio) {
        ApplicationUser newUser = new ApplicationUser(username, encoder.encode(password), firstName, lastName,
                                                      dateOfBirth,
                                                      bio);
        applicationUserRepository.save(newUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(newUser, null, new ArrayList<>());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new RedirectView("/");
    }


    @Controller
    public class MyErrorController implements ErrorController {

        @RequestMapping("/error")
        public String handleError(HttpServletRequest request) {
            Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

            if (status != null) {
                Integer statusCode = Integer.valueOf(status.toString());

                if(statusCode == HttpStatus.NOT_FOUND.value()) {
                    return "error-404";
                }
                else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                    return "error-500";
                }
            }
            return "error";
        }

        @Override
        public String getErrorPath() {
            return "/error";
        }
    }
}
