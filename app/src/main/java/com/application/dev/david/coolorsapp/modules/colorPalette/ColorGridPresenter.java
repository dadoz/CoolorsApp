package com.application.dev.david.coolorsapp.modules.colorPalette;

import android.support.v4.util.Pair;

import com.application.dev.david.coolorsapp.data.ColorsRepository;
import com.application.dev.david.coolorsapp.models.ColorPalette;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ColorGridPresenter {
    private final ColorsRepository respository;
    private final ColorGridView view;

    public ColorGridPresenter(ColorGridView view, ColorsRepository repository) {
        this.respository = repository;
        this.view = view;
    }

    public void retrieveData(int i) {
        Disposable disposable =
                Observable.just(i)
                        .subscribeOn(Schedulers.newThread())
                        .flatMap(position -> respository.getColors()
                                .subscribeOn(Schedulers.newThread())
                                .observeOn(AndroidSchedulers.mainThread())
                                .flatMap(list -> Observable.fromIterable(list).map(item -> "#" + item).toList().toObservable())
                                .map(ColorPalette::new)
                                .doOnError(Throwable::printStackTrace)
                                .map(list -> {
                                    List<ColorPalette> paletteList = new ArrayList<>();
                                    paletteList.add(list);
                                    return paletteList; })
                                .map(list -> new Pair<>(list, position))
                        )
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(pair -> view.onColorGrid(pair.first, pair.second),
                                error -> view.onColorGridError(error.getMessage()));
    }
}
