package com.epam.digital.data.platform.starter.swagger.customizer;

import com.epam.digital.data.platform.starter.swagger.apiresponse.ApiResponseHandler;
import io.swagger.v3.oas.models.Operation;
import java.util.List;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

@Component
public class OpenApiResponseCustomizer implements OperationCustomizer {

  private List<ApiResponseHandler> apiResponseHandlers;

  public OpenApiResponseCustomizer(List<ApiResponseHandler> apiResponseHandlers) {
    this.apiResponseHandlers = apiResponseHandlers;
  }

  @Override
  public Operation customize(Operation operation, HandlerMethod handlerMethod) {
    apiResponseHandlers.stream()
        .filter(apiResponseHandler -> apiResponseHandler.isApplicable(handlerMethod))
        .forEach(apiResponseHandler -> apiResponseHandler.handle(operation));
    return operation;
  }
}
