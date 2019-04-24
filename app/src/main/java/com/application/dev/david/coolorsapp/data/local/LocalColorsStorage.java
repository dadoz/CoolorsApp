package com.application.dev.david.coolorsapp.data.local;

import android.support.v4.view.PagerAdapter;
import android.util.Log;

import com.application.dev.david.coolorsapp.data.ColorsDataSource;
import com.application.dev.david.coolorsapp.models.ColorPalette;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposables;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;

@Local
public class LocalColorsStorage implements ColorsDataSource {
    private final Realm realm;

    public LocalColorsStorage() {
        realm = Realm.getDefaultInstance();
    }

    @Override
    public Observable<List<String>> getColors(int position) {
        return Observable.<List<String>>create(emitter -> {
            ColorPalette colorPalette = realm.where(ColorPalette.class)
                    .like("id", "*_" + position)
                    .findFirstAsync();

            final RealmChangeListener<ColorPalette> realmChangeListener = element -> {
                if(element.isLoaded() && !emitter.isDisposed()) {
                    if(!emitter.isDisposed()) {
                        emitter.onNext(element.getColorList());
                    }
                }
            };

            emitter.setDisposable(Disposables.fromAction(() -> {
                if(colorPalette.isValid()) {
                    colorPalette.removeChangeListener(realmChangeListener);
                }
                realm.close();
            }));

            colorPalette.addChangeListener(realmChangeListener);
        })
                .delay(200, TimeUnit.MILLISECONDS)

                .subscribeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public boolean hasColors(int position) {
        return realm.where(ColorPalette.class).findAll().size() > position &&
                findByPosition(position) != null;
    }

    /**
     *
     * @param position
     * @return
     */
    private ColorPalette findByPosition(int position) {
        return realm.where(ColorPalette.class).like("id", "*_" + position).findFirst();
    }

    @Override
    public void addColors(List<String> list, int position) {
        realm.executeTransaction(realm -> realm.insertOrUpdate(new ColorPalette(position, list)));
    }

    @Override
    public Observable<Boolean> removeColor(ColorPalette colorPalette) {
        realm.executeTransaction(realm -> {
            ColorPalette realmObj = findByPosition(Integer.parseInt(colorPalette.getId().split("_")[1]));
            if (realmObj != null)
                realmObj.deleteFromRealm();
        });
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
