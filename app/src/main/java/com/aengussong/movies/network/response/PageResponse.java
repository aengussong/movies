package com.aengussong.movies.network.response;

import com.google.gson.annotations.SerializedName;

public class PageResponse extends BaseResponse {
    private int page;

    @SerializedName("total_results")
    private int totalResults;
    @SerializedName("total_pages")
    private int totalPages;

    public PageResponse(String statusCode, String statusMessage, boolean success, int page, int totalResults, int totalPages) {
        super(statusCode, statusMessage, success);
        this.page = page;
        this.totalResults = totalResults;
        this.totalPages = totalPages;
    }

    public int getPage() {
        return page;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }
}
