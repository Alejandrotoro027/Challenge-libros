package app.api.dto;

import java.util.List;

public class ApiSearchResponse {
    public int count;
    public String next;
    public String previous;
    public List<ApiBookDTO> results;
}