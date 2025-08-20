package app.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private final String url;

    // Constructor que recibe el nombre del archivo de la BD SQLite
    public Database(String fileName) {
        this.url = "jdbc:sqlite:" + fileName;
        init();
    }

    // Crea la tabla si no existe
    private void init() {
        try (Connection conn = get(); Statement st = conn.createStatement()) {
            st.execute("""
                CREATE TABLE IF NOT EXISTS books (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    gutendex_id INTEGER UNIQUE,
                    title TEXT NOT NULL,
                    download_count INTEGER,
                    languages TEXT
                )
            """);
        } catch (SQLException e) {
            throw new RuntimeException("Error inicializando la base de datos", e);
        }
    }

    // Devuelve conexión
    public Connection get() throws SQLException {
        return DriverManager.getConnection(url);
    }
}
