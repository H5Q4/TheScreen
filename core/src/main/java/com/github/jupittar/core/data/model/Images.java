package com.github.jupittar.core.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@SuppressWarnings("unused")
public class Images {

  @SerializedName("base_url")
  private String baseUrl;
  @SerializedName("secure_base_url")
  private String secureBaseUrl;
  @SerializedName("backdrop_sizes")
  private List<String> backdropSizes;
  @SerializedName("logo_sizes")
  private List<String> logoSizes;
  @SerializedName("poster_sizes")
  private List<String> posterSizes;
  @SerializedName("profile_sizes")
  private List<String> profileSizes;
  @SerializedName("still_sizes")
  private List<String> stillSizes;

  //region Getters and Setters
  public String getBaseUrl() {
    return baseUrl;
  }

  public void setBaseUrl(String baseUrl) {
    this.baseUrl = baseUrl;
  }

  public String getSecureBaseUrl() {
    return secureBaseUrl;
  }

  public void setSecureBaseUrl(String secureBaseUrl) {
    this.secureBaseUrl = secureBaseUrl;
  }

  public List<String> getBackdropSizes() {
    return backdropSizes;
  }

  public void setBackdropSizes(List<String> backdropSizes) {
    this.backdropSizes = backdropSizes;
  }

  public List<String> getLogoSizes() {
    return logoSizes;
  }

  public void setLogoSizes(List<String> logoSizes) {
    this.logoSizes = logoSizes;
  }

  public List<String> getPosterSizes() {
    return posterSizes;
  }

  public void setPosterSizes(List<String> posterSizes) {
    this.posterSizes = posterSizes;
  }

  public List<String> getProfileSizes() {
    return profileSizes;
  }

  public void setProfileSizes(List<String> profileSizes) {
    this.profileSizes = profileSizes;
  }

  public List<String> getStillSizes() {
    return stillSizes;
  }

  public void setStillSizes(List<String> stillSizes) {
    this.stillSizes = stillSizes;
  }
  //endregion
}
