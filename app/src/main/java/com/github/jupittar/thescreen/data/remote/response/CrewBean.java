package com.github.jupittar.thescreen.data.remote.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by huangqiao on 03/05/2017.
 */

public class CrewBean {
    @SerializedName("credit_id") private String creditId;
    @SerializedName("department") private String department;
    @SerializedName("id") private int id;
    @SerializedName("job") private String job;
    @SerializedName("name") private String name;
    @SerializedName("profile_path") private Object profilePath;

    public String getCreditId() {
        return creditId;
    }

    public void setCreditId(String creditId) {
        this.creditId = creditId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getProfilePath() {
        return profilePath;
    }

    public void setProfilePath(Object profilePath) {
        this.profilePath = profilePath;
    }
}
