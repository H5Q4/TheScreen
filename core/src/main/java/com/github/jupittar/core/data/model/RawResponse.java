package com.github.jupittar.core.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@SuppressWarnings("unused")
public class RawResponse<T> {

    // region Fields
    @SerializedName("page")
    private int page;
    @SerializedName("results")
    private List<T> results;
    @SerializedName("total_results")
    private int totalResults;
    @SerializedName("total_pages")
    private int totalPages;
    // endregion

    // region Getters

    public int getPage() {
      return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<T> getResults() {
      return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }

    // endregion

    // region Setters

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
      this.totalResults = totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
      this.totalPages = totalPages;
    }

    // endregion
}
