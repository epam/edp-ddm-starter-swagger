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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class GroupedOpenApiBeanFactoryPostProcessor implements BeanFactoryPostProcessor,
    EnvironmentAware {

  // <beanName, <groupName, pathsToMatch>>
  private final Map<String, Entry<String, List<String>>> beanNameToGroup = new HashMap<>();
  private Environment environment;

  @Override
  public void setEnvironment(Environment environment) {
    this.environment = environment;
  }

  @Override
  public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory)
      throws BeansException {

    var beanDefinition = getBeanDefinition();

    var properties = Binder
        .get(environment)
        .bind("openapi.request", OpenApiRequestParamProperties.class)
        .get();

    var groups = properties.getGroups();

    for (Entry<String, List<String>> group : groups.entrySet()) {
      String beanName = putGroupDescription(beanNameToGroup, group);
      ((DefaultListableBeanFactory) beanFactory).registerBeanDefinition(beanName, beanDefinition);
    }
  }

  public Map<String, Entry<String, List<String>>> getBeanNameToGroup() {
    return beanNameToGroup;
  }

  private String putGroupDescription(Map<String, Entry<String, List<String>>> beanNameToGroup,
      Entry<String, List<String>> groupEntry) {

    String beanName = groupEntry.getKey().replace("-", "") + "OpenApiGroup";
    groupEntry.setValue(wrapPaths(groupEntry.getValue()));
    beanNameToGroup.put(beanName, groupEntry);
    return beanName;
  }

  private List<String> wrapPaths(List<String> paths) {
    List<String> wrappedPaths = new ArrayList<>();
    for (String path : paths) {
      wrappedPaths.add("/" + path + "/**");
    }
    return wrappedPaths;
  }

  private RootBeanDefinition getBeanDefinition() {
    var beanDefinition = new RootBeanDefinition();
    beanDefinition.setFactoryBeanName("groupedOpenApiBeanPostProcessor");
    beanDefinition.setFactoryMethodName("openApiGroupFactory");
    return beanDefinition;
  }
}