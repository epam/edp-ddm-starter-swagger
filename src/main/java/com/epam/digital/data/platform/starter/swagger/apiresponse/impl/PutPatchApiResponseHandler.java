package com.epam.digital.data.platform.starter.swagger.apiresponse.impl;

import com.epam.digital.data.platform.starter.swagger.apiresponse.AbstractApiResponseHandler;
import com.epam.digital.data.platform.starter.swagger.config.OpenApiResponseProperties;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.method.HandlerMethod;

@Component
public class PutPatchApiResponseHandler extends AbstractApiResponseHandler {
  public PutPatchApiResponseHandler(
      MessageSourceAccessor messageSourceAccessor,
      OpenApiResponseProperties openapiResponseProperties) {
    super(messageSourceAccessor, openapiResponseProperties);
  }

  @Override
  protected String getDescriptionCode() {
    return "put";
  }

  @Override
  public boolean isApplicable(HandlerMethod handlerMethod) {
    return handlerMethod.hasMethodAnnotation(PutMapping.class)
        || handlerMethod.hasMethodAnnotation(PatchMapping.class);
  }
}
