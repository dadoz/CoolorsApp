package com.application.dev.david.coolorsapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsActivity extends AppCompatActivity {
    @BindView(R.id.colorSettingsListViewId)
    ListView colorSettingsListView;
    @BindView(R.id.toolbarId)
    Toolbar toolbar;
    private List<String> settingList = Arrays.asList("Reset your palettes",
            "Your Account",
            "Contributors on palette",
            "Third libraries",
            "Version 0.1");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        onInitView();
    }

    private void onInitView() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.setting_text));
        colorSettingsListView.setAdapter(new ArrayAdapter<>(this,
                R.layout.simple_list_item, settingList));
    }
}
