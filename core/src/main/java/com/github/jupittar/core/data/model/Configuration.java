package com.github.jupittar.core.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@SuppressWarnings("unused")
public class Configuration {

  //region Fields
  @SerializedName("images")
  private Images images;
  @SerializedName("change_keys")
  private List<String> changeKeys;
  //endregion

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
