package com.application.dev.david.coolorsapp.data;

import com.application.dev.david.coolorsapp.data.StoredPaletteDataSource;
import com.application.dev.david.coolorsapp.data.local.Local;
import com.application.dev.david.coolorsapp.data.local.StoredPaletteStorage;
import com.application.dev.david.coolorsapp.models.StoredColorPalette;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.reactivex.Observable;

@Local
public class StoredPaletteRepository {
    private final StoredPaletteStorage storedPaletteStorage;
    enum StoredPaletteType { COLOR_TYPE, PALETTE_TYPE }
    public StoredPaletteRepository(@Local StoredPaletteStorage storedPaletteStorage) {
        this.storedPaletteStorage = storedPaletteStorage;
    }

    public Observable<List<StoredColorPalette>> getStoredPalette() {
        return storedPaletteStorage.getStoredPalette();
    }

    public Observable<Boolean> addStoredColor(String color) {
        ArrayList<String> list = new ArrayList<>();
        list.add(color);
        storedPaletteStorage.addStoredPalette(Long.numberOfTrailingZeros(UUID.randomUUID().getLeastSignificantBits()), StoredPaletteType.COLOR_TYPE.ordinal(), list);
        return Observable.just(true);
    }

    public Observable<Boolean> addStoredPalette(List<String> palette) {
        storedPaletteStorage.addStoredPalette(Long.numberOfTrailingZeros(UUID.randomUUID().getLeastSignificantBits()), StoredPaletteType.PALETTE_TYPE.ordinal(), palette);
        return Observable.just(true);
    }
}
