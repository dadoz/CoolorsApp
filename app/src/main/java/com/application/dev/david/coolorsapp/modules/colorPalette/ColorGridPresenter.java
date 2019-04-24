package com.application.dev.david.coolorsapp.modules.colorPalette;

import android.support.v4.util.Pair;
import android.util.Log;

import com.application.dev.david.coolorsapp.data.ColorsRepository;
import com.application.dev.david.coolorsapp.data.StoredPaletteRepository;
import com.application.dev.david.coolorsapp.models.ColorPalette;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ColorGridPresenter {
    private final ColorsRepository respository;
    private final ColorGridView view;
    private final StoredPaletteRepository storedPaletteRepository;

    public ColorGridPresenter(ColorGridView view, ColorsRepository repository, StoredPaletteRepository storedPaletteRepository) {
        this.respository = repository;
        this.storedPaletteRepository = storedPaletteRepository;
        this.view = view;
    }

    public void retrieveData(int i) {
        Disposable disposable =
                Observable.just(i)
                        .flatMap(position -> respository.getColors(position)
                                .subscribeOn(Schedulers.newThread())
                                .observeOn(AndroidSchedulers.mainThread())
                                .flatMap(list -> Observable.fromIterable(list).map(item -> "#" + item).toList().toObservable())
                                .map(list -> new ColorPalette(position, list))
                                .map(res -> {
                                    ArrayList<ColorPalette> list = new ArrayList<>();
                                    list.add(res);
                                    return list;
                                })
                                .doOnError(Throwable::printStackTrace)
                                .map(list -> new Pair<>(list, position))
                        )
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(pair -> view.onColorGrid(pair.first, pair.second),
                                error -> view.onColorGridError(error.getMessage()));
    }

    public void deletePalette(ColorPalette colorPalette) {
        Disposable disposable =
                respository.removeColorPalette(colorPalette)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.newThread())
                        .subscribe(view::onDeletedColorPalette,
                                error -> view.onColorGridError(error.getMessage()));
    }

    public void lockColor(String color) {
        Disposable disposable =
                storedPaletteRepository.addStoredColor(color)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(res_ -> view.onStoredColor(color),
                        error -> view.onStoredColorError(error.getMessage()));

    }

    public void pinPalette(List<String> palette) {
        Disposable disposable =
                storedPaletteRepository.addStoredPalette(palette)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(res_ -> view.onStoredPalette(palette),
                        error -> view.onStoredPaletteError(error.getMessage()));
    }
}
