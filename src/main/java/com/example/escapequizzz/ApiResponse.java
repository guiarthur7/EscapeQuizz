package com.example.escapequizzz;

import java.util.List;

public class ApiResponse {
    public int response_code;
    public List<ApiQuestion> results;

    public ApiResponse(int response_code, List<ApiQuestion> results) {
        this.response_code = response_code;
        this.results = results;
    }
}