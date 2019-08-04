package com.google.cloud.vision.client;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GoogleCloudVisionClientConrtoller {

  @GetMapping(path = "/google/cloud/vision/client", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<?> get() {

    return ResponseEntity.ok("hello world.");
  }
}
