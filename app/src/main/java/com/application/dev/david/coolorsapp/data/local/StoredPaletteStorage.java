package com.application.dev.david.coolorsapp.data.local;

import com.application.dev.david.coolorsapp.data.StoredPaletteDataSource;
import com.application.dev.david.coolorsapp.models.StoredColorPalette;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import io.realm.Sort;

@Local
public class StoredPaletteStorage implements StoredPaletteDataSource {
    private final Realm realm;

    public StoredPaletteStorage() {
        realm = Realm.getDefaultInstance();
    }

    @Override
    public Observable<List<StoredColorPalette>> getStoredPalette() {
        RealmResults<StoredColorPalette> list = realm.where(StoredColorPalette.class).findAll().sort("createdAt", Sort.DESCENDING);
        return list == null ?
                Observable.just(new ArrayList<>()) :
                Observable.just(list);
    }

    @Override
    public boolean hasStoredPalette() {
        return realm.where(StoredColorPalette.class).findAll().size() > 0;
    }
    @Override
    public void addStoredPalette(int type, List<String> colorPaletteList) {
        realm.executeTransaction(realm -> {
            RealmResults<StoredColorPalette> list = realm.where(StoredColorPalette.class).equalTo("type", type).findAll();
            for (StoredColorPalette item : list) {
                if (item.getColorPaletteList().containsAll(colorPaletteList)) {
                    realm.insertOrUpdate(item);
                    return;
                }
            }

            //new obj
            realm.insertOrUpdate(new StoredColorPalette(type, colorPaletteList));
        });
    }
}
