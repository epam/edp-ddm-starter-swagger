package com.epam.digital.data.platform.starter.swagger.apiresponse.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.epam.digital.data.platform.starter.swagger.apiresponse.ApiResponseHandler;
import com.epam.digital.data.platform.starter.swagger.apiresponse.validator.ResponseEntityTypeValidator;
import com.epam.digital.data.platform.starter.swagger.config.OpenApiResponseProperties;
import com.epam.digital.data.platform.starter.swagger.utils.MockDefaultController;
import io.swagger.v3.oas.models.Operation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.web.method.HandlerMethod;

import java.util.Map;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
class GetForIdApiResponseHandlerTest {

  private static final String OPERATION_CODE = "get-by-id";
  private static final Set<String> RESPONSE_CODES = Set.of("200", "400", "401", "404", "500", "501");

  private ApiResponseHandler apiResponseHandler;

  private final MockDefaultController mockDefaultController = new MockDefaultController();

  @Mock
  private MessageSourceAccessor messageSourceAccessor;
  @Mock
  private ResponseEntityTypeValidator responseEntityTypeValidator;
  @Mock
  private OpenApiResponseProperties openApiResponseProperties;

  @BeforeEach
  void beforeEach() {
    apiResponseHandler =
        new GetForIdApiResponseHandler(
            messageSourceAccessor, openApiResponseProperties, responseEntityTypeValidator);
  }

  @Test
  void expectIsNotApplicableWhenGenericListIsReturned() throws NoSuchMethodException {
    HandlerMethod handlerMethod =
        new HandlerMethod(mockDefaultController, "getGenericListResponse");
    when(responseEntityTypeValidator.isReturnSingleValue(handlerMethod)).thenReturn(false);

    boolean actual = apiResponseHandler.isApplicable(handlerMethod);

    verify(responseEntityTypeValidator).isReturnSingleValue(handlerMethod);
    assertThat(actual).isFalse();
  }

  @Test
  void expectIsApplicableWhenNonGenericIsReturned() throws NoSuchMethodException {
    HandlerMethod handlerMethod = new HandlerMethod(mockDefaultController, "getNonListResponse");
    when(responseEntityTypeValidator.isReturnSingleValue(handlerMethod)).thenReturn(true);

    boolean actual = apiResponseHandler.isApplicable(handlerMethod);

    verify(responseEntityTypeValidator).isReturnSingleValue(handlerMethod);
    assertThat(actual).isTrue();
  }

  @Test
  void expectIsNotApplicableWhenNonGetRequest() throws NoSuchMethodException {
    HandlerMethod handlerMethod = new HandlerMethod(mockDefaultController, "post");

    boolean actual = apiResponseHandler.isApplicable(handlerMethod);

    assertThat(actual).isFalse();
  }

  @Test
  void expectNewResponsesAreAddedWhenNoExisting() {
    when(openApiResponseProperties.getCodes())
            .thenReturn(Map.of(OPERATION_CODE, RESPONSE_CODES));
    Operation processedOperation = new Operation();

    apiResponseHandler.handle(processedOperation);

    assertThat(processedOperation.getResponses())
        .containsOnlyKeys(RESPONSE_CODES);
  }
}
