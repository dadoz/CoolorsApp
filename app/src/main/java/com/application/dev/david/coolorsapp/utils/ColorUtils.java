package com.application.dev.david.coolorsapp.utils;

import android.graphics.Color;

public class ColorUtils {


    public static int lighten(int color, double fraction) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        red = lightenColor(red, fraction);
        green = lightenColor(green, fraction);
        blue = lightenColor(blue, fraction);
        int alpha = Color.alpha(color);
        return Color.argb(alpha, red, green, blue);
    }

    public static int darken(int color, double fraction) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        red = darkenColor(red, fraction);
        green = darkenColor(green, fraction);
        blue = darkenColor(blue, fraction);
        int alpha = Color.alpha(color);

        return Color.argb(alpha, red, green, blue);
    }

    private static int getLuminance(int argb) {
        int lum= (   77  * ((argb>>16)&255)
                + 150 * ((argb>>8)&255)
                + 29  * ((argb)&255))>>8;
        return lum;
    }

    public static boolean isDark(int color) {
        return 255 - getLuminance(color) > 100;
    }

    public static boolean isLight(int color) {
        return getLuminance(color) < 10;
    }

    private static int darkenColor(int color, double fraction) {
        return (int)Math.max(color - (color * fraction), 0);
    }

    private static int lightenColor(int color, double fraction) {
        return (int) Math.min(color + (color * fraction), 255);
    }

    public static int gerOppositeColor(int selectedColor) {
        return isDark(selectedColor) ?
                lighten(selectedColor, 2.5f) :
                darken(selectedColor, 0.4f);
    }
}
