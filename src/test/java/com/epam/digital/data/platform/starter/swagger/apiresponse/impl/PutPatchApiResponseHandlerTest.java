package com.epam.digital.data.platform.starter.swagger.apiresponse.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.epam.digital.data.platform.starter.swagger.apiresponse.ApiResponseHandler;
import com.epam.digital.data.platform.starter.swagger.config.OpenApiResponseProperties;
import io.swagger.v3.oas.models.Operation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.support.MessageSourceAccessor;

import java.util.Map;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
class PutPatchApiResponseHandlerTest {

  private static final String OPERATION_CODE = "put";
  private static final Set<String> RESPONSE_CODES = Set.of("204", "400", "401", "403", "409", "412", "422", "500", "501");

  private ApiResponseHandler apiResponseHandler;

  @Mock
  private MessageSourceAccessor messageSourceAccessor;
  @Mock
  private OpenApiResponseProperties openApiResponseProperties;

  @BeforeEach
  void beforeEach() {
    apiResponseHandler =
        new PutPatchApiResponseHandler(messageSourceAccessor, openApiResponseProperties);

    when(openApiResponseProperties.getCodes())
            .thenReturn(Map.of(OPERATION_CODE, RESPONSE_CODES));
  }

  @Test
  void expectNewResponsesAreAddedWhenNoExisting() {
    Operation processedOperation = new Operation();

    apiResponseHandler.handle(processedOperation);

    assertThat(processedOperation.getResponses())
        .containsOnlyKeys(RESPONSE_CODES);
  }
}
