package com.sduwh.sso.grant;

import com.sduwh.sso.domain.User;
import com.sduwh.sso.service.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import javax.annotation.Resource;
import java.security.InvalidParameterException;
import java.util.Collections;
import java.util.Map;

public class SsoGranter extends AbstractTokenGranter {
  @Resource private UserService userService;
  @Resource private PasswordEncoder passwordEncoder;

  public SsoGranter(
      AuthorizationServerTokenServices tokenServices,
      ClientDetailsService clientDetailsService,
      OAuth2RequestFactory requestFactory,
      String grantType) {
    super(tokenServices, clientDetailsService, requestFactory, grantType);
  }

  @Override
  protected OAuth2Authentication getOAuth2Authentication(
      ClientDetails client, TokenRequest tokenRequest) {
    Map<String, String> param = tokenRequest.getRequestParameters();
    String keyUsername = "username";
    String keyPassword = "password";
    if (!param.containsKey(keyUsername) || !param.containsKey(keyPassword)) {
      throw new InvalidParameterException();
    }
    String username = param.get(keyUsername);
    String password = param.get(keyPassword);
    User user = userService.findOneUserByLogin(username);
    if (user == null) {
      throw new InvalidGrantException("Grant fail");
    }
    if (passwordEncoder.matches(password, user.getPassword())) {
      Authentication authentication =
          new UsernamePasswordAuthenticationToken(
              username, user.getPassword(), Collections.singleton(() -> user.getRole().name()));
      OAuth2Request auth2Request =
          this.getRequestFactory().createOAuth2Request(client, tokenRequest);
      return new OAuth2Authentication(auth2Request, authentication);
    }
    throw new InvalidGrantException("Grant fail");
  }
}
