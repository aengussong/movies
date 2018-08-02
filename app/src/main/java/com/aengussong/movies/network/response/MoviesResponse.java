package com.aengussong.movies.network.response;

import com.aengussong.movies.model.Movie;

import java.util.List;

public class MoviesResponse extends PageResponse {

    private List<Movie> results;

    public MoviesResponse(String statusCode, String statusMessage, boolean success, int page, int totalResults, int totalPages, List<Movie> results) {
        super(statusCode, statusMessage, success, page, totalResults, totalPages);
        this.results = results;
    }

    public List<Movie> getResults() {
        return results;
    }
}
