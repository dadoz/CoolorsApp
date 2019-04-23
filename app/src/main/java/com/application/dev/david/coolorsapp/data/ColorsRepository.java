package com.application.dev.david.coolorsapp.data;
import com.application.dev.david.coolorsapp.data.local.Local;
import com.application.dev.david.coolorsapp.data.remote.Remote;
import com.application.dev.david.coolorsapp.models.ColorPalette;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ColorsRepository {

    private final ColorsDataSource remoteDataSource;
    private final ColorsDataSource localDataSource;

    public ColorsRepository(@Remote ColorsDataSource remoteDataSource, @Local  ColorsDataSource localDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    public Observable<List<String>> getColors(int position) {
        if (localDataSource.hasColors(position)) {
            return localDataSource.getColors(position);
        }

        return Observable.just(position)
                .subscribeOn(Schedulers.newThread())
                .flatMap(index -> remoteDataSource.getColors(position)
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnNext(list -> localDataSource.addColors(list, index)))
                .subscribeOn(Schedulers.newThread());
    }

    public Observable<Boolean> removeColorPalette(ColorPalette colorPalette) {
        return localDataSource.removeColor(colorPalette);
    }
}
