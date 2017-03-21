package com.github.jupittar.commlib.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;

public class AppUtils {

  private static Boolean isDebug = null;

  public static boolean isDebug() {
    return isDebug != null && isDebug;
  }

  /**
   * 在 Application 中调用进行初始化，之后即可调用 isDebug() 获取是否是 debug 版本
   */
  public static void syncIsDebug(Context context) {
    if (isDebug == null) {
      isDebug = context.getApplicationInfo() != null
          && (context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
    }
  }

}
