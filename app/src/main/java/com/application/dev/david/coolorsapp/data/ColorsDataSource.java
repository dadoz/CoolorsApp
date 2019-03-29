package com.application.dev.david.coolorsapp.data;


import java.util.List;

import io.reactivex.Observable;

public interface ColorsDataSource {
    Observable<List<String>> getColors();
}