package com.github.jupittar.thescreen.data.model;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class SpokenLanguages {

    //region Fields
    @SerializedName("iso_639_1")
    private String iso6391;
    @SerializedName("name")
    private String name;
    //endregion

    //region Getters and Setters
    public String getIso6391() {
        return iso6391;
    }

    public void setIso6391(String iso6391) {
        this.iso6391 = iso6391;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    //endregion
}
