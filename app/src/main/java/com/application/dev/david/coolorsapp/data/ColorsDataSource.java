package com.application.dev.david.coolorsapp.data;


import com.application.dev.david.coolorsapp.models.ColorPalette;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public interface ColorsDataSource {
    Observable<List<String>> getColors(int position);

    boolean hasColors(int position);

    void addColors(List<String> list, int index);

    Observable<Boolean> removeColor(ColorPalette colorPalette);
}
