package com.sduwh.sso.config;

import com.sduwh.sso.mapper.handler.UserRoleHandler;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisConfig {

  @Bean
  public ConfigurationCustomizer configurationCustomizer() {
    return (configuration -> {
      configuration.setMapUnderscoreToCamelCase(true);
      configuration.getTypeHandlerRegistry().register(new UserRoleHandler());
    });
  }
}
