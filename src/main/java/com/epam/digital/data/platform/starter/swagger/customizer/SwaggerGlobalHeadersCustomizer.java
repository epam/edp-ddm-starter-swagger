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

import com.epam.digital.data.platform.starter.swagger.config.OpenApiRequestParamProperties;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

@Component
public class SwaggerGlobalHeadersCustomizer implements OperationCustomizer {

  private OpenApiRequestParamProperties openApiRequestParamProperties;

  public SwaggerGlobalHeadersCustomizer(
      OpenApiRequestParamProperties openApiRequestParamProperties) {
    this.openApiRequestParamProperties = openApiRequestParamProperties;
  }

  @Override
  public Operation customize(Operation operation, HandlerMethod handlerMethod) {
    for (String header : openApiRequestParamProperties.getHeaders()) {
      Parameter parameter =
          new Parameter()
              .in(ParameterIn.HEADER.toString())
              .name(header)
              .schema(new StringSchema())
              .required(false);

      operation.addParametersItem(parameter);
    }
    return operation;
  }
}
