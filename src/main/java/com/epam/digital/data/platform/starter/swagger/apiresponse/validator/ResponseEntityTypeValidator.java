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

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

@Component
public class ResponseEntityTypeValidator {

  public boolean isReturnGenericCollection(HandlerMethod handlerMethod) {
    Type responseType = handlerMethod.getMethod().getGenericReturnType();
    if (responseType instanceof ParameterizedType) {
      ParameterizedType parameterizedResponseType = (ParameterizedType) responseType;
      Type genericType = parameterizedResponseType.getActualTypeArguments()[0];
      if (genericType instanceof ParameterizedType) {
        ParameterizedType parameterizedInnerGenericType = (ParameterizedType) genericType;
        Class<?> genericClass = (Class<?>) parameterizedInnerGenericType.getRawType();
        return Collection.class.isAssignableFrom(genericClass);
      }
    }
    return false;
  }

  public boolean isReturnSingleValue(HandlerMethod handlerMethod) {
    Type responseType = handlerMethod.getMethod().getGenericReturnType();
    if (responseType instanceof ParameterizedType) {
      ParameterizedType parameterizedResponseType = (ParameterizedType) responseType;
      Type genericType = parameterizedResponseType.getActualTypeArguments()[0];
      return genericType instanceof Class;
    }
    return false;
  }
}
