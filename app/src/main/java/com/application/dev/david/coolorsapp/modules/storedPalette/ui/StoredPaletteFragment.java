package com.application.dev.david.coolorsapp.modules.storedPalette.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.application.dev.david.coolorsapp.R;
import com.application.dev.david.coolorsapp.data.StoredPaletteRepository;
import com.application.dev.david.coolorsapp.data.local.StoredPaletteStorage;
import com.application.dev.david.coolorsapp.models.ColorPalette;
import com.application.dev.david.coolorsapp.models.StoredColorPalette;
import com.application.dev.david.coolorsapp.modules.storedPalette.StoredPalettePresenter;
import com.application.dev.david.coolorsapp.modules.storedPalette.StoredPaletteView;
import com.application.dev.david.coolorsapp.modules.storedPalette.adapter.StoredPaletteOptionsArrayListAdapter;
import com.application.dev.david.coolorsapp.modules.storedPalette.adapter.StoredPaletteRecyclerViewAdapter;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.design.widget.BottomSheetBehavior.STATE_COLLAPSED;
import static android.support.design.widget.BottomSheetBehavior.STATE_EXPANDED;
import static android.support.design.widget.BottomSheetBehavior.from;

public class StoredPaletteFragment extends Fragment implements StoredPaletteView {
    private StoredPalettePresenter presenter;

    @BindView(R.id.colorStoredPaletteRecyclerViewId)
    RecyclerView colorStoredPaletteRecyclerView;
    @BindView(R.id.colorStoredPaletteOptionsListViewId)
    ListView colorStoredPaletteOptionsListView;
    @BindView(R.id.colorStoredPaletteOptionsMenuCardViewId)
    View colorStoredPaletteOptionsMenuCardView;
    private BottomSheetBehavior<View> colorStoredPaletteOptionsBottomBehavior;
    private List<String> settingList = Arrays.asList("Rename", "Share Palette", "Delete");

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
        colorStoredPaletteOptionsBottomBehavior =  BottomSheetBehavior.from(colorStoredPaletteOptionsMenuCardView);
        presenter = new StoredPalettePresenter(this, new StoredPaletteRepository(new StoredPaletteStorage()));
        presenter.retrieveData();
    }

    @Override
    public void onStoredPaletteRetrieved(List<StoredColorPalette> list) {
        colorStoredPaletteRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        colorStoredPaletteRecyclerView.setAdapter(new StoredPaletteRecyclerViewAdapter(list, (v, position) -> {
            colorStoredPaletteOptionsBottomBehavior.setState(STATE_EXPANDED);
            colorStoredPaletteOptionsListView.setAdapter(new StoredPaletteOptionsArrayListAdapter(getContext(),
                    R.layout.stored_palette_option_item, settingList, pos_ -> this.checkPosition(list.get(position).getColorPaletteId(), pos_)));
        }));
    }

    @Override
    public void onStoredPaletteEditedName(boolean result) {
        Toast.makeText(getContext(), "edited" , Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStoredPaletteDeleted(boolean result) {
        Toast.makeText(getContext(), "Deleted" , Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStoredPaletteEditedNameError(String error) {
        Log.e(getClass().getName(), error);
    }

    @Override
    public void onStoredPaletteDeletedError(String error) {
        Log.e(getClass().getName(), error);
    }

    private void checkPosition(String paletteId, int position) {
        colorStoredPaletteOptionsBottomBehavior.setState(STATE_COLLAPSED);

        switch (position) {
            case 0:
                presenter.editPaletteName(paletteId, "balalalala getlabel");
            case 1:
                //share
                Toast.makeText(getContext(), "Share palette n" + paletteId, Toast.LENGTH_SHORT).show();
            case 2:
                presenter.deletePalette(paletteId);
            default:
        }
    }

    @Override
    public void onStoredPaletteError(String error) {
        Log.e(getClass().getName(), error);
    }
}
