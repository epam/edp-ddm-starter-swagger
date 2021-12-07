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
