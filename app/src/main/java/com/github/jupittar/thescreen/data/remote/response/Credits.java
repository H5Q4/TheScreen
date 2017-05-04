package com.github.jupittar.thescreen.data.remote.response;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Credits {
    @SerializedName("id") private int id;
    @SerializedName("cast") private List<CastBean> cast;
    @SerializedName("crew") private List<CrewBean> crew;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<CastBean> getCast() {
        return cast;
    }

    public void setCast(List<CastBean> cast) {
        this.cast = cast;
    }

    public List<CrewBean> getCrew() {
        return crew;
    }

    public void setCrew(List<CrewBean> crew) {
        this.crew = crew;
    }
}
