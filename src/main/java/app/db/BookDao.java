package app.db;

import app.models.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDao {
    private final Database db;

    public BookDao(Database db) {
        this.db = db;
        createTableIfNotExists();
    }

    private void createTableIfNotExists() {
        String sql = """
                CREATE TABLE IF NOT EXISTS books (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    gutendex_id INTEGER UNIQUE,
                    title TEXT,
                    author TEXT,
                    language TEXT,
                    download_count INTEGER
                )
                """;
        try (Connection conn = db.get(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void upsert(Book b) throws SQLException {
        String sql = """
                INSERT INTO books (gutendex_id, title, author, language, download_count)
                VALUES (?, ?, ?, ?, ?)
                ON CONFLICT(gutendex_id) DO UPDATE SET
                    title=excluded.title,
                    author=excluded.author,
                    language=excluded.language,
                    download_count=excluded.download_count
                """;
        try (Connection conn = db.get(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, b.getGutendexId());
            ps.setString(2, b.getTitle());
            ps.setString(3, b.getAuthor());      // autor
            ps.setString(4, b.getLanguage());    // primer idioma
            if (b.getDownloadCount() != null) {
                ps.setInt(5, b.getDownloadCount());
            } else {
                ps.setNull(5, Types.INTEGER);
            }
            ps.executeUpdate();
        }
    }

    public List<Book> listAll() throws SQLException {
        List<Book> list = new ArrayList<>();
        String sql = "SELECT gutendex_id, title, author, language, download_count FROM books";
        try (Connection conn = db.get(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Book(
                        rs.getInt("gutendex_id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("language"),
                        rs.getInt("download_count")
                ));
            }
        }
        return list;
    }

    public List<Book> listByLanguage(String lang) throws SQLException {
        List<Book> list = new ArrayList<>();
        String sql = "SELECT gutendex_id, title, author, language, download_count FROM books WHERE language = ?";
        try (Connection conn = db.get(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, lang);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Book(
                            rs.getInt("gutendex_id"),
                            rs.getString("title"),
                            rs.getString("author"),
                            rs.getString("language"),
                            rs.getInt("download_count")
                    ));
                }
            }
        }
        return list;
    }

    public List<Book> topDownloads(int limit) throws SQLException {
        List<Book> list = new ArrayList<>();
        String sql = "SELECT gutendex_id, title, author, language, download_count FROM books " +
                "ORDER BY download_count DESC LIMIT ?";
        try (Connection conn = db.get(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, limit);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Book(
                            rs.getInt("gutendex_id"),
                            rs.getString("title"),
                            rs.getString("author"),
                            rs.getString("language"),
                            rs.getInt("download_count")
                    ));
                }
            }
        }
        return list;
    }
}


