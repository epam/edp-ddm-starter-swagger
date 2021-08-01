package com.epam.digital.data.platform.starter.swagger.apiresponse.impl;

import com.epam.digital.data.platform.starter.swagger.apiresponse.validator.ResponseEntityTypeValidator;

import com.epam.digital.data.platform.starter.swagger.config.OpenApiResponseProperties;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

@Component
public class GetForEnumAllValuesApiResponseHandler extends GetApiResponseHandler {

  public GetForEnumAllValuesApiResponseHandler(
      MessageSourceAccessor messageSourceAccessor,
      OpenApiResponseProperties openapiResponseProperties,
      ResponseEntityTypeValidator responseEntityTypeValidator) {
    super(messageSourceAccessor, openapiResponseProperties, responseEntityTypeValidator);
  }

  @Override
  public boolean isApplicable(HandlerMethod handlerMethod) {
    return super.isApplicable(handlerMethod)
        && isHandlingEnumValues(handlerMethod)
        && responseEntityTypeValidator.isReturnGenericCollection(handlerMethod);
  }

  @Override
  protected String getDescriptionCode() {
    return "get-enum-labels";
  }
}
