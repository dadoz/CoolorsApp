package com.application.dev.david.coolorsapp.models;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

public class RealmPalette extends RealmObject {
    public int id;
    public RealmList<String> colorList;

    public RealmPalette() {
    }

    public RealmPalette(RealmList<String> list) {
        this.colorList = list;
    }

    public ArrayList<String> getColorList() {
        ArrayList<String> list = new ArrayList<>();
        list.addAll(colorList);
        return list;
    }
}
