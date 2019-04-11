package com.application.dev.david.coolorsapp.data;


import com.application.dev.david.coolorsapp.models.StoredColorPalette;

import java.util.List;

import io.reactivex.Observable;

public interface StoredPaletteDataSource {
    Observable<List<StoredColorPalette>> getStoredPalette();
    boolean hasStoredPalette();
    void addStoredPalette(int id, int type, List<String> palette);
}
