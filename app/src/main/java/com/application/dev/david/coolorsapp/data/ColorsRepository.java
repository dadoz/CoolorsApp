package com.application.dev.david.coolorsapp.data;
import com.application.dev.david.coolorsapp.data.local.Local;
import com.application.dev.david.coolorsapp.data.remote.Remote;

import java.util.List;

import io.reactivex.Observable;

public class ColorsRepository {

    private final ColorsDataSource remoteDataSource;
    private final ColorsDataSource localDataSource;

    public ColorsRepository(@Remote ColorsDataSource remoteDataSource, @Local  ColorsDataSource localDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    public Observable<List<String>> getColors() {
//        if (localDataSource.hasColors()) {
        if (true) {
            return localDataSource.getColors();
        }
        return remoteDataSource.getColors();
    }
}
