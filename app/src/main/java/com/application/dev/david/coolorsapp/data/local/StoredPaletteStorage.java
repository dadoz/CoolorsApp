package com.application.dev.david.coolorsapp.data.local;

import com.application.dev.david.coolorsapp.data.ColorsDataSource;
import com.application.dev.david.coolorsapp.data.StoredPaletteDataSource;
import com.application.dev.david.coolorsapp.models.ColorPalette;
import com.application.dev.david.coolorsapp.models.RealmPalette;
import com.application.dev.david.coolorsapp.models.StoredColorPalette;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmList;

@Local
public class StoredPaletteStorage implements StoredPaletteDataSource {
    private final Realm realm;

    public StoredPaletteStorage() {
        realm = Realm.getDefaultInstance();
    }


    @Override
    public Observable<List<StoredColorPalette>> getStoredPalette() {
//        List<RealmPalette> realmPaletteList = realm.where(StoredColorPalette.class).equalTo("id", position).findAll();
//        return realmPaletteList == null ?
//                Observable.just(new ArrayList<>()) :
//                Observable.just(realmPaletteList.get(position).getColorList()); //next get from position
        return Observable.just(new ArrayList<>());
    }

    @Override
    public boolean hasStoredPalette() {
        return false; //realm.where(StoredColorPalette.class).findAll().size() > 0;
    }
    @Override
    public void addStoredPalette(int id, int type) {
        realm.executeTransaction(realm -> {
            StoredColorPalette palette = null; //realm.createObject(StoredColorPalette.class);
            palette.colorPaletteType = type;
            palette.colorPaletteId = id;
        });
    }
}
