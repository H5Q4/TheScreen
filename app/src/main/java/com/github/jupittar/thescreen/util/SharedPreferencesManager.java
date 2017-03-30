package com.github.jupittar.thescreen.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.github.jupittar.core.data.model.Configuration;
import com.google.gson.Gson;

public class SharedPreferencesManager {

  private static final String KEY_TMDB_PREF = "tmdb_pref";
  private static final String KEY_TMDB_API_CONF = "tmdb_api_conf";

  private SharedPreferencesManager() {
  }

  // region Getters
  public static Configuration getConfiguration(Context context){
    SharedPreferences preferences = getSharedPreferences(context);
    Gson gson = new Gson();
    String json = preferences.getString(KEY_TMDB_API_CONF, "");
    Configuration configuration = gson.fromJson(json, Configuration.class);
    return configuration;
  }
  // endregion

  // region Setters
  public static void setConfiguration(Context context, Configuration configuration) {
    SharedPreferences.Editor editor = getEditor(context);
    Gson gson = new Gson();
    String json = gson.toJson(configuration);
    editor.putString(KEY_TMDB_API_CONF, json)
        .apply();
  }
  // endregion

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
