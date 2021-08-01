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
