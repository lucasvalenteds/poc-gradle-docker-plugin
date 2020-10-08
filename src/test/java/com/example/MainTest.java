package com.example;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MainTest {

    @Test
    void test() throws IOException, InterruptedException {
        HttpResponse<String> response = HttpClient.newHttpClient()
            .send(
                HttpRequest.newBuilder().uri(URI.create(System.getenv("SERVER_URL"))).GET().build(),
                HttpResponse.BodyHandlers.ofString()
            );

        assertEquals(200, response.statusCode());
        assertEquals("Hello world!\n", response.body());
    }
}
