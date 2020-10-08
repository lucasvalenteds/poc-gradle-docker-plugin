package com.example;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        HttpResponse<String> response = HttpClient.newHttpClient()
            .send(
                HttpRequest.newBuilder().uri(URI.create(System.getenv("SERVER_URL"))).GET().build(),
                HttpResponse.BodyHandlers.ofString()
            );

        System.out.println(response.body());
    }
}
