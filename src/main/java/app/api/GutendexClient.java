package app.api;

import app.api.dto.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class GutendexClient {
    private final String baseUrl;
    private final HttpClient http;
    private final Gson gson = new GsonBuilder().create();

    public GutendexClient(String baseUrl) {
        this.baseUrl = baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length()-1) : baseUrl;
        this.http = HttpClient.newHttpClient();
    }

    public ApiSearchResponse searchBooks(String query) {
        String q = URLEncoder.encode(query, StandardCharsets.UTF_8);
        String url = baseUrl + "/books?search=" + q;
        return get(url, ApiSearchResponse.class).orElseGet(ApiSearchResponse::new);
    }

    public ApiSearchResponse searchByAuthor(String author) {
        String q = URLEncoder.encode(author, StandardCharsets.UTF_8);
        String url = baseUrl + "/books?search=" + q; // Gutendex busca por texto libre (incluye autor)
        return get(url, ApiSearchResponse.class).orElseGet(ApiSearchResponse::new);
    }

    public Optional<ApiBookDTO> getBook(int id) {
        String url = baseUrl + "/books/" + id;
        return get(url, ApiBookDTO.class);
    }

    private <T> Optional<T> get(String url, Class<T> type) {
        try {
            HttpRequest req = HttpRequest.newBuilder(URI.create(url)).GET().build();
            HttpResponse<String> res = http.send(req, HttpResponse.BodyHandlers.ofString());
            if (res.statusCode() >= 200 && res.statusCode() < 300) {
                return Optional.of(gson.fromJson(res.body(), type));
            } else {
                System.err.println("HTTP " + res.statusCode() + " → " + url);
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Error HTTP: " + e.getMessage());
        }
        return Optional.empty();
    }
}