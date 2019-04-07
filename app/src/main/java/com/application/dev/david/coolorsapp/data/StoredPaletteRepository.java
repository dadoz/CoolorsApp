package com.application.dev.david.coolorsapp.data;

import com.application.dev.david.coolorsapp.data.StoredPaletteDataSource;
import com.application.dev.david.coolorsapp.data.local.Local;
import com.application.dev.david.coolorsapp.data.local.StoredPaletteStorage;
import com.application.dev.david.coolorsapp.models.StoredColorPalette;

import java.util.List;

import io.reactivex.Observable;

@Local
public class StoredPaletteRepository {
    private final StoredPaletteStorage storedPaletteStorage;

    public StoredPaletteRepository(@Local StoredPaletteStorage storedPaletteStorage) {
        this.storedPaletteStorage = storedPaletteStorage;
    }

    public Observable<List<StoredColorPalette>> getStoredPalette() {
        return storedPaletteStorage.getStoredPalette();
    }
}
