package com.epam.digital.data.platform.starter.swagger.utils;

import static org.mockito.Mockito.mock;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/enum")
public class MockEnumController {
  @GetMapping
  public ResponseEntity<List<Object>> getGenericListResponse() {
    return mock(ResponseEntity.class);
  }

  @GetMapping
  public ResponseEntity<Object> getNonListResponse() {
    return mock(ResponseEntity.class);
  }
}
