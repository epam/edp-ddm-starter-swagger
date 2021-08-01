package com.epam.digital.data.platform.starter.swagger.apiresponse.impl;

import com.epam.digital.data.platform.starter.swagger.apiresponse.AbstractApiResponseHandler;
import com.epam.digital.data.platform.starter.swagger.config.OpenApiResponseProperties;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.method.HandlerMethod;

@Component
public class DeleteApiResponseHandler extends AbstractApiResponseHandler {

  public DeleteApiResponseHandler(
      MessageSourceAccessor messageSourceAccessor,
      OpenApiResponseProperties openapiResponseProperties) {
    super(messageSourceAccessor, openapiResponseProperties);
  }

  @Override
  protected String getDescriptionCode() {
    return "delete";
  }

  @Override
  public boolean isApplicable(HandlerMethod handlerMethod) {
    return handlerMethod.hasMethodAnnotation(DeleteMapping.class);
  }
}
