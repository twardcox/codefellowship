package com.fellowship.cox.demo.controllers;

import com.fellowship.cox.demo.models.ApplicationUser;
import com.fellowship.cox.demo.models.ApplicationUserRepository;
import com.fellowship.cox.demo.models.UserPost;
import com.fellowship.cox.demo.models.UserPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.Date;

@Controller
public class UserPostController {

  @Autowired
  UserPostRepository userPostRepository;

  @Autowired
  ApplicationUserRepository applicationUserRepository;

  @GetMapping("/user/post")
  public String getUserPostForm(Principal p, Model m){
    ApplicationUser applicationUser = null;
    if(p!=null){
      applicationUser=applicationUserRepository.findByUsername(p.getName());
    }
    m.addAttribute("user", applicationUser);
    return "userPost";
  }

  @PostMapping("/user/post")
  public RedirectView createPost(String body, Principal p){
    ApplicationUser loggedInUser = applicationUserRepository.findByUsername(p.getName());
    Date createdAt = new Date();

    UserPost userPost = new UserPost(body, createdAt, loggedInUser);
    userPostRepository.save(userPost);
    return new RedirectView("/users/" + loggedInUser.getId());
  }


}
