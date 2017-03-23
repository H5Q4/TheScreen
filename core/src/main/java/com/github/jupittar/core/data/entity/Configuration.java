package com.github.jupittar.core.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@SuppressWarnings("unused")
public class Configuration {

  @SerializedName("images")
  private Images images;
  @SerializedName("change_keys")
  private List<String> changeKeys;

  //region Getters and Setters
  public Images getImages() {
    return images;
  }

  public void setImages(Images images) {
    this.images = images;
  }

  public List<String> getChangeKeys() {
    return changeKeys;
  }

  public void setChangeKeys(List<String> changeKeys) {
    this.changeKeys = changeKeys;
  }
  //endregion
}
