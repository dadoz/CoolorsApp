package com.application.dev.david.coolorsapp.data.local;

import com.application.dev.david.coolorsapp.data.ColorsDataSource;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

@Local
public class LocalColorsStorage implements ColorsDataSource {
    private ArrayList<String> list = new ArrayList<>();

    public Observable<List<String>> getColors() {
        list.clear();
        mock3(list);
        return Observable.just(list);
    }

    @Override
    public boolean hasColors() {
        return list.size() > 0;
    }


    private void mock3(ArrayList<String> list) {
        list.add("3F51B1");
        list.add("550033");
        list.add("FFD9aa");
        list.add("ECECcc");
        list.add("FFFF00");
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
