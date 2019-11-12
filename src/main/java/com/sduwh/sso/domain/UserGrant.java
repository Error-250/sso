package com.sduwh.sso.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserGrant {
  private Long id;
  private Long userId;
  private String login;
  private String currentToken;
  private Integer times;
  private LocalDateTime createTime;
  private LocalDateTime updateTime;
}
