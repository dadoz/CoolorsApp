package com.application.dev.david.coolorsapp.modules.storedPalette.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.application.dev.david.coolorsapp.R;
import com.application.dev.david.coolorsapp.data.StoredPaletteRepository;
import com.application.dev.david.coolorsapp.data.local.StoredPaletteStorage;
import com.application.dev.david.coolorsapp.models.ColorPalette;
import com.application.dev.david.coolorsapp.modules.storedPalette.StoredPalettePresenter;
import com.application.dev.david.coolorsapp.modules.storedPalette.StoredPaletteView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StoredPaletteFragment extends Fragment implements StoredPaletteView {
    private StoredPalettePresenter presenter;

    @BindView(R.id.colorStoredPaletteRecyclerViewId)
    RecyclerView colorStoredPaletteRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.color_stored_palette_layout, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new StoredPalettePresenter(this, new StoredPaletteRepository(new StoredPaletteStorage()));
        presenter.retrieveData();

    }

    @Override
    public void onStoredPaletteRetrieved(List<ColorPalette> list, int requestedPosition) {

    }

    @Override
    public void onStoredPaletteError(String error) {

    }
}
