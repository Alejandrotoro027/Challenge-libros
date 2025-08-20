package app.services;

import app.db.BookDao;
import app.models.Book;
import app.db.Database;

import java.sql.SQLException;
import java.util.List;

public class BookService {
    private final BookDao bookDao;

    public BookService(Database db) {
        this.bookDao = new BookDao(db);
    }

    // Guardar libro en la BD (insert o update)
    public void save(Book book) throws SQLException {
        bookDao.upsert(book);
    }

    // Listar todos los libros guardados
    public List<Book> listAll() throws SQLException {
        return bookDao.listAll();
    }

    // Listar libros por idioma
    public List<Book> listByLanguage(String lang) throws SQLException {
        return bookDao.listByLanguage(lang);
    }

    // Top libros por descargas
    public List<Book> topDownloads(int limit) throws SQLException {
        return bookDao.topDownloads(limit);
    }
}


