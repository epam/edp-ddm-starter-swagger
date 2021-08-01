package com.epam.digital.data.platform.starter.swagger.config;

import java.util.List;
import java.util.Map.Entry;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GroupedOpenApiBeanPostProcessor implements BeanPostProcessor {

  private final GroupedOpenApiBeanFactoryPostProcessor beanGenerator;
  private final List<OperationCustomizer> customizers;

  public GroupedOpenApiBeanPostProcessor(
      GroupedOpenApiBeanFactoryPostProcessor beanGenerator,
      List<OperationCustomizer> customizers) {
    this.beanGenerator = beanGenerator;
    this.customizers = customizers;
  }

  @Override
  public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
    Entry<String, List<String>> groupDescription = beanGenerator.getBeanNameToGroup().get(beanName);
    if (groupDescription != null) {
      var builder = GroupedOpenApi
          .builder()
          .group(groupDescription.getKey())
          .pathsToMatch(groupDescription.getValue().toArray(new String[0]));
      customizers.forEach(builder::addOperationCustomizer);
      return builder.build();
    }
    return bean;
  }

  @Bean
  public GroupedOpenApi openApiGroupFactory() {
    var builder = GroupedOpenApi
        .builder()
        .group("all")
        .pathsToMatch("/**");
    customizers.forEach(builder::addOperationCustomizer);
    return builder.build();
  }
}
