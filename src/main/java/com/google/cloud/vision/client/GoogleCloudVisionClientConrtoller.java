package com.google.cloud.vision.client;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.gax.core.CredentialsProvider;
import com.google.api.gax.rpc.HeaderProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Feature.Type;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.cloud.vision.v1.ImageAnnotatorSettings;
import com.google.cloud.vision.v1.ImageSource;

@RestController
public class GoogleCloudVisionClientConrtoller {

  @GetMapping(path = "/google/cloud/vision/client", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<?> get() throws IOException {

    CredentialsProvider credentialsProvider = () -> GoogleCredentials.fromStream(
        new FileInputStream("path/to/json"));

    HeaderProvider headerProvider = () -> new HashMap<String, String> ();

    ImageAnnotatorSettings imageAnnotatorSettings = ImageAnnotatorSettings.newBuilder()
        .setCredentialsProvider(credentialsProvider).setHeaderProvider(headerProvider)
        .build();

    // Instantiates a client
    try (ImageAnnotatorClient vision = ImageAnnotatorClient.create(imageAnnotatorSettings)) {

      // Builds the image annotation request
      List<AnnotateImageRequest> requests = new ArrayList<>();
      ImageSource source = ImageSource.newBuilder().setGcsImageUri("gs://path/to/object").build();
      Image img = Image.newBuilder().setSource(source).build();
      Feature feat = Feature.newBuilder().setType(Type.SAFE_SEARCH_DETECTION).build();
      AnnotateImageRequest request = AnnotateImageRequest.newBuilder()
          .addFeatures(feat)
          .setImage(img)
          .build();
      requests.add(request);

      // Performs label detection on the image file
      BatchAnnotateImagesResponse response = vision.batchAnnotateImages(requests);
      List<AnnotateImageResponse> responses = response.getResponsesList();

      return ResponseEntity.ok(responses.toString());
    }
  }
}
