package com.github.jupittar.core.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@SuppressWarnings("unused")
public class RawResponse<T> {

    // region Fields
    @SerializedName("page")
    private int page;
    @SerializedName("results")
    private List<T> results = null;
    @SerializedName("total_results")
    private int totalResults;
    @SerializedName("total_pages")
    private int totalPages;
    // endregion

    // region Getters

    public int getPage() {
      return page;
    }

    public List<T> getResults() {
      return results;
    }

    public int getTotalResults() {
      return totalResults;
    }

    public int getTotalPages() {
      return totalPages;
    }

    // endregion

    // region Setters

    public void setPage(int page) {
      this.page = page;
    }

    public void setResults(List<T> results) {
      this.results = results;
    }

    public void setTotalResults(int totalResults) {
      this.totalResults = totalResults;
    }

    public void setTotalPages(int totalPages) {
      this.totalPages = totalPages;
    }

    // endregion
}
