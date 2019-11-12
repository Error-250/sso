package com.sduwh.sso.config;

import com.sduwh.sso.service.UserService;
import com.sduwh.sso.service.impl.LocalUserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SsoConfig {
  @Bean
  public UserService userService() {
    return new LocalUserServiceImpl();
  }
}
