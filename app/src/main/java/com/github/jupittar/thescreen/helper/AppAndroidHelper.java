package com.github.jupittar.thescreen.helper;

import android.content.Context;

import com.github.jupittar.commlib.util.NetworkUtils;
import com.github.jupittar.core.data.entity.Configuration;
import com.github.jupittar.core.helper.AndroidHelper;
import com.github.jupittar.thescreen.util.SharedPreferencesManager;


@SuppressWarnings("unused")
public class AppAndroidHelper implements AndroidHelper {

  private Context mContext;

  public AppAndroidHelper(Context context) {
    mContext = context;
  }

  @Override
  public void saveApiConfiguration(Configuration configuration) {
    SharedPreferencesManager.setConfiguration(mContext, configuration);
  }

  @Override
  public boolean isApiConfigurationExisted() {
    Configuration configuration = SharedPreferencesManager.getConfiguration(mContext);
    return configuration != null;
  }

  @Override
  public boolean isNetworkConnected() {
    return NetworkUtils.isNetworkConnected(mContext);
  }
}
