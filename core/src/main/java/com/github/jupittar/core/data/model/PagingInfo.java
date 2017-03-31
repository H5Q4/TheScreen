package com.github.jupittar.core.data.model;

public class PagingInfo {

    //region Fields
    private int currentPage;
    private int totalPages;
    //endregion

    //region Getters and Setters
    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
    //endregion

    public void nextPage() {
        currentPage++;
    }

    public boolean isLastPage() {
        return currentPage == totalPages;
    }
}
