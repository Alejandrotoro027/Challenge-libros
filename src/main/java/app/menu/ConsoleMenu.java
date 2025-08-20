package app.menu;

import app.db.BookDao;
import app.db.Database;
import app.models.Book;
import app.services.GutendexService;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class ConsoleMenu {
    private final BookDao dao;
    private final GutendexService service;
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleMenu(Database db) {
        this.dao = new BookDao(db);
        this.service = new GutendexService();
    }

    public void start() {
        while (true) {
            System.out.println("\n===== Catálogo de Libros =====");
            System.out.println("1. Buscar libro por título (API Gutendex)");
            System.out.println("2. Guardar libro en base de datos");
            System.out.println("3. Listar todos los libros guardados");
            System.out.println("4. Listar libros por idioma");
            System.out.println("0. Salir");
            System.out.print("Elige una opción: ");

            int opt;
            try {
                opt = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Opción inválida. Intenta de nuevo.");
                continue;
            }

            try {
                switch (opt) {
                    case 1 -> buscarLibro();
                    case 2 -> guardarLibro();
                    case 3 -> listarLibros();
                    case 4 -> listarPorIdioma();
                    case 0 -> {
                        System.out.println("¡Adiós!");
                        return;
                    }
                    default -> System.out.println("⚠️ Opción no válida");
                }
            } catch (Exception e) {
                System.out.println("⚠️ Ocurrió un error: " + e.getClass().getName() + " - " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void buscarLibro() throws Exception {
        System.out.print("Título a buscar: ");
        String title = scanner.nextLine();

        List<Book> results = service.searchByTitle(title);

        if (results == null || results.isEmpty()) {
            System.out.println("⚠️ No se encontraron libros con ese título.");
            return;
        }

        System.out.println("📚 Resultados encontrados:");
        results.forEach(System.out::println);
    }

    private void guardarLibro() throws Exception {
        System.out.print("ID de Gutendex a guardar: ");
        int gutId = Integer.parseInt(scanner.nextLine());
        Book b = service.findByGutendexId(gutId);
        if (b == null) {
            System.out.println("⚠️ Libro no encontrado en Gutendex.");
            return;
        }
        dao.upsert(b);
        System.out.println("✅ Libro guardado en DB: " + b);
    }

    private void listarLibros() throws SQLException {
        List<Book> all = dao.listAll();
        if (all.isEmpty()) {
            System.out.println("⚠️ No hay libros guardados en la base de datos.");
            return;
        }
        all.forEach(System.out::println);
    }

    private void listarPorIdioma() throws SQLException {
        System.out.print("Idioma (ej: en, es, fr): ");
        String lang = scanner.nextLine();
        List<Book> books = dao.listByLanguage(lang);
        if (books.isEmpty()) {
            System.out.println("⚠️ No hay libros guardados en ese idioma.");
            return;
        }
        books.forEach(System.out::println);
    }
}


