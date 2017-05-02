package com.github.jupittar.thescreen.data.remote.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Images {

    //region Fields
    @SerializedName("id") private int id;
    @SerializedName("backdrops") private List<BackdropsBean> backdrops;
    @SerializedName("posters") private List<PostersBean> posters;
    //endregion

    //region Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<BackdropsBean> getBackdrops() {
        return backdrops;
    }

    public void setBackdrops(List<BackdropsBean> backdrops) {
        this.backdrops = backdrops;
    }

    public List<PostersBean> getPosters() {
        return posters;
    }

    public void setPosters(List<PostersBean> posters) {
        this.posters = posters;
    }
    //endregion

    public static class BackdropsBean {
        //region Fields
        @SerializedName("aspect_ratio") private double aspectRatio;
        @SerializedName("file_path") private String filePath;
        @SerializedName("height") private int height;
        @SerializedName("iso_639_1") private Object iso6391;
        @SerializedName("vote_average") private float voteAverage;
        @SerializedName("vote_count") private int voteCount;
        @SerializedName("width") private int width;
        //endregion

        //region Getters and Setters
        public double getAspectRatio() {
            return aspectRatio;
        }

        public void setAspectRatio(double aspectRatio) {
            this.aspectRatio = aspectRatio;
        }

        public String getFilePath() {
            return filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public Object getIso6391() {
            return iso6391;
        }

        public void setIso6391(Object iso6391) {
            this.iso6391 = iso6391;
        }

        public float getVoteAverage() {
            return voteAverage;
        }

        public void setVoteAverage(float voteAverage) {
            this.voteAverage = voteAverage;
        }

        public int getVoteCount() {
            return voteCount;
        }

        public void setVoteCount(int voteCount) {
            this.voteCount = voteCount;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }
        //endregion
    }

    public static class PostersBean {
        //region Fields
        @SerializedName("aspect_ratio") private double aspectRatio;
        @SerializedName("file_path") private String filePath;
        @SerializedName("height") private int height;
        @SerializedName("iso_639_1") private String iso6391;
        @SerializedName("vote_average") private float voteAverage;
        @SerializedName("vote_count") private int voteCount;
        @SerializedName("width") private int width;
        //endregion

        //region Getters and Setters
        public double getAspectRatio() {
            return aspectRatio;
        }

        public void setAspectRatio(double aspectRatio) {
            this.aspectRatio = aspectRatio;
        }

        public String getFilePath() {
            return filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public String getIso6391() {
            return iso6391;
        }

        public void setIso6391(String iso6391) {
            this.iso6391 = iso6391;
        }

        public float getVoteAverage() {
            return voteAverage;
        }

        public void setVoteAverage(float voteAverage) {
            this.voteAverage = voteAverage;
        }

        public int getVoteCount() {
            return voteCount;
        }

        public void setVoteCount(int voteCount) {
            this.voteCount = voteCount;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }
        //endregion
    }
}
