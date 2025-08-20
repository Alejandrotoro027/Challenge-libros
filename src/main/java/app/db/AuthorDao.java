package app.db;

import app.model.Author;

import java.sql.*;
import java.util.*;

public class AuthorDao {
    private final Database db;
    public AuthorDao(Database db) { this.db = db; }

    public int upsert(Author a) throws SQLException {
        String insert = "INSERT INTO authors(name, birth_year, death_year) VALUES(?,?,?) ON CONFLICT(name) DO UPDATE SET birth_year=excluded.birth_year, death_year=excluded.death_year RETURNING id";
        try (Connection c = db.get(); PreparedStatement ps = c.prepareStatement(insert)) {
            ps.setString(1, a.getName());
            if (a.getBirthYear() == null) ps.setNull(2, Types.INTEGER); else ps.setInt(2, a.getBirthYear());
            if (a.getDeathYear() == null) ps.setNull(3, Types.INTEGER); else ps.setInt(3, a.getDeathYear());
            try (ResultSet rs = ps.executeQuery()) { if (rs.next()) return rs.getInt(1); }
        }
        throw new SQLException("No se pudo upsert autor");
    }

    public List<Author> listAll() throws SQLException {
        String sql = "SELECT id, name, birth_year, death_year FROM authors ORDER BY name";
        List<Author> out = new ArrayList<>();
        try (Connection c = db.get(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                out.add(new Author(
                        rs.getInt("id"), rs.getString("name"),
                        (Integer) rs.getObject("birth_year"),
                        (Integer) rs.getObject("death_year")
                ));
            }
        }
        return out;
    }
}