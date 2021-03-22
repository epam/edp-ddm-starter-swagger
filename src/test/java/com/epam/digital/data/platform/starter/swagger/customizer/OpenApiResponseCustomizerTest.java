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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.epam.digital.data.platform.starter.swagger.apiresponse.ApiResponseHandler;
import io.swagger.v3.oas.models.Operation;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.method.HandlerMethod;

@ExtendWith(MockitoExtension.class)
class OpenApiResponseCustomizerTest {

  private OpenApiResponseCustomizer customizer;

  @Mock
  private ApiResponseHandler apiResponseHandler;

  @Mock
  private Operation mockOperation;
  @Mock
  private HandlerMethod mockHandlerMethod;

  @BeforeEach
  void beforeEach() {
    List<ApiResponseHandler> apiResponseHandlers = Collections.singletonList(apiResponseHandler);
    customizer = new OpenApiResponseCustomizer(apiResponseHandlers);
  }

  @Test
  void expectHandlerAddsApiResponseWhenApplicable() {
    when(apiResponseHandler.isApplicable(any())).thenReturn(true);

    customizer.customize(mockOperation, mockHandlerMethod);

    verify(apiResponseHandler).handle(mockOperation);
  }

  @Test
  void expectHandlerNotAddsApiResponseWhenNotApplicable() {
    when(apiResponseHandler.isApplicable(any())).thenReturn(false);

    customizer.customize(mockOperation, mockHandlerMethod);

    verify(apiResponseHandler, never()).handle(mockOperation);
  }
}