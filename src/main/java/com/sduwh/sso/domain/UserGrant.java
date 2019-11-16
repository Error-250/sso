package com.sduwh.sso.domain;

import com.fasterxml.jackson.annotation.JsonView;
import com.sduwh.sso.domain.view.AdminView;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户授权
 *
 * @author wxp
 */
@Data
public class UserGrant {
  @JsonView(AdminView.class)
  private Long id;

  @JsonView(AdminView.class)
  private Long userId;

  @JsonView(AdminView.class)
  private String login;

  private String currentToken;

  @JsonView(AdminView.class)
  private Integer times;

  @JsonView(AdminView.class)
  private LocalDateTime createTime;

  @JsonView(AdminView.class)
  private LocalDateTime updateTime;
}
