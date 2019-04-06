package com.application.dev.david.coolorsapp.data.local;

import com.application.dev.david.coolorsapp.data.ColorsDataSource;
import com.application.dev.david.coolorsapp.models.ColorPalette;
import com.application.dev.david.coolorsapp.models.RealmPalette;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;

@Local
public class LocalColorsStorage implements ColorsDataSource {
    private final Realm realm;

    public LocalColorsStorage() {
        realm = Realm.getDefaultInstance();
    }

    @Override
    public Observable<List<String>> getColors(int position) {
        List<RealmPalette> realmPaletteList = realm.where(RealmPalette.class).equalTo("id", position).findAll();
        return realmPaletteList == null ?
                Observable.just(new ArrayList<>()) :
                Observable.just(realmPaletteList.get(position).getColorList()); //next get from position
    }

    @Override
    public boolean hasColors(int position) {
       return realm.where(RealmPalette.class).findAll().size() > position;
    }

    @Override
    public void addColors(List<String> list, int position) {
        realm.executeTransaction(realm -> {
            RealmPalette palette = realm.createObject(RealmPalette.class);
            palette.id = position;
            RealmList<String> realmList = new RealmList<>();
            realmList.addAll(list);
            palette.colorList = realmList;
        });
    }


    private void mock3(ArrayList<String> list) {
        list.add("333333");
        list.add("550033");
        list.add("FFD9aa");
        list.add("ECECcc");
        list.add("FFFFFF");
    }

    private void mock2(ArrayList<String> list) {
        list.add("3F51B5");
        list.add("5500aa");
        list.add("FFD900");
        list.add("ECECEC");
        list.add("FFFFFF");
    }

    private ArrayList<String> mock1(ArrayList<String> list) {
        list.add("rgb(222, 39, 39)");
        list.add("rgb(80, 80, 80)");
        list.add("rgb(122, 114, 101)");
        list.add("rgb(192, 183, 177)");
        list.add("rgb(142, 110, 83)");
        return list;
    }

}
