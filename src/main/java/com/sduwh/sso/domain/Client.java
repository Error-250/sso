package com.sduwh.sso.domain;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
public class Client implements ClientDetails {
  private String id;
  private String security;
  private List<User.Role> grantRole;
  private String grantType;
  private Integer accessTokenValiditySeconds;
  private Integer refreshTokenValiditySeconds;
  private LocalDateTime createTime;
  private LocalDateTime updateTime;

  @Override
  public String getClientId() {
    return this.id;
  }

  @Override
  public Set<String> getResourceIds() {
    return null;
  }

  @Override
  public boolean isSecretRequired() {
    return true;
  }

  @Override
  public String getClientSecret() {
    return this.security;
  }

  @Override
  public boolean isScoped() {
    return false;
  }

  @Override
  public Set<String> getScope() {
    return Collections.singleton("OPEN");
  }

  @Override
  public Set<String> getAuthorizedGrantTypes() {
    if (StringUtils.isEmpty(this.grantType)) {
      return Collections.emptySet();
    }
    return Stream.of(this.grantType).map(String::toLowerCase).collect(Collectors.toSet());
  }

  @Override
  public Set<String> getRegisteredRedirectUri() {
    return null;
  }

  @Override
  public Collection<GrantedAuthority> getAuthorities() {
    if (this.grantRole == null) {
      return Collections.emptyList();
    }
    return Lists.newArrayList(this.grantRole).stream()
        .map(role -> (GrantedAuthority) role::name)
        .collect(Collectors.toList());
  }

  @Override
  public Integer getAccessTokenValiditySeconds() {
    return this.accessTokenValiditySeconds;
  }

  @Override
  public Integer getRefreshTokenValiditySeconds() {
    return this.refreshTokenValiditySeconds;
  }

  @Override
  public boolean isAutoApprove(String s) {
    return false;
  }

  @Override
  public Map<String, Object> getAdditionalInformation() {
    return null;
  }

  public enum ClientIdE {
    LMS
  }
}
