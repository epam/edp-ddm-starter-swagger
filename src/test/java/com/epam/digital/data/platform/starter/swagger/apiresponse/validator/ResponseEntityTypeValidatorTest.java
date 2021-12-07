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
