package com.sduwh.sso.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {
  private Long id;
  private String login;
  private String password;
  private Role role;
  private String email;
  private LocalDateTime createTime;
  private LocalDateTime updateTime;

  public enum Role {
    STUDENT,
    TEACHER,
    ADMIN
  }
}
