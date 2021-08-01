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