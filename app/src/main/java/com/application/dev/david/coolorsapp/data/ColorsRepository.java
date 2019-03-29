package com.application.dev.david.coolorsapp.data;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class ColorsRepository implements ColorsDataSource {

    public Observable<List<String>> getColors() {
        ArrayList<String> list = new ArrayList<>();
        list.add("rgb(222, 39, 39)");
        list.add("rgb(80, 80, 80)");
        list.add("rgb(122, 114, 101)");
        list.add("rgb(192, 183, 177)");
        list.add("rgb(142, 110, 83)");
        return Observable.just(list);
    }
}
