package com.sduwh.sso.grant;

import com.sduwh.sso.domain.UserGrant;
import com.sduwh.sso.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.annotation.Resource;

public class SsoTokenStore extends JwtTokenStore {
  @Resource private UserService userService;

  public SsoTokenStore(JwtAccessTokenConverter jwtTokenEnhancer) {
    super(jwtTokenEnhancer);
  }

  @Override
  public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
    String login = (String) authentication.getPrincipal();
    UserGrant userGrant = new UserGrant();
    userGrant.setLogin(login);
    userGrant.setCurrentToken(token.getValue());
    userService.updateUserGrant(userGrant);
  }

  @Override
  public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
    String login = (String) authentication.getPrincipal();
    UserGrant userGrant = userService.findOneUserGrantByLogin(login);
    if (userGrant != null && !StringUtils.isEmpty(userGrant.getCurrentToken())) {
      return readAccessToken(userGrant.getCurrentToken());
    }
    return super.getAccessToken(authentication);
  }
}
