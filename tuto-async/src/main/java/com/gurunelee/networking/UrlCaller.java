package com.gurunelee.networking;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class UrlCaller {

    private final HttpClient client;

    public UrlCaller() {
        this.client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .build();
    }

    public String call(String url) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .timeout(Duration.ofSeconds(10))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            return "ERROR: " + e.getMessage();
        }
    }

    public Long callAndMeasure(String url) {
        long startTime = System.currentTimeMillis();
        call(url);
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }
}
