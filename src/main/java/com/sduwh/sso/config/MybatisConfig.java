package com.sduwh.sso.config;

import com.sduwh.sso.domain.User;
import com.sduwh.sso.mapper.handler.UserRoleHandler;
import org.apache.ibatis.type.TypeReference;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.List;

/**
 * mybatis全局配置
 *
 * @author wxp
 */
@Configuration
@EnableTransactionManagement
public class MybatisConfig {

  @Bean
  public ConfigurationCustomizer configurationCustomizer() {
    return (configuration -> {
      // 使用驼峰命名
      configuration.setMapUnderscoreToCamelCase(true);
      // 对List<User.Role>使用自定义数据库类型转换器
      configuration
          .getTypeHandlerRegistry()
          .register(new TypeReference<List<User.Role>>() {}, new UserRoleHandler());
    });
  }
}
