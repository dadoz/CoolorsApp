package com.application.dev.david.coolorsapp.utils;

public class Utils {
    public static String parseColorFromRGB(String rgbcolor) {
        String[] rgbColorChunk = rgbcolor.split(",");
        return String.format("#%02x%02x%02x", Integer.parseInt(rgbColorChunk[0].split("\\(")[1].trim()),
                Integer.parseInt(rgbColorChunk[1].trim()),
                Integer.parseInt(rgbColorChunk[2].split("\\)")[0].trim()));
    }
}