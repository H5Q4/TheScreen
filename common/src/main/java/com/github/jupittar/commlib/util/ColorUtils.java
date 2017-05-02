package com.github.jupittar.commlib.util;

import android.support.annotation.Nullable;
import android.support.v7.graphics.Palette;

public class ColorUtils {


    @Nullable
    public static Palette.Swatch getMostPopulousSwatch(Palette palette) {
        Palette.Swatch mostPopulous = null;
        if (palette != null) {
            for (Palette.Swatch swatch : palette.getSwatches()) {
                if (mostPopulous == null || swatch.getPopulation() > mostPopulous.getPopulation()) {
                    mostPopulous = swatch;
                }
            }
        }
        return mostPopulous;
    }


    @Nullable
    public static Palette.Swatch getLeastPopulousSwatch(Palette palette) {
        Palette.Swatch leastPopulous = null;
        if (palette != null) {
            for (Palette.Swatch swatch : palette.getSwatches()) {
                if (leastPopulous == null || swatch.getPopulation() < leastPopulous.getPopulation()) {
                    leastPopulous = swatch;
                }
            }
        }
        return leastPopulous;
    }

}
