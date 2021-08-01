package com.epam.digital.data.platform.starter.swagger.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
public class OpenApiConfig {

  @Bean
  public MessageSource swaggerCodesMessageSource() {
    ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
    messageSource.setDefaultEncoding("UTF-8");
    messageSource.setFallbackToSystemLocale(false);
    messageSource.addBasenames("classpath:swagger-messages");
    return messageSource;
  }

  @Bean
  public MessageSourceAccessor swaggerCodesMessageSourceAccessor(MessageSource swaggerCodesMessageSource) {
    return new MessageSourceAccessor(swaggerCodesMessageSource);
  }
}
