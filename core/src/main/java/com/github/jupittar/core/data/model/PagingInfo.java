package com.github.jupittar.core.data.model;

public class PagingInfo {

    //region Fields
    private int currentPage;
    private boolean isLastPage;
    //endregion

    //region Getters and Setters
    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public boolean isLastPage() {
        return isLastPage;
    }

    public void setLastPage(boolean lastPage) {
        isLastPage = lastPage;
    }
    //endregion
}
