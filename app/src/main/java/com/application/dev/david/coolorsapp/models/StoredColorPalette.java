package com.application.dev.david.coolorsapp.models;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

public class StoredColorPalette extends RealmObject {
    private RealmList<String> colorPaletteList;
    private int colorPaletteId;
    private int colorPaletteType; //palette or color

    public StoredColorPalette() {
    }

    public StoredColorPalette(int id, int type, List<String> list) {
        this.colorPaletteId = id;
        this.colorPaletteType = type;
        this.colorPaletteList = new RealmList<>();
        this.colorPaletteList.addAll(list);
    }

    public List<String> getColorPaletteList() {
        return colorPaletteList;
    }
    public int getColorPaletteId() {
        return colorPaletteId;
    }

    public int getColorPaletteType() {
        return colorPaletteType;
    }
}
