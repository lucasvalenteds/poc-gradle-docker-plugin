package com.example;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        String serverUrl = "http://localhost:" + System.getenv("PORT");
        HttpResponse<String> response = HttpClient.newHttpClient().send(
            HttpRequest.newBuilder().uri(URI.create(serverUrl)).GET().build(),
            HttpResponse.BodyHandlers.ofString()
        );

        assert response.statusCode() == 200;
        assert response.body().trim().equals("Hello world!");

        System.out.println("Server URL: " + serverUrl);
        System.out.println("Status code: " + response.statusCode());
        System.out.println("Response body: " + response.body().trim());
    }
}
