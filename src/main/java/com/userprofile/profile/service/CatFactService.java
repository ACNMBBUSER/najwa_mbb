package com.userprofile.profile.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.userprofile.profile.model.response.CatFactResponse;
import com.userprofile.profile.model.response.ResponseHandler;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@Service
public class CatFactService {

    ResponseHandler<CatFactResponse> responseHandler = new ResponseHandler<>();

    private final Environment environment;

    public RestTemplate restTemplate = new RestTemplate();

    CompletableFuture<String> completableFuture = new CompletableFuture<>();

    private final ObjectMapper objectMapper; // ObjectMapper for JSON parsing

    public CatFactService(Environment environment, RestTemplateBuilder restTemplateBuilder, ObjectMapper objectMapper) {
        this.environment = environment;
        this.restTemplate = restTemplateBuilder.build();
        this.objectMapper = objectMapper;

    }

    public String getCatFacts(){
        String catUrl = environment.getProperty("app.catUrl");
        ResponseEntity<String> response = restTemplate.exchange(catUrl,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<String>() {
                });

        if (response.getStatusCode().is2xxSuccessful()) {
            try {
                // Parse the JSON string into a JSON object
                String responseBody = response.getBody();
                CatFactResponse catFact = objectMapper.readValue(responseBody, CatFactResponse.class);
                return catFact.getFact();
            } catch (Exception e) {
                e.printStackTrace(); // Handle parsing exception
                return responseHandler.generateFailResponse("Failed to parse cat facts").toString();
            }
        } else {
            // Handle error response
            return responseHandler.generateFailResponse("Failed to fetch cat facts").toString();
        }
    }



}
