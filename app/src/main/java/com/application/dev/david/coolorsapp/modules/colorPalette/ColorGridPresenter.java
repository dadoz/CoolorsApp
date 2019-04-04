package com.application.dev.david.coolorsapp.modules.colorPalette;

import com.application.dev.david.coolorsapp.data.ColorsRepository;
import com.application.dev.david.coolorsapp.models.ColorPalette;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class ColorGridPresenter {
    private final ColorsRepository respository;
    private final ColorGridView view;
    private final ArrayList<ColorPalette> list;

    public ColorGridPresenter(ColorGridView view, ColorsRepository repository) {
        this.respository = repository;
        this.view = view;
        this.list = new ArrayList<>();
    }

    public void retrieveData() {
        Disposable disposable =
                Observable.just(list)
                        .subscribeOn(Schedulers.newThread())
                        .flatMap(colorPaletteArrayList -> respository.getColors()
                                .subscribeOn(Schedulers.newThread())
                                .observeOn(AndroidSchedulers.mainThread())
                                .flatMap(list -> Observable.fromIterable(list).map(item -> "#" + item).toList().toObservable())
                                .map(ColorPalette::new)
                                .doOnError(Throwable::printStackTrace)
                                .doOnNext(colorPaletteArrayList::add)
                                .map(res -> colorPaletteArrayList))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(view::onColorGrid,
                                error -> view.onColorGridError(error.getMessage()));
    }
}
