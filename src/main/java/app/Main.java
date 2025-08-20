package app;

import app.db.Database;
import app.menu.ConsoleMenu;

public class Main {
    public static void main(String[] args) {
        System.out.println("Iniciando Catálogo de Libros (Gutendex)...");

        // Inicializar base de datos SQLite
        Database db = new Database("books.db");

        // Crear menú
        ConsoleMenu menu = new ConsoleMenu(db);

        // Iniciar interacción con usuario
        menu.start();
    }
}