package com.application.dev.david.coolorsapp.modules.storedPalette;


import com.application.dev.david.coolorsapp.data.StoredPaletteRepository;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class StoredPalettePresenter {
    private final StoredPaletteRepository respository;
    private final StoredPaletteView view;

    public StoredPalettePresenter(StoredPaletteView view, StoredPaletteRepository repository) {
        this.respository = repository;
        this.view = view;
    }

    public void retrieveData() {
        Disposable disposable =
                        respository.getStoredPalette()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.newThread())
                        .subscribe(view::onStoredPaletteRetrieved,
                                error -> view.onStoredPaletteError(error.getMessage()));
    }
}
