package com.fellowship.cox.demo.models;

import javax.persistence.*;
import java.util.Date;

@Entity

public class UserPost {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  long id;
  String body;
  Date createdAt;

  @ManyToOne
  ApplicationUser user;

  public UserPost(String body, Date createdAt, ApplicationUser user) {
    this.user = user;
    this.body = body;
    this.createdAt = createdAt;
  }

  public long getId() {
    return id;
  }



  public String getBody() {
    return body;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public UserPost(){}

  public String  toString(){
    return String.format("On %s, User Id %s posted: %s", this.createdAt, this.id,
      this.body);
  }
}
