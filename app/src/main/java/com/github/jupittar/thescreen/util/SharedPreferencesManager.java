package com.github.jupittar.thescreen.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {

  private static final String KEY_TMDB_PREF = "tmdb_pref";
  private static final String KEY_TMDB_API_CONF = "tmdb_api_conf";

  private SharedPreferencesManager() {
  }

  // region Helper Methods
  private static SharedPreferences.Editor getEditor(Context context) {
    SharedPreferences preferences = getSharedPreferences(context);
    return preferences.edit();
  }

  private static SharedPreferences getSharedPreferences(Context context) {
    return context.getSharedPreferences(KEY_TMDB_PREF, Context.MODE_PRIVATE);
  }
  // endregion
}
