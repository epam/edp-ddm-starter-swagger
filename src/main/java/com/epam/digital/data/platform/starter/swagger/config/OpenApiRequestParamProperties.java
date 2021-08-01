package com.epam.digital.data.platform.starter.swagger.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("openapi.request")
public class OpenApiRequestParamProperties {

  private List<String> headers = new ArrayList<>();
  private Map<String, List<String>> groups = new HashMap<>();

  public List<String> getHeaders() {
    return headers;
  }

  public void setHeaders(List<String> headers) {
    this.headers = headers;
  }

  public Map<String, List<String>> getGroups() {
    return groups;
  }

  public void setGroups(Map<String, List<String>> groups) {
    this.groups = groups;
  }
}
