package com.application.dev.david.coolorsapp;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.application.dev.david.coolorsapp.data.ColorsRepository;
import com.application.dev.david.coolorsapp.modules.colorGrid.ColorGridPresenter;
import com.application.dev.david.coolorsapp.modules.colorGrid.ColorGridView;
import com.application.dev.david.coolorsapp.modules.colorGrid.adapter.ColorGridPagerAdapter;
import com.application.dev.david.coolorsapp.modules.colorGrid.adapter.ColorSettingArrayListAdapter;
import com.application.dev.david.coolorsapp.utils.ColorUtils;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

//powered by https://www.colourlovers.com/api/palettes/random

public class MainActivity extends AppCompatActivity implements ColorGridView {
    private ColorGridPresenter presenter;
    @BindView(R.id.colorGridViewPagerId)
    ViewPager colorGridViewPager;
    @BindView(R.id.toolbarId)
    Toolbar toolbar;
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
    List settingList = Arrays.asList("Pin list", "Lock color", "Share list", "About and sources");
    private BottomSheetBehavior<View> bottomSheetBeh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.app_name));

        presenter = new ColorGridPresenter(this, new ColorsRepository());
        onInitView();
    }

    /**
     * init view
     */
    private void onInitView() {
        presenter.retrieveData();
        colorGridViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
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

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_dashboard:
                    return true;
            }
            return false;

        });

        bottomSheetBeh = BottomSheetBehavior.from(colorSettingMenuCardView);
    }

    @Override
    public void onColorGrid(List<String> list) {
        if (colorGridViewPager.getAdapter() == null)
            colorGridViewPager.setAdapter(new ColorGridPagerAdapter(list,
                    (v, postion) -> {
                        int selectedColor = Color.parseColor(((ColorGridPagerAdapter) colorGridViewPager.getAdapter())
                                .getColorList(colorGridViewPager.getCurrentItem()).get(postion));
                        bottomSheetBeh.setState(BottomSheetBehavior.STATE_EXPANDED);
                        colorUserTextView.setText("davide bla");
                        colorUserTextView.setTextColor(ColorUtils.darken(selectedColor, 0.3f));
                        colorSettingSeparatorView.setBackgroundColor(ColorUtils.darken(selectedColor, 0.3f));
                        colorSettingListView.setAdapter(new ColorSettingArrayListAdapter(this, R.layout.color_setting_item,
                                settingList, selectedColor));
                    }));
        else {
            ((ColorGridPagerAdapter) colorGridViewPager.getAdapter()).setColorList(list,
                    colorGridViewPager.getCurrentItem(), colorGridViewPager.getChildAt(0));
        }
    }

    @Override
    public void onColorGridError(String error) {
        Log.e(getClass().getName(), error);
        Snackbar.make(container, error, Snackbar.LENGTH_SHORT).show();
    }
}
