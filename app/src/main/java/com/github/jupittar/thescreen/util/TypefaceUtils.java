package com.github.jupittar.thescreen.util;

import android.content.Context;
import android.graphics.Typeface;
import android.util.ArrayMap;

import java.util.Map;

public class TypefaceUtils {

    public static final String FONT_AVENIR_NEXT_LT_PRO_REGULAR = "AvenirNextLTPro-Regular.otf";
    public static final String FONT_AVENIR_NEXT_LT_PRO_LT = "AvenirNextLTPro-It.otf";
    public static final String FONT_AVENIR_NEXT_LT_PRO_BOLD = "AvenirNextLTPro-Bold.otf";

    private static Map<String, Typeface> sTypefaceMap = new ArrayMap<>();

    public static Typeface getTypeface(String fontName, Context context) {
        Typeface typeface = sTypefaceMap.get(fontName);

        if (typeface == null) {
            try {
                typeface = Typeface.createFromAsset(context.getAssets(), String.format("fonts/%s", fontName));
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

            sTypefaceMap.put(fontName, typeface);
        }

        return typeface;
    }

}
