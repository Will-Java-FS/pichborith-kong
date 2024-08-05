package com.revature;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.revature.models.Account;

public class AccountLoginTest {
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
    public void loginSuccessful() throws IOException, InterruptedException {
        String json = """
            {"username":"bo",
            "password":"123"
            }
            """;
        HttpRequest postRequest = HttpRequest.newBuilder()
                                             .uri(
                                                 URI.create(
                                                     "http://localhost:8080/accounts/login"))
                                             .POST(
                                                 HttpRequest.BodyPublishers.ofString(
                                                     json))
                                             .header("Content-Type",
                                                     "application/json")
                                             .build();
        HttpResponse<String> response = webClient.send(postRequest,
                                                       HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        Assertions.assertEquals(200, status);
        ObjectMapper om = new ObjectMapper();
        Account expectedResult = new Account(1, "bo", "123");
        Account actualResult = om.readValue(response.body(),
                                            Account.class);
        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void loginInvalidUsername() throws IOException, InterruptedException {
        String json = """
            {"username":"random",
            "password":"123"
            }
            """;
        HttpRequest postRequest = HttpRequest.newBuilder()
                                             .uri(
                                                 URI.create(
                                                     "http://localhost:8080/accounts/login"))
                                             .POST(
                                                 HttpRequest.BodyPublishers.ofString(
                                                     json))
                                             .header("Content-Type",
                                                     "application/json")
                                             .build();
        HttpResponse<String> response = webClient.send(postRequest,
                                                       HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        Assertions.assertEquals(401, status, "Expected Status Code 401 - Actual Code was: "+ status);
    }

    @Test
    public void loginInvalidPassword() throws IOException, InterruptedException {
        String json = """
            {"username":"bo",
            "password":"wrongpassword"
            }
            """;
        HttpRequest postRequest = HttpRequest.newBuilder()
                                             .uri(
                                                 URI.create(
                                                     "http://localhost:8080/accounts/login"))
                                             .POST(
                                                 HttpRequest.BodyPublishers.ofString(
                                                     json))
                                             .header("Content-Type",
                                                     "application/json")
                                             .build();
        HttpResponse<String> response = webClient.send(postRequest,
                                                       HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        Assertions.assertEquals(401, status, "Expected Status Code 401 - Actual Code was: "+ status);
    }
}
