package com.github.jupittar.core.helper;


import com.github.jupittar.core.data.model.Configuration;

public interface AndroidHelper {

  void saveApiConfiguration(Configuration configuration);

  boolean isApiConfigurationExisted();

  boolean isNetworkConnected();

}
