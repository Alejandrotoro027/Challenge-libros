package app.model;

public class Author {
    private Integer id;        // PK local (SQLite autoincrement)
    private String name;
    private Integer birthYear;
    private Integer deathYear;

    public Author() {}

    public Author(Integer id, String name, Integer birthYear, Integer deathYear) {
        this.id = id;
        this.name = name;
        this.birthYear = birthYear;
        this.deathYear = deathYear;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Integer getBirthYear() { return birthYear; }
    public void setBirthYear(Integer birthYear) { this.birthYear = birthYear; }
    public Integer getDeathYear() { return deathYear; }
    public void setDeathYear(Integer deathYear) { this.deathYear = deathYear; }

    @Override public String toString() {
        return String.format("%s (%s–%s)", name,
                birthYear == null ? "?" : birthYear,
                deathYear == null ? "?" : deathYear);
    }
}