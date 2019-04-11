package com.application.dev.david.coolorsapp.data.local;

import com.application.dev.david.coolorsapp.data.StoredPaletteDataSource;
import com.application.dev.david.coolorsapp.models.StoredColorPalette;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmResults;

@Local
public class StoredPaletteStorage implements StoredPaletteDataSource {
    private final Realm realm;

    public StoredPaletteStorage() {
        realm = Realm.getDefaultInstance();
    }

    @Override
    public Observable<List<StoredColorPalette>> getStoredPalette() {
        RealmResults<StoredColorPalette> list = realm.where(StoredColorPalette.class).findAll();
        return list == null ?
                Observable.just(new ArrayList<>()) :
                Observable.just(list);
    }

    @Override
    public boolean hasStoredPalette() {
        return realm.where(StoredColorPalette.class).findAll().size() > 0;
    }
    @Override
    public void addStoredPalette(int id, int type, List<String> colorPaletteList) {
        realm.executeTransaction(realm -> {
            StoredColorPalette palette = new StoredColorPalette(id, type, colorPaletteList);
            realm.insertOrUpdate(palette);
        });
    }
}
