package com.github.jupittar.thescreen.helper;

import android.content.Context;

import com.github.jupittar.commlib.util.NetworkUtils;
import com.github.jupittar.core.helper.AndroidHelper;


public class AppAndroidHelper implements AndroidHelper {

  private Context mContext;

  public AppAndroidHelper(Context context) {
    mContext = context;
  }

  @Override
  public boolean isNetworkConnected() {
    return NetworkUtils.isNetworkConnected(mContext);
  }
}
