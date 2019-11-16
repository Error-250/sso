package com.sduwh.sso.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.sduwh.sso.jackson.LocalDateTimeSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

/**
 * jackson全局配置
 *
 * @author wxp
 */
@Configuration
public class JacksonConfig {

  @Bean
  public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
    return jacksonObjectMapperBuilder -> {
      // 驼峰配置
      jacksonObjectMapperBuilder.propertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
      // 忽略值为null的属性
      jacksonObjectMapperBuilder.serializationInclusion(JsonInclude.Include.NON_NULL);
      // 对LocalDateTime类型使用自定义序列化类
      jacksonObjectMapperBuilder.serializerByType(
          LocalDateTime.class, new LocalDateTimeSerializer());
    };
  }
}
