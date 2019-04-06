package com.application.dev.david.coolorsapp.models;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;

public class ColorPalette {
    private List<String> colorList;

    public ColorPalette(List<String> list) {
        this.colorList = list;
    }

    public List<String> getColorList() {
        return colorList;
    }

    public void setColorList(RealmList colorList) {
        this.colorList = colorList;
    }


}
