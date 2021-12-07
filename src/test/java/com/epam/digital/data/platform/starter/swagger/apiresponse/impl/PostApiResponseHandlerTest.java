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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.epam.digital.data.platform.starter.swagger.apiresponse.ApiResponseHandler;
import com.epam.digital.data.platform.starter.swagger.config.OpenApiResponseProperties;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.support.MessageSourceAccessor;

import java.util.Map;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
class PostApiResponseHandlerTest {

  private static final String OPERATION_CODE = "post";
  private static final Set<String> RESPONSE_CODES =
      Set.of("201", "400", "401", "403", "409", "412", "422", "500", "501");

  private ApiResponseHandler apiResponseHandler;

  @Mock
  private MessageSourceAccessor messageSourceAccessor;
  @Mock
  private OpenApiResponseProperties openApiResponseProperties;

  @BeforeEach
  void beforeEach() {
    apiResponseHandler =
        new PostApiResponseHandler(messageSourceAccessor, openApiResponseProperties);

    when(openApiResponseProperties.getCodes())
            .thenReturn(Map.of(OPERATION_CODE, RESPONSE_CODES));
  }

  @Test
  void expectNewResponsesAreAddedWhenNoExisting() {
    Operation processedOperation = new Operation();

    apiResponseHandler.handle(processedOperation);

    assertThat(processedOperation.getResponses())
        .containsOnlyKeys(RESPONSE_CODES);
    verify(messageSourceAccessor, times(11)).getMessage(anyString());
  }

  @Test
  void expectExistingResponseIsRemovedWhenNonMatchingCodes() {
    Operation processedOperation = new Operation();
    processedOperation.setResponses(new ApiResponses().addApiResponse("200", new ApiResponse()));

    apiResponseHandler.handle(processedOperation);

    assertThat(processedOperation.getResponses())
        .containsOnlyKeys(RESPONSE_CODES);
    verify(messageSourceAccessor, times(11)).getMessage(anyString());
  }

  @Test
  void expectExistingResponseIsUpdatedWhenMatchingCodes() {
    Operation processedOperation = new Operation();
    processedOperation.setResponses(
        new ApiResponses().addApiResponse("201", new ApiResponse().$ref("ref")));

    apiResponseHandler.handle(processedOperation);

    assertThat(processedOperation.getResponses())
        .containsOnlyKeys(RESPONSE_CODES);
    assertThat(processedOperation.getResponses().get("201").get$ref()).isNotNull();
    verify(messageSourceAccessor, times(11)).getMessage(anyString());
  }
}
