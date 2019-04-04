package com.application.dev.david.coolorsapp;

//powered by https://www.colourlovers.com/api/palettes/random

import android.os.Bundle;

import com.application.dev.david.coolorsapp.modules.colorPalette.ui.ColorPaletteFragment;

public class MainActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onInitView(savedInstanceState);
    }

    private void onInitView(Bundle savedInstanceState) {
        loadFragment(new ColorPaletteFragment());
    }

}