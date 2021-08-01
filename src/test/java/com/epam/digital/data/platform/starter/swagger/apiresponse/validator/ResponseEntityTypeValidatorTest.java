package com.epam.digital.data.platform.starter.swagger.apiresponse.validator;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.epam.digital.data.platform.starter.swagger.utils.MockDefaultController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.method.HandlerMethod;

@ExtendWith(MockitoExtension.class)
class ResponseEntityTypeValidatorTest {

  private final ResponseEntityTypeValidator responseEntityTypeValidator =
      new ResponseEntityTypeValidator();

  private final MockDefaultController mockDefaultController = new MockDefaultController();

  @Test
  void expectIsReturnGenericCollectionWhenListOfObjectReturned() throws NoSuchMethodException {
    HandlerMethod handlerMethod =
        new HandlerMethod(mockDefaultController, "getGenericListResponse");

    boolean actual = responseEntityTypeValidator.isReturnGenericCollection(handlerMethod);

    assertThat(actual).isTrue();
  }

  @Test
  void expectIsNotReturnGenericCollectionWhenSingleObjectReturned() throws NoSuchMethodException {
    HandlerMethod handlerMethod = new HandlerMethod(mockDefaultController, "getNonListResponse");

    boolean actual = responseEntityTypeValidator.isReturnGenericCollection(handlerMethod);

    assertThat(actual).isFalse();
  }

  @Test
  void expectIsReturnSingleValueWhenSingleObjectReturned() throws NoSuchMethodException {
    HandlerMethod handlerMethod = new HandlerMethod(mockDefaultController, "getNonListResponse");

    boolean actual = responseEntityTypeValidator.isReturnSingleValue(handlerMethod);

    assertThat(actual).isTrue();
  }

  @Test
  void expectIsNotReturnSingleValueWhenListOfObjectsReturned() throws NoSuchMethodException {
    HandlerMethod handlerMethod =
        new HandlerMethod(mockDefaultController, "getGenericListResponse");

    boolean actual = responseEntityTypeValidator.isReturnSingleValue(handlerMethod);

    assertThat(actual).isFalse();
  }
}
