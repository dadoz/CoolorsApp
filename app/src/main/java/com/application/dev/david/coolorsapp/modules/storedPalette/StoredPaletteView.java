package com.application.dev.david.coolorsapp.modules.storedPalette;

import com.application.dev.david.coolorsapp.models.ColorPalette;
import com.application.dev.david.coolorsapp.models.StoredColorPalette;

import java.util.List;

public interface StoredPaletteView {
    void onStoredPaletteRetrieved(List<StoredColorPalette> list);
    void onStoredPaletteEditedName(boolean result);
    void onStoredPaletteDeleted(boolean result);
    void onStoredPaletteEditedNameError(String error);
    void onStoredPaletteDeletedError(String error);
    void onStoredPaletteError(String error);

}
