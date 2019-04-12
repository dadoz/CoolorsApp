package com.application.dev.david.coolorsapp.modules.colorPalette.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.card.MaterialCardView;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.application.dev.david.coolorsapp.R;
import com.application.dev.david.coolorsapp.data.ColorsRepository;
import com.application.dev.david.coolorsapp.data.StoredPaletteRepository;
import com.application.dev.david.coolorsapp.data.local.LocalColorsStorage;
import com.application.dev.david.coolorsapp.data.local.StoredPaletteStorage;
import com.application.dev.david.coolorsapp.data.remote.RemoteColorsStorage;
import com.application.dev.david.coolorsapp.models.ColorPalette;
import com.application.dev.david.coolorsapp.modules.colorPalette.ColorGridPresenter;
import com.application.dev.david.coolorsapp.modules.colorPalette.ColorGridView;
import com.application.dev.david.coolorsapp.modules.colorPalette.adapter.ColorGridPagerAdapter;
import com.application.dev.david.coolorsapp.modules.colorPalette.adapter.ColorSettingArrayListAdapter;
import com.application.dev.david.coolorsapp.utils.ColorUtils;
import com.bumptech.glide.Glide;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.design.widget.BottomSheetBehavior.STATE_COLLAPSED;
import static android.support.design.widget.BottomSheetBehavior.STATE_EXPANDED;


public class ColorPaletteFragment extends Fragment implements ColorGridView {
    private ColorGridPresenter presenter;
    @BindView(R.id.colorGridViewPagerId)
    ViewPager colorGridViewPager;
    @BindView(R.id.container)
    View container;
    @BindView(R.id.colorSettingListViewId)
    ListView colorSettingListView;
    @BindView(R.id.colorUserLayoutId)
    View colorUserLayout;
    @BindView(R.id.colorUserTextViewId)
    TextView colorUserTextView;
    @BindView(R.id.colorUserImageViewId)
    ImageView colorUserImageView;
    @BindView(R.id.colorSettingSeparatorViewId)
    View colorSettingSeparatorView;
    @BindView(R.id.colorSettingMenuCardViewId)
    View colorSettingMenuCardView;
    private BottomSheetBehavior<View> bottomSheetBeh;

    //TODO move smwhere else
    private final static String USERNAME = null;//"david";
    private List<String> settingList = Arrays.asList("Pin Palette", "Lock Color", "Share Palette", "Delete Palette", "About and sources");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.color_palette_layout, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new ColorGridPresenter(this,
                new ColorsRepository(new RemoteColorsStorage(), new LocalColorsStorage()),
                new StoredPaletteRepository(new StoredPaletteStorage()));
        onInitView();
    }

    /**
     * init view
     */
    private void onInitView() {
        bottomSheetBeh = BottomSheetBehavior.from(colorSettingMenuCardView);
        colorGridViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                //load next page
                if (!((ColorGridPagerAdapter) colorGridViewPager.getAdapter()).hasItemsAtPosition(i +1))
                    presenter.retrieveData(i +1);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        presenter.retrieveData(0);
        initDialogView();
    }

    @Override
    public void onColorGrid(List<ColorPalette> list, int requestedPos) {
        if (colorGridViewPager.getAdapter() == null) {
            colorGridViewPager.setAdapter(new ColorGridPagerAdapter(list,
                    (items, v, position) -> updateColorSettingsView(list, position)));
            presenter.retrieveData(requestedPos +1);
        } else {
            ((ColorGridPagerAdapter) colorGridViewPager.getAdapter()).setItems(requestedPos, list);
            colorGridViewPager.getAdapter().notifyDataSetChanged();
        }
    }

    /**
     *  @param list
     * @param colorPosition
     */
    private void updateColorSettingsView(List<ColorPalette> list, int colorPosition) {
        int selectedColor = Color.parseColor(((ColorGridPagerAdapter) colorGridViewPager.getAdapter())
                .getColorList(colorGridViewPager.getCurrentItem()).get(colorPosition));
        int selectedOppositeColor = ColorUtils.gerOppositeColor(selectedColor);

        ((MaterialCardView) colorSettingMenuCardView).setCardBackgroundColor(selectedColor);
        bottomSheetBeh.setState(STATE_EXPANDED);

        if (USERNAME != null) {
            colorUserTextView.setTextColor(selectedOppositeColor);
            colorSettingSeparatorView.setBackgroundColor(selectedOppositeColor);
        }

        int pagePosition = colorGridViewPager.getCurrentItem();
        ((ColorSettingArrayListAdapter) colorSettingListView.getAdapter()).setSelectedOppositeColor(selectedOppositeColor);
        ((ColorSettingArrayListAdapter) colorSettingListView.getAdapter()).setOndialogItemClickListener((res_, pos_) ->
                ColorPaletteFragment.this.onDialogItemClick(list.get(pagePosition).getColorList(), colorPosition, pos_));
    }

    /**
     *
     */
    void initDialogView() {
        if (USERNAME != null) {
            colorUserLayout.setVisibility(View.VISIBLE);
            colorUserTextView.setText(USERNAME);
            Glide.with(getActivity().getApplicationContext())
                    .load("https://api.adorable.io/avatars/" + USERNAME)
                    .circleCrop()
                    .into(colorUserImageView);
        }
        colorSettingListView.setAdapter(new ColorSettingArrayListAdapter(getContext(),
                R.layout.color_setting_item, settingList));
    }

    /**
     *
     * @param list
     */
    private void onDialogItemClick(List<String> list, int colorPosition, int optionPosition) {
        switch (optionPosition) {
            case 0:
                //PIN
                bottomSheetBeh.setState(STATE_COLLAPSED);
                presenter.pinPalette(list);
                break;
            case 1:
                //LOCK
                bottomSheetBeh.setState(STATE_COLLAPSED);
                presenter.lockColor(list.get(colorPosition));
                break;
            case 2:
                //SHARE
                Toast.makeText(getContext(), "share your palette", Toast.LENGTH_SHORT).show();
                break;
            case 3:
                //DELETE
                bottomSheetBeh.setState(STATE_COLLAPSED);
                presenter.deletePalette();
                break;
            case 4:
                //INFO
                break;
        }
    }

    @Override
    public void onColorGridError(String error) {
        Log.e(getClass().getName(), "----" + error);
        Snackbar.make(container, error, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onStoredColor(String color) {

    }

    @Override
    public void onStoredColorError(String message) {
        Log.e(getClass().getName(), message);

    }

    @Override
    public void onStoredPalette(List<String> palette) {

    }

    @Override
    public void onStoredPaletteError(String message) {
        Log.e(getClass().getName(), message);

    }
}
