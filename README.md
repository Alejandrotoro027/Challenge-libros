# Catálogo de Libros (Gutendex)

Este proyecto en Java permite buscar, listar y guardar libros utilizando la **API de Gutendex** y una base de datos **SQLite**.

---

## Funcionalidades

1. **Buscar libro por título**  
   Consulta la API de Gutendex y obtiene los datos del libro, incluyendo:
   - Título
   - Autor
   - Idioma
   - Número de descargas

2. **Guardar libro en la base de datos**  
   Permite almacenar libros consultados para poder listarlos posteriormente.

3. **Listar todos los libros guardados**  
   Muestra un listado completo de todos los libros almacenados en la base de datos.

4. **Listar libros por idioma**  
   Filtra los libros guardados según su idioma.

5. **Top libros más descargados**  
   Muestra los libros con mayor número de descargas guardados en la base de datos.

---

## Tecnologías

- **Java 17**
- **SQLite** (base de datos local)
- **Maven** (gestión de dependencias)
- **Gutendex API** para obtener datos de libros
- **Gson** y **Jackson** para parseo de JSON

---

## Instalación

1. Clonar el repositorio:

```bash
git clone https://github.com/tuusuario/catalogo-libros.git
cd catalogo-libros
