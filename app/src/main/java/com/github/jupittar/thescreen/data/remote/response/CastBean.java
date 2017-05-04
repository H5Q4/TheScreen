package com.github.jupittar.thescreen.data.remote.response;

import com.google.gson.annotations.SerializedName;


public class CastBean {
    @SerializedName("cast_id") private int castId;
    @SerializedName("character") private String character;
    @SerializedName("credit_id") private String creditId;
    @SerializedName("id") private int id;
    @SerializedName("name") private String name;
    @SerializedName("order") private int order;
    @SerializedName("profile_path") private Object profilePath;

    public int getCastId() {
        return castId;
    }

    public void setCastId(int castId) {
        this.castId = castId;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getCreditId() {
        return creditId;
    }

    public void setCreditId(String creditId) {
        this.creditId = creditId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public Object getProfilePath() {
        return profilePath;
    }

    public void setProfilePath(Object profilePath) {
        this.profilePath = profilePath;
    }
}
