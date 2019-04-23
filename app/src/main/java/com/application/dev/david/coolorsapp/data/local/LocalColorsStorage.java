package com.application.dev.david.coolorsapp.data.local;

import com.application.dev.david.coolorsapp.data.ColorsDataSource;
import com.application.dev.david.coolorsapp.models.ColorPalette;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.realm.Realm;

@Local
public class LocalColorsStorage implements ColorsDataSource {
    private final Realm realm;

    public LocalColorsStorage() {
        realm = Realm.getDefaultInstance();
    }

    @Override
    public Observable<List<String>> getColors(int position) {
        ColorPalette realmPalette = realm.where(ColorPalette.class).like("id", "*_" + position).findFirst();
        return realmPalette == null ?
                Observable.just(new ArrayList<>()) :
                Observable.just(realmPalette.getColorList()); //next get from position
    }

    @Override
    public boolean hasColors(int position) {
       return realm.where(ColorPalette.class).findAll().size() > position;
    }

    @Override
    public void addColors(List<String> list, int position) {
        realm.executeTransaction(realm -> realm.insertOrUpdate(new ColorPalette(position, list)));
    }

    @Override
    public Observable<Boolean> removeColor(ColorPalette colorPalette) {
        realm.executeTransaction(realm -> realm.where(ColorPalette.class).equalTo("id", colorPalette.getId()));
        return Observable.just(true);
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
