package com.github.jupittar.thescreen.data.model;

public class PagingInfo {

    //region Fields
    public static final int NO_MORE_PAGES = -1;

    private int currentPage;
    private int totalPages;
    //endregion


    //region Constructors
    public PagingInfo(int currentPage, int totalPages) {
        this.currentPage = currentPage;
        this.totalPages = totalPages;
    }
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

    //region Helper Methods
    public void nextPage() {
        currentPage++;
        if (currentPage > totalPages) currentPage = NO_MORE_PAGES;
    }

    public boolean isLastPage() {
        return currentPage == totalPages;
    }
    //endregion
}
