package com.application.dev.david.coolorsapp.models;

import java.util.Date;
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
    private Date createdAt = new Date();
    private String label;

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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setPaletteLabel(String label) {
        this.label = label;
    }
}
