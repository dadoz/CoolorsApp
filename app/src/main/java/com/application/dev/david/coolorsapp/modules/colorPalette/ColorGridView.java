package com.application.dev.david.coolorsapp.modules.colorPalette;

import com.application.dev.david.coolorsapp.models.ColorPalette;

import java.util.List;

public interface ColorGridView {
    void onColorGrid(List<ColorPalette> list, int requestedPosition);
    void onColorGridError(String error);

    void onStoredColor(String color);
    void onStoredColorError(String message);

    void onStoredPalette(List<String> palette);
    void onStoredPaletteError(String message);
}
