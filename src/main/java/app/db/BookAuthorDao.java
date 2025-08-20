package app.db;

import java.sql.*;

public class BookAuthorDao {
    private final Database db;
    public BookAuthorDao(Database db) { this.db = db; }

    public void link(int bookId, int authorId) throws SQLException {
        String sql = "INSERT OR IGNORE INTO book_author(book_id, author_id) VALUES(?,?)";
        try (Connection c = db.get(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, bookId);
            ps.setInt(2, authorId);
            ps.executeUpdate();
        }
    }
}