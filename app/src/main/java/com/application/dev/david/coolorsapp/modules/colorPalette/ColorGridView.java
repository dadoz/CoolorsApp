package com.application.dev.david.coolorsapp.modules.colorPalette;

import com.application.dev.david.coolorsapp.models.ColorPalette;

import java.util.List;

public interface ColorGridView {
    void onColorGrid(List<ColorPalette> list);
    void onColorGridError(String error);
}
