package com.application.dev.david.coolorsapp.modules.storedPalette;

import com.application.dev.david.coolorsapp.models.ColorPalette;

import java.util.List;

public interface StoredPaletteView {
    void onStoredPaletteRetrieved(List<ColorPalette> list, int requestedPosition);
    void onStoredPaletteError(String error);

}
