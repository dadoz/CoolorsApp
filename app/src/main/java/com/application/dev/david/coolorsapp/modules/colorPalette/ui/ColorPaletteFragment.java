package com.application.dev.david.coolorsapp.modules.colorPalette.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

import com.application.dev.david.coolorsapp.R;
import com.application.dev.david.coolorsapp.data.ColorsRepository;
import com.application.dev.david.coolorsapp.modules.colorPalette.ColorGridPresenter;
import com.application.dev.david.coolorsapp.modules.colorPalette.ColorGridView;
import com.application.dev.david.coolorsapp.modules.colorPalette.adapter.ColorGridPagerAdapter;
import com.application.dev.david.coolorsapp.modules.colorPalette.adapter.ColorSettingArrayListAdapter;
import com.application.dev.david.coolorsapp.utils.ColorUtils;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ColorPaletteFragment extends Fragment implements ColorGridView {
    private static final String COLORS_SHARED_PREF_KEY = "COOLORS_LIST_ID_";
    private ColorGridPresenter presenter;
    @BindView(R.id.colorGridViewPagerId)
    ViewPager colorGridViewPager;
    @BindView(R.id.container)
    View container;
    @BindView(R.id.colorSettingListViewId)
    ListView colorSettingListView;
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
    private final static String USERNAME = "david";
    private List settingList = Arrays.asList("Pin list", "Lock color", "Share list", "About and sources");
    private SharedPreferences sharedPre;

    //SharedPref

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
        presenter = new ColorGridPresenter(this, new ColorsRepository());
        sharedPre = getContext().getSharedPreferences(getContext().getPackageName() + "_preferences", Context.MODE_PRIVATE);
        onInitView();
    }

    /**
     * init view
     */
    private void onInitView() {
        colorGridViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                //load from shared
                if (sharedPre.contains(COLORS_SHARED_PREF_KEY + i)) {
                    Set<String> colorSet = sharedPre.getStringSet(COLORS_SHARED_PREF_KEY + i, null);
                    List colorList = new ArrayList();
                    colorList.addAll(colorSet);
                    onColorGrid(colorList);
                    return;
                }

                //retrieve new data
                if (colorGridViewPager.getAdapter() == null ||
                        ((ColorGridPagerAdapter) colorGridViewPager.getAdapter()).getColorList(i).size() < 5) {
                    presenter.retrieveData();
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });

        bottomSheetBeh = BottomSheetBehavior.from(colorSettingMenuCardView);

        if (sharedPre.contains(COLORS_SHARED_PREF_KEY + 0)) {
            Set<String> colorSet = sharedPre.getStringSet(COLORS_SHARED_PREF_KEY + 0, null);
            List colorList = new ArrayList();
            colorList.addAll(colorSet);
            onColorGrid(colorList);
            return;
        }
        presenter.retrieveData();
    }

    @Override
    public void onColorGrid(List<String> list) {
        int index = 0;
        if (colorGridViewPager.getAdapter() == null) {
            colorGridViewPager.setAdapter(new ColorGridPagerAdapter(list,
                    (v, position) -> updateColorSettingsView(position)));
        } else {
            index = colorGridViewPager.getCurrentItem();
            ((ColorGridPagerAdapter) colorGridViewPager.getAdapter()).setColorList(list,
                    index, colorGridViewPager.getChildAt(index));
        }
        sharedPre.edit().putStringSet(COLORS_SHARED_PREF_KEY + index, new HashSet<>(list)).commit();
    }

    /**
     *
     * @param position
     */
    private void updateColorSettingsView(int position) {
            int selectedColor = Color.parseColor(((ColorGridPagerAdapter) colorGridViewPager.getAdapter())
                    .getColorList(colorGridViewPager.getCurrentItem()).get(position));
            bottomSheetBeh.setState(BottomSheetBehavior.STATE_EXPANDED);
            colorUserTextView.setText(USERNAME);
            colorUserTextView.setTextColor(ColorUtils.darken(selectedColor, 0.3f));
            Glide.with(getActivity().getApplicationContext())
                    .load("https://api.adorable.io/avatars/" + USERNAME)
                    .circleCrop()
                    .into(colorUserImageView);
            colorSettingSeparatorView.setBackgroundColor(ColorUtils.darken(selectedColor, 0.3f));
            colorSettingListView.setAdapter(new ColorSettingArrayListAdapter(getContext(), R.layout.color_setting_item,
                    settingList, selectedColor));
    }

    @Override
    public void onColorGridError(String error) {
        Log.e(getClass().getName(), error);
        Snackbar.make(container, error, Snackbar.LENGTH_SHORT).show();
    }
}
