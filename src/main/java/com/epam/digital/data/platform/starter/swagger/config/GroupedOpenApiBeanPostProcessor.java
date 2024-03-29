/*
 * Copyright 2021 EPAM Systems.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.epam.digital.data.platform.starter.swagger.config;

import java.util.List;
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
    OpenApiRequestParamProperties.OpenApiGroup groupDescription = beanGenerator.getBeanNameToGroup().get(beanName);
    if (groupDescription != null) {
      var builder = GroupedOpenApi
          .builder()
          .group(groupDescription.getName())
          .pathsToMatch(groupDescription.getEndpoints().toArray(new String[0]));
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
