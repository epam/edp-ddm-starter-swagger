package com.epam.digital.data.platform.starter.swagger.utils;

import static org.mockito.Mockito.mock;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MockDefaultController {
  @GetMapping
  public ResponseEntity<List<Integer>> getGenericListResponse() {
    return mock(ResponseEntity.class);
  }

  @GetMapping
  public ResponseEntity<Integer> getNonListResponse() {
    return mock(ResponseEntity.class);
  }

  @PostMapping
  public ResponseEntity<Void> post() {
    return mock(ResponseEntity.class);
  }
}
