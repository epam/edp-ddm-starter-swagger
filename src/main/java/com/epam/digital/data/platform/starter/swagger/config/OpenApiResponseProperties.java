package com.epam.digital.data.platform.starter.swagger.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Configuration
@ConfigurationProperties("openapi.response")
public class OpenApiResponseProperties {
  private Map<String, Set<String>> codes = new HashMap<>();

  public Map<String, Set<String>> getCodes() {
    return codes;
  }

  public void setCodes(Map<String, Set<String>> codes) {
    this.codes = codes;
  }
}
