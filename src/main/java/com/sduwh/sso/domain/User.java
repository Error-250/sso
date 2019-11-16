package com.sduwh.sso.domain;

import com.fasterxml.jackson.annotation.JsonView;
import com.sduwh.sso.domain.view.AdminView;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * 用户, 使用jackson试图控制数据返回
 *
 * @author wxp
 */
@Data
public class User implements UserDetails {
  @JsonView(AdminView.class)
  private Long id;

  @JsonView(AdminView.class)
  private String login;

  private String password;

  @JsonView(AdminView.class)
  private Role role;

  @JsonView(AdminView.class)
  private String email;

  @JsonView(AdminView.class)
  private LocalDateTime createTime;

  @JsonView(AdminView.class)
  private LocalDateTime updateTime;

  @JsonView(AdminView.class)
  private List<UserGrant> lastGrant;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.singleton(() -> role.name());
  }

  @Override
  public String getUsername() {
    return login;
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

  public enum Role {
    /*
    学生
     */
    STUDENT,
    /*
    教师
     */
    TEACHER,
    /*
    管理员
     */
    ADMIN
  }
}
