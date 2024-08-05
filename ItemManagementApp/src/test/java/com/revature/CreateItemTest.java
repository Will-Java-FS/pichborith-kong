package com.revature;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.Item;
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

public class CreateItemTest {

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
    public void createItemSuccessful() throws IOException, InterruptedException {
        String json = """
            {"name": "Test_Item",
            "quantity": 166
            }""";
        HttpRequest postMessageRequest = HttpRequest.newBuilder()
                                                    .uri(URI.create(
                                                        "http://localhost:8080/items"))
                                                    .POST(
                                                        HttpRequest.BodyPublishers.ofString(
                                                            json))
                                                    .header("Content-Type",
                                                            "application/json")
                                                    .build();
        HttpResponse<String> response = webClient.send(postMessageRequest,
                                                       HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        Assertions.assertEquals(200, status,
                                "Expected Status Code 200 - Actual Code was: " + status);
        ObjectMapper om = new ObjectMapper();
        Item expectedResult = new Item(6, "Test_Item", 166);
        Item actualResult = om.readValue(response.body(), Item.class);
        Assertions.assertEquals(expectedResult, actualResult,
                                "Expected=" + expectedResult + ", Actual=" + actualResult);
    }

    @Test
    public void createDuplicateItem() throws IOException, InterruptedException {
        String json = """
            {"name": "Dress",
            "quantity": 166
            }""";
        HttpRequest postMessageRequest = HttpRequest.newBuilder()
                                                    .uri(URI.create(
                                                        "http://localhost:8080/items"))
                                                    .POST(
                                                        HttpRequest.BodyPublishers.ofString(
                                                            json))
                                                    .header("Content-Type",
                                                            "application/json")
                                                    .build();
        HttpResponse<String> response = webClient.send(postMessageRequest,
                                                       HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        Assertions.assertEquals(409, status,
                                "Expected Status Code 409 - Actual Code was: " + status);
    }

    @Test
    public void createItemWithBadName() throws IOException, InterruptedException {
        String json = """
            {"name": " ",
            "quantity": 166
            }""";
        HttpRequest postMessageRequest = HttpRequest.newBuilder()
                                                    .uri(URI.create(
                                                        "http://localhost:8080/items"))
                                                    .POST(
                                                        HttpRequest.BodyPublishers.ofString(
                                                            json))
                                                    .header("Content-Type",
                                                            "application/json")
                                                    .build();
        HttpResponse<String> response = webClient.send(postMessageRequest,
                                                       HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        Assertions.assertEquals(400, status,
                                "Expected Status Code 400 - Actual Code was: " + status);
    }
}
