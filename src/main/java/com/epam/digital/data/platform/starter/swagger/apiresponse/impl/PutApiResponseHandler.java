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

package com.epam.digital.data.platform.starter.swagger.apiresponse.impl;

import com.epam.digital.data.platform.starter.swagger.apiresponse.AbstractApiResponseHandler;
import com.epam.digital.data.platform.starter.swagger.config.OpenApiResponseProperties;
import java.util.Optional;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;

public abstract class PutApiResponseHandler extends AbstractApiResponseHandler {
  
  private static final String NESTED_CONTROLLER_PREFIX = "/nested";

  PutApiResponseHandler(
      MessageSourceAccessor messageSourceAccessor,
      OpenApiResponseProperties openapiResponseProperties) {
    super(messageSourceAccessor, openapiResponseProperties);
  }

  @Override
  public boolean isApplicable(HandlerMethod handlerMethod) {
    return handlerMethod.hasMethodAnnotation(PutMapping.class);
  }

  protected boolean isHandlingNestedEntity(HandlerMethod handlerMethod) {
    return Optional.of(handlerMethod.getBeanType())
        .map(beanClass -> beanClass.getDeclaredAnnotation(RequestMapping.class))
        .map(RequestMapping::value)
        .map(requestMappingValue -> requestMappingValue[0])
        .map(NESTED_CONTROLLER_PREFIX::equals)
        .orElse(false);
  }
}
