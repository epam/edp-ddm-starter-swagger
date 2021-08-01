package com.epam.digital.data.platform.starter.swagger.apiresponse.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.epam.digital.data.platform.starter.swagger.apiresponse.ApiResponseHandler;
import com.epam.digital.data.platform.starter.swagger.apiresponse.validator.ResponseEntityTypeValidator;
import com.epam.digital.data.platform.starter.swagger.config.OpenApiResponseProperties;
import com.epam.digital.data.platform.starter.swagger.utils.MockDefaultController;
import com.epam.digital.data.platform.starter.swagger.utils.MockEnumController;
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
class GetForEnumAllValuesApiResponseHandlerTest {

  private static final String OPERATION_CODE = "get-enum-labels";
  private static final Set<String> RESPONSE_CODES = Set.of("200", "401", "500", "501");

  private ApiResponseHandler apiResponseHandler;

  private final MockEnumController mockEnumController = new MockEnumController();
  private final MockDefaultController mockDefaultController = new MockDefaultController();

  @Mock
  private MessageSourceAccessor messageSourceAccessor;
  @Mock
  private OpenApiResponseProperties openapiResponseProperties;
  @Mock
  private ResponseEntityTypeValidator responseEntityTypeValidator;

  @BeforeEach
  void beforeEach() {
    apiResponseHandler =
        new GetForEnumAllValuesApiResponseHandler(
            messageSourceAccessor, openapiResponseProperties, responseEntityTypeValidator);
  }

  @Test
  void expectIsApplicableWhenGenericListIsReturned() throws NoSuchMethodException {
    HandlerMethod handlerMethod = new HandlerMethod(mockEnumController, "getGenericListResponse");
    when(responseEntityTypeValidator.isReturnGenericCollection(handlerMethod)).thenReturn(true);

    boolean actual = apiResponseHandler.isApplicable(handlerMethod);

    verify(responseEntityTypeValidator).isReturnGenericCollection(handlerMethod);
    assertThat(actual).isTrue();
  }

  @Test
  void expectIsNotApplicableWhenNonListIsReturned() throws NoSuchMethodException {
    HandlerMethod handlerMethod = new HandlerMethod(mockEnumController, "getNonListResponse");
    when(responseEntityTypeValidator.isReturnGenericCollection(handlerMethod)).thenReturn(false);

    boolean actual = apiResponseHandler.isApplicable(handlerMethod);

    verify(responseEntityTypeValidator).isReturnGenericCollection(handlerMethod);
    assertThat(actual).isFalse();
  }

  @Test
  void expectIsNotApplicableWhenNotEnumsAreHandling() throws NoSuchMethodException {
    HandlerMethod handlerMethod = new HandlerMethod(mockDefaultController, "getNonListResponse");

    boolean actual = apiResponseHandler.isApplicable(handlerMethod);

    assertThat(actual).isFalse();
  }

  @Test
  void expectNewResponsesAreAddedWhenNoExisting() {
    when(openapiResponseProperties.getCodes()).thenReturn(Map.of(OPERATION_CODE, RESPONSE_CODES));
    Operation processedOperation = new Operation();

    apiResponseHandler.handle(processedOperation);

    assertThat(processedOperation.getResponses()).containsOnlyKeys(RESPONSE_CODES);
  }
}
