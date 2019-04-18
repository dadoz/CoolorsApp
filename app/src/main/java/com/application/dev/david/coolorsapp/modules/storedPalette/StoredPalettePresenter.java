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

    public void editPaletteName(String paletteId, String label) {
        respository.editStoredPaletteName(paletteId, label)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(view::onStoredPaletteEditedName,
                        error -> view.onStoredPaletteEditedNameError(error.getMessage()));
    }

    public void deletePalette(String paletteId) {
        respository.deleteStoredPalette(paletteId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(view::onStoredPaletteDeleted,
                        error -> view.onStoredPaletteDeletedError(error.getMessage()));

    }
}
