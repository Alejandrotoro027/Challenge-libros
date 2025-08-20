package app.services;

import app.models.Book;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class GutendexService {
    private static final String BASE_URL = "https://gutendex.com/books";
    private final HttpClient client;
    private final ObjectMapper mapper;

    public GutendexService() {
        this.client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .build();
        this.mapper = new ObjectMapper();
    }

    /**
     * Busca libros en Gutendex por título
     */
    public List<Book> searchByTitle(String title) throws IOException, InterruptedException {
        String url = BASE_URL + "?search=" + title.replace(" ", "%20");
        System.out.println("📡 Consultando URL: " + url);

        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("User-Agent", "JavaHttpClient")
                .GET()
                .build();

        HttpResponse<String> res = client.send(req, HttpResponse.BodyHandlers.ofString());

        if (res.statusCode() != 200) {
            System.out.println("⚠️ Respuesta no OK (" + res.statusCode() + "): " + res.body());
            throw new RuntimeException("Error en la API Gutendex: " + res.statusCode());
        }

        JsonNode root = mapper.readTree(res.body());
        List<Book> books = new ArrayList<>();
        for (JsonNode item : root.get("results")) {
            books.add(parseBook(item));
        }
        return books;
    }

    /**
     * Busca un libro específico en Gutendex por ID
     */
    public Book findByGutendexId(int id) throws IOException, InterruptedException {
        String url = BASE_URL + "/" + id;
        System.out.println("📡 Consultando URL: " + url);

        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("User-Agent", "JavaHttpClient")
                .GET()
                .build();

        HttpResponse<String> res = client.send(req, HttpResponse.BodyHandlers.ofString());

        if (res.statusCode() != 200) {
            System.out.println("⚠️ Respuesta no OK (" + res.statusCode() + "): " + res.body());
            return null;
        }

        JsonNode item = mapper.readTree(res.body());
        return parseBook(item);
    }

    /**
     * Convierte un nodo JSON en un objeto Book
     */
    private Book parseBook(JsonNode item) {
        int gutendexId = item.get("id").asInt();
        String title = item.get("title").asText();
        Integer downloads = item.has("download_count") && !item.get("download_count").isNull()
                ? item.get("download_count").asInt()
                : null;

        // Primer idioma
        String language = null;
        if (item.has("languages") && item.get("languages").size() > 0) {
            language = item.get("languages").get(0).asText();
        }

        // Primer autor
        String author = null;
        if (item.has("authors") && item.get("authors").size() > 0) {
            JsonNode firstAuthor = item.get("authors").get(0);
            if (firstAuthor.has("name")) {
                author = firstAuthor.get("name").asText();
            }
        }

        return new Book(gutendexId, title, author, language, downloads);
    }
}

