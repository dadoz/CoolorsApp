package com.application.dev.david.coolorsapp.modules.colorGrid;

import android.view.View;

import com.application.dev.david.coolorsapp.data.ColorsRepository;

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
        Disposable disposable = respository.getColors()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .subscribe(view::onColorGrid,
                        error -> view.onColorGridError(error.getMessage()));
    }
}
