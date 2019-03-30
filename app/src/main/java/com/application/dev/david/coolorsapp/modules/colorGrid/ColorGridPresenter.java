package com.application.dev.david.coolorsapp.modules.colorGrid;

import android.view.View;

import com.application.dev.david.coolorsapp.data.ColorsRepository;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
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

    public void retrieveData() {
        Disposable disposable =
                Observable.just("")
                        .subscribeOn(Schedulers.newThread())
                        .flatMap(_1 -> respository.getColors())
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .flatMap(list -> Observable.fromIterable(list).map(item -> "#" + item).toList().toObservable())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(view::onColorGrid,
                            error -> view.onColorGridError(error.getMessage()));
    }
}
