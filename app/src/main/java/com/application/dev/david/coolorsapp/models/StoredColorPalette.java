package com.application.dev.david.coolorsapp.models;

import java.util.List;
import java.util.UUID;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class StoredColorPalette extends RealmObject {
    @PrimaryKey
    private String id = UUID.randomUUID().toString();
    private int type;
    private RealmList<String> colorPaletteList = new RealmList<>();

    public StoredColorPalette() {
    }

    public StoredColorPalette( int type, List<String> list) {
        this.type = type;
        this.colorPaletteList.addAll(list);
    }

    public List<String> getColorPaletteList() {
        return colorPaletteList;
    }
    public String getColorPaletteId() {
        return id;
    }

    public int getType() {
        return type;
    }
}
