package com.github.jupittar.commlib.util;

import android.support.annotation.CheckResult;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.support.annotation.IntRange;
import android.support.annotation.Nullable;
import android.support.v7.graphics.Palette;

public class ColorUtils {

    /**
     * Set the alpha component of {@code color} to be {@code alpha}.
     */
    public static
    @CheckResult
    @ColorInt
    int modifyAlpha(@ColorInt int color,
                    @IntRange(from = 0, to = 255) int alpha) {
        return (color & 0x00ffffff) | (alpha << 24);
    }

    /**
     * Set the alpha component of {@code color} to be {@code alpha}.
     */
    public static
    @CheckResult
    @ColorInt
    int modifyAlpha(@ColorInt int color,
                    @FloatRange(from = 0f, to = 1f) float alpha) {
        return modifyAlpha(color, (int) (255f * alpha));
    }


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
