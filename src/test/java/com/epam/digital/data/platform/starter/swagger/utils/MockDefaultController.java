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

package com.epam.digital.data.platform.starter.swagger.utils;

import static org.mockito.Mockito.mock;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
  
  @PutMapping
  public ResponseEntity<Void> put() {
    return mock(ResponseEntity.class);
  }
}
