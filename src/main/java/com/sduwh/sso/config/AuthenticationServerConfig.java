package com.sduwh.sso.config;

import com.sduwh.sso.grant.SsoGranter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.TokenStore;

import javax.annotation.Resource;

/**
 * 授权服务器配置
 *
 * @author wxp
 */
@Configuration
@EnableAuthorizationServer
public class AuthenticationServerConfig extends AuthorizationServerConfigurerAdapter {
  @Resource private ClientDetailsService clientDetailsService;
  @Resource private TokenStore tokenStore;
  @Resource private SsoGranter ssoGranter;
  @Resource private WebResponseExceptionTranslator exceptionTranslator;

  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    clients.withClientDetails(clientDetailsService);
  }

  @Override
  public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
    // 允许通过表单授权客户端
    security.allowFormAuthenticationForClients();
    // 允许使用检查token的接口
    security.checkTokenAccess("permitAll()");
  }

  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    endpoints.tokenGranter(ssoGranter);
    endpoints.tokenStore(tokenStore);
    endpoints.exceptionTranslator(exceptionTranslator);
  }
}
