package com.revature;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class AccountRegistrationTest {

    ApplicationContext app;
    HttpClient webClient;
    ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() throws InterruptedException {
        webClient = HttpClient.newHttpClient();
        objectMapper = new ObjectMapper();
        String[] args = new String[]{};
        app = SpringApplication.run(Application.class, args);
        Thread.sleep(500);
    }

    @AfterEach
    public void tearDown() throws InterruptedException {
        Thread.sleep(500);
        SpringApplication.exit(app);
    }

    @Test
    public void registerAccountSuccessful() throws IOException, InterruptedException {
        String json = """
               {"username": "user",
               "password": "password"
               }
            """;

        HttpRequest postRequest = HttpRequest.newBuilder()
                                             .uri(
                                                 URI.create(
                                                     "http://localhost:8080/accounts/register"))
                                             .POST(
                                                 HttpRequest.BodyPublishers.ofString(
                                                     json))
                                             .header("Content-Type",
                                                     "application/json")
                                             .build();

        HttpResponse<String> response = webClient.send(postRequest,
                                                       HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        Assertions.assertEquals(200, status,
                                "Expected Status Code 200- Actual Code was: " + status);
    }

    @Test
    public void registerAccountDuplicateUsername() throws IOException, InterruptedException {
        String json = """
               {"username": "user",
               "password": "password"
               }
            """;

        HttpRequest postRequest = HttpRequest.newBuilder()
                                             .uri(
                                                 URI.create(
                                                     "http://localhost:8080/accounts/register"))
                                             .POST(
                                                 HttpRequest.BodyPublishers.ofString(
                                                     json))
                                             .header("Content-Type",
                                                     "application/json")
                                             .build();

        HttpResponse<String> response1 = webClient.send(postRequest,
                                                       HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> response2 = webClient.send(postRequest,
                                                        HttpResponse.BodyHandlers.ofString());
        int status1 = response1.statusCode();
        int status2 = response2.statusCode();
        Assertions.assertEquals(200, status1,
                                "Expected Status Code 200- Actual Code was: " + status1);
        Assertions.assertEquals(409, status2,
                                "Expected Status Code 409- Actual Code was: " + status2);
    }

    @Test
    public void registerAccountWithBadInput() throws IOException, InterruptedException {
        String json1 = """
               {"username": "",
               "password": "password"
               }
            """;
        String json2 = """
               {"username": "user",
               "password": "1"
               }
            """;

        HttpRequest postRequest1 = HttpRequest.newBuilder()
                                             .uri(
                                                 URI.create(
                                                     "http://localhost:8080/accounts/register"))
                                             .POST(
                                                 HttpRequest.BodyPublishers.ofString(
                                                     json1))
                                             .header("Content-Type",
                                                     "application/json")
                                             .build();

        HttpRequest postRequest2 = HttpRequest.newBuilder()
                                              .uri(
                                                  URI.create(
                                                      "http://localhost:8080/accounts/register"))
                                              .POST(
                                                  HttpRequest.BodyPublishers.ofString(
                                                      json2))
                                              .header("Content-Type",
                                                      "application/json")
                                              .build();

        HttpResponse<String> response1 = webClient.send(postRequest1,
                                                        HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> response2 = webClient.send(postRequest2,
                                                        HttpResponse.BodyHandlers.ofString());
        int status1 = response1.statusCode();
        int status2 = response2.statusCode();
        Assertions.assertEquals(400, status1,
                                "Expected Status Code 400- Actual Code was: " + status1);
        Assertions.assertEquals(400, status2,
                                "Expected Status Code 400- Actual Code was: " + status2);
    }
}
