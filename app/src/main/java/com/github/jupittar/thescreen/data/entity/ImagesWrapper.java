package com.github.jupittar.thescreen.data.entity;


import com.github.jupittar.thescreen.data.remote.response.Images;

public class ImagesWrapper {

    //region Fields
    private Images images;
    private long persistedTime;
    //endregion


    //region Constructors
    public ImagesWrapper(Images images, long persistedTime) {
        this.images = images;
        this.persistedTime = persistedTime;
    }
    //endregion

    //region Getters and Setters
    public Images getImages() {
        return images;
    }

    public void setImages(Images images) {
        this.images = images;
    }

    public long getPersistedTime() {
        return persistedTime;
    }

    public void setPersistedTime(long persistedTime) {
        this.persistedTime = persistedTime;
    }
    //endregion

    //region Helper Methods
    public boolean isExpired() {
        return System.currentTimeMillis() >
                persistedTime + 7 * 24 * 60 * 60 * 1000;
    }
    //endregion
}
