package com.sduwh.sso.grant;

import com.sduwh.sso.domain.UserGrant;
import com.sduwh.sso.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.annotation.Resource;

/**
 * 自定义token存储.
 * 首次登录时token存入数据库.
 * 第二次到第N次登录时, 从数据库读入, 只要该token不过期, 登陆成功时都返回该token.
 * 直到数据库token过期, 重新生成token返回 并存入数据库.
 * 即: 任意一个时刻对同一个用户只有一个token有效.
 *
 * @author wxp
 */
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
