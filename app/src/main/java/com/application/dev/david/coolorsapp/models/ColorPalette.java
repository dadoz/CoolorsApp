package com.application.dev.david.coolorsapp.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.realm.RealmList;
import io.realm.RealmObject;

public class ColorPalette extends RealmObject {
    public String id;
    private RealmList<String> colorList;

    public ColorPalette() {
    }
    public ColorPalette(int positionId, List<String> list) {
        this.id = UUID.randomUUID().toString() + "_" + positionId;
        this.colorList = new RealmList<>();
        colorList.addAll(list);
    }

    public List<String> getColorList() {
        ArrayList<String> list = new ArrayList<>();
        list.addAll(colorList);
        return list;
    }

    public String getId() {
        return id;
    }
}
