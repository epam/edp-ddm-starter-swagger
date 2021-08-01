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
