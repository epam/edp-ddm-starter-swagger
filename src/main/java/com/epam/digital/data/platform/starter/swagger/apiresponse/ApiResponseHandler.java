package com.epam.digital.data.platform.starter.swagger.apiresponse;

import io.swagger.v3.oas.models.Operation;
import org.springframework.web.method.HandlerMethod;

public interface ApiResponseHandler {

  boolean isApplicable(HandlerMethod handlerMethod);

  void handle(Operation operation);
}
