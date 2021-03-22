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
import com.epam.digital.data.platform.starter.swagger.apiresponse.validator.ResponseEntityTypeValidator;
import java.util.Optional;

import com.epam.digital.data.platform.starter.swagger.config.OpenApiResponseProperties;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;

public abstract class GetApiResponseHandler extends AbstractApiResponseHandler {

  private static final String ENUM_CONTROLLER_PREFIX = "/enum";

  protected ResponseEntityTypeValidator responseEntityTypeValidator;

  protected GetApiResponseHandler(
      MessageSourceAccessor messageSourceAccessor,
      OpenApiResponseProperties openapiResponseProperties,
      ResponseEntityTypeValidator responseEntityTypeValidator) {
    super(messageSourceAccessor, openapiResponseProperties);
    this.responseEntityTypeValidator = responseEntityTypeValidator;
  }

  @Override
  public boolean isApplicable(HandlerMethod handlerMethod) {
    return handlerMethod.hasMethodAnnotation(GetMapping.class);
  }

  protected boolean isHandlingEnumValues(HandlerMethod handlerMethod) {
    return Optional.of(handlerMethod.getBeanType())
        .map(beanClass -> beanClass.getDeclaredAnnotation(RequestMapping.class))
        .map(RequestMapping::value)
        .map(requestMappingValue -> requestMappingValue[0])
        .map(ENUM_CONTROLLER_PREFIX::equals)
        .orElse(false);
  }
}
