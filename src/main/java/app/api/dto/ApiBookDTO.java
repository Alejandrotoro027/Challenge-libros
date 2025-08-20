package app.api.dto;

import java.util.List;
import java.util.Map;

public class ApiBookDTO {
    public int id;
    public String title;
    public java.util.List<ApiAuthorDTO> authors;
    public java.util.List<String> languages;
    public Integer download_count;
    public Map<String, String> formats; // mime -> url
}