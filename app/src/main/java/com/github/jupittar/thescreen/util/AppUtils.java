package com.github.jupittar.thescreen.util;


import android.content.Context;
import android.preference.PreferenceManager;

import com.github.jupittar.thescreen.R;
import com.github.jupittar.thescreen.helper.Utils;

import java.util.Locale;

public class AppUtils implements Utils {

    private Context mContext;

    public AppUtils(Context context) {
        mContext = context;
    }

    public String getRegion4Api() {
        String region = PreferenceManager
                .getDefaultSharedPreferences(mContext)
                .getString(mContext.getString(R.string.pref_key_region), "SYSTEM");
        if (region.equals("SYSTEM")) {
            return Locale.getDefault().getCountry();
        }
        return region;
    }

}
