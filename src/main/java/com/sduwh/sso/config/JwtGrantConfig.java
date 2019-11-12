package com.sduwh.sso.config;

import com.sduwh.sso.grant.Md5PasswordEncoder;
import com.sduwh.sso.grant.SsoGranter;
import com.sduwh.sso.grant.SsoTokenStore;
import com.sduwh.sso.service.impl.LocalClientServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

@Configuration
public class JwtGrantConfig {
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new  Md5PasswordEncoder();
  }

  @Bean
  public ClientDetailsService clientDetailsService() {
    return new LocalClientServiceImpl();
  }

  @Bean
  @DependsOn("clientDetailsService")
  public OAuth2RequestFactory auth2RequestFactory(ClientDetailsService clientDetailsService) {
    return new DefaultOAuth2RequestFactory(clientDetailsService);
  }

  @Bean
  public JwtAccessTokenConverter accessTokenConverter() {
    JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
    jwtAccessTokenConverter.setSigningKey("test");
    return jwtAccessTokenConverter;
  }

  @Bean
  @DependsOn("accessTokenConverter")
  public TokenStore tokenStore(JwtAccessTokenConverter jwtAccessTokenConverter) {
    return new SsoTokenStore(jwtAccessTokenConverter);
  }

  @Bean
  @DependsOn({"accessTokenConverter", "tokenStore", "clientDetailsService"})
  public DefaultTokenServices defaultTokenServices(
      JwtAccessTokenConverter jwtAccessTokenConverter,
      TokenStore tokenStore,
      ClientDetailsService clientDetailsService) {
    DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
    defaultTokenServices.setClientDetailsService(clientDetailsService);
    defaultTokenServices.setAccessTokenValiditySeconds(3600);
    defaultTokenServices.setRefreshTokenValiditySeconds(0);
    defaultTokenServices.setReuseRefreshToken(false);
    defaultTokenServices.setSupportRefreshToken(false);
    defaultTokenServices.setTokenStore(tokenStore);
    defaultTokenServices.setTokenEnhancer(jwtAccessTokenConverter);
    return defaultTokenServices;
  }

  @Bean
  @DependsOn({"defaultTokenServices", "auth2RequestFactory", "clientDetailsService"})
  public SsoGranter ssoGranter(
      @Qualifier("defaultTokenServices") DefaultTokenServices defaultTokenServices,
      OAuth2RequestFactory auth2RequestFactory,
      ClientDetailsService clientDetailsService) {
    return new SsoGranter(defaultTokenServices, clientDetailsService, auth2RequestFactory, "sso");
  }
}
