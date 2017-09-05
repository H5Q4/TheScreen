package com.github.jupittar.thescreen.data.entity;


import com.github.jupittar.thescreen.data.remote.response.Review;

import java.util.List;

public class ReviewWrapper {

    // region Fields
    private List<Review> mReviews;
    private PagingInfo pagingInfo;
    private long persistedTime;
    // endregion

    // region Constructors

    public ReviewWrapper(List<Review> reviews, PagingInfo pagingInfo, long persistedTime) {
        this.mReviews = reviews;
        this.pagingInfo = pagingInfo;
        this.persistedTime = persistedTime;
    }

    // endregion


    public List<Review> getReviews() {
        return mReviews;
    }

    public void setReviews(List<Review> reviews) {
        mReviews = reviews;
    }

    public PagingInfo getPagingInfo() {
        return pagingInfo;
    }

    public void setPagingInfo(PagingInfo pagingInfo) {
        this.pagingInfo = pagingInfo;
    }

    public long getPersistedTime() {
        return persistedTime;
    }

    public void setPersistedTime(long persistedTime) {
        this.persistedTime = persistedTime;
    }

    // Helper Methods
    public boolean hasReviews() {
        return mReviews.size() > 0;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() >
                persistedTime + 24 * 60 * 60 * 1000;
    }
    // endregion
}
