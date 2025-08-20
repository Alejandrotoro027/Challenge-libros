package app.models;

import java.util.Objects;

public class Book {
    private Integer id;             // ID interno en la base de datos
    private int gutendexId;         // ID de Gutendex
    private String title;           // Título del libro
    private String author;          // Primer autor
    private String language;        // Primer idioma
    private Integer downloadCount;  // Número de descargas (puede ser null)

    // Constructor completo
    public Book(Integer id, int gutendexId, String title, String author, String language, Integer downloadCount) {
        this.id = id;
        this.gutendexId = gutendexId;
        this.title = title;
        this.author = author;
        this.language = language;
        this.downloadCount = downloadCount;
    }

    // Constructor sin id (cuando todavía no está guardado en DB)
    public Book(int gutendexId, String title, String author, String language, Integer downloadCount) {
        this(null, gutendexId, title, author, language, downloadCount);
    }

    // Getters y setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public int getGutendexId() { return gutendexId; }
    public void setGutendexId(int gutendexId) { this.gutendexId = gutendexId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }

    public Integer getDownloadCount() { return downloadCount; }
    public void setDownloadCount(Integer downloadCount) { this.downloadCount = downloadCount; }

    @Override
    public String toString() {
        return String.format("[%d] %s | Autor: %s | Idioma: %s | Descargas: %s",
                gutendexId,
                title,
                author != null ? author : "N/A",
                language != null ? language : "N/A",
                downloadCount != null ? downloadCount : "N/A"
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return gutendexId == book.gutendexId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(gutendexId);
    }
}
